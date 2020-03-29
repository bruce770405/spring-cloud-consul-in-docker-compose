package tw.com.bruce.gateway;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_SCHEME_PREFIX_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestTransformer;
import org.springframework.cloud.gateway.config.LoadBalancerProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.LoadBalancerClientFilter;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Configuration
//@ConditionalOnClass({LoadBalancerClient.class, RibbonAutoConfiguration.class, DispatcherHandler.class})
//@AutoConfigureAfter(RibbonAutoConfiguration.class)
//@EnableConfigurationProperties(LoadBalancerProperties.class)
public class ServiceRequestConfiguration {
	
	
	//@Bean("LoadBalancerClientFilter")
	//@ConditionalOnBean(LoadBalancerClient.class)
	public RefactorContextPathFilter loadBalancerClientFilter(LoadBalancerClient client, LoadBalancerProperties properties) {
		return new RefactorContextPathFilter(client, properties);
	}
	
	
	
	public class RefactorContextPathFilter extends LoadBalancerClientFilter {
		
		private LoadBalancerProperties properties;

		public RefactorContextPathFilter(LoadBalancerClient loadBalancer, LoadBalancerProperties properties) {
			super(loadBalancer, properties);
			this.properties = properties;
		}
		
		
		@Override
		public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
			URI url = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
			String schemePrefix = exchange.getAttribute(GATEWAY_SCHEME_PREFIX_ATTR);
			
			System.out.println("RefactorContextPathFilter url before: " + url);
			
			if (url == null || (!"lb".equals(url.getScheme()) && !"lb".equals(schemePrefix))) {
				return chain.filter(exchange);
			}
			//preserve the original url
			addOriginalRequestUrl(exchange, url);

			System.out.println("RefactorContextPathFilter url before: " + url);

			final ServiceInstance instance = choose(exchange);

			if (instance == null) {
				String msg = "Unable to find instance for " + url.getHost();
				if(properties.isUse404()) {
					throw new FourOFourNotFoundException(msg);
				}
				throw new NotFoundException(msg);
			}

			URI uri = exchange.getRequest().getURI();

			// if the `lb:<scheme>` mechanism was used, use `<scheme>` as the default,
			// if the loadbalancer doesn't provide one.
			String overrideScheme = instance.isSecure() ? "https" : "http";
			if (schemePrefix != null) {
				overrideScheme = url.getScheme();
			}
			

			URI requestUrl = loadBalancer.reconstructURI(new DelegatingServiceInstance(instance, overrideScheme), uri);
			
			// Refactor Context Path
			if(instance.getMetadata() != null) {
				String contextPath = instance.getMetadata().get("contextPath");
				
				if(contextPath != null) {
					
					System.out.println("RefactorContextPathFilter ： contextPath="+contextPath);
					
					try {
						requestUrl = new URIBuilder(requestUrl).setPath(contextPath + requestUrl.getPath()).build();
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			//--
			
			System.out.println("RefactorContextPathFilter url chosen: " + requestUrl);
			exchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, requestUrl);
			return chain.filter(exchange);
		}
		
		
		@ResponseStatus(value = NOT_FOUND, reason = "The service was not found.")
		private class FourOFourNotFoundException extends RuntimeException {
			public FourOFourNotFoundException(String msg) {
				super(msg);
			}
		}
		
		class DelegatingServiceInstance implements ServiceInstance {
			final ServiceInstance delegate;
			private String overrideScheme;

			DelegatingServiceInstance(ServiceInstance delegate, String overrideScheme) {
				this.delegate = delegate;
				this.overrideScheme = overrideScheme;
			}

			@Override
			public String getServiceId() {
				return delegate.getServiceId();
			}

			@Override
			public String getHost() {
				return delegate.getHost();
			}

			@Override
			public int getPort() {
				return delegate.getPort();
			}

			@Override
			public boolean isSecure() {
				return delegate.isSecure();
			}

			@Override
			public URI getUri() {
				return delegate.getUri();
			}

			@Override
			public Map<String, String> getMetadata() {
				return delegate.getMetadata();
			}

			@Override
			public String getScheme() {
				String scheme = delegate.getScheme();
				if (scheme != null) {
					return scheme;
				}
				return this.overrideScheme;
			}

		}
		
	}
	
	
	
	
	
	
	
	
	
	

	@Bean
	public LoadBalancerRequestTransformer consulContextPathTransformer() {
		return new LoadBalancerRequestTransformer() {
			
			@Override
			public HttpRequest transformRequest(HttpRequest request, ServiceInstance instance) {
				
				System.out.println("ConsulContextPathTransformer execute transformRequest ");
				
				if(instance.getMetadata() != null) {
					String contextPath = instance.getMetadata().get("contextPath");
					
					if(contextPath != null) {
						
						System.out.println("ConsulContextPathTransformer ： contextPath="+contextPath);
						
						return new ConsulContextPathServiceRequestWrapper(request, contextPath);
						
					}
					
				}
				
				return request;
			}
			
		};
	}
	
	
	public class ConsulContextPathServiceRequestWrapper extends HttpRequestWrapper {
		
		private String contextPath;

		public ConsulContextPathServiceRequestWrapper(HttpRequest request, String contextPath) {
			super(request);
			this.contextPath = contextPath;
		}

		@Override
		public URI getURI() {

			URI originUri = super.getURI();
			
			try {
				return new URIBuilder(originUri).setPath(contextPath + originUri.getPath()).build();
			} catch (URISyntaxException e) {
				System.err.println(e.getMessage());
				return originUri;
			}
		}
		
	}
	
}
