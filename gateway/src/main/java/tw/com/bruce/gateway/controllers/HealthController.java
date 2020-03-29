package tw.com.bruce.gateway.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

	@GetMapping("/pid")
	private String getPid() {
		String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
		return processName.split("@")[0];
	}

}
