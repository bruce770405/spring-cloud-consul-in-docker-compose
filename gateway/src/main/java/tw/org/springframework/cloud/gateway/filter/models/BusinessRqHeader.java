package tw.org.springframework.cloud.gateway.filter.models;

import java.util.Date;

public class BusinessRqHeader {

	private String trackingPk;
	
	
	private String locale;
	
	
	private String clientSystem;
	
	
	private String clientInstanceId;
	
	
	private Date requestTime;


	public String getTrackingPk() {
		return trackingPk;
	}


	public void setTrackingPk(String trackingPk) {
		this.trackingPk = trackingPk;
	}


	public String getLocale() {
		return locale;
	}


	public void setLocale(String locale) {
		this.locale = locale;
	}


	public Date getRequestTime() {
		return requestTime;
	}


	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}


	public String getClientSystem() {
		return clientSystem;
	}


	public void setClientSystem(String clientSystem) {
		this.clientSystem = clientSystem;
	}


	public String getClientInstanceId() {
		return clientInstanceId;
	}


	public void setClientInstanceId(String clientInstanceId) {
		this.clientInstanceId = clientInstanceId;
	}

	
	
}
