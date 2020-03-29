package tw.org.springframework.cloud.gateway.filter.models;

import java.util.Date;

public class BusinessRsHeader {

	private String trackingPk;
	
	
	private Date responseTime;
	
	
	private String statusSystem;
	
	
	private String statusCode;
	
	
	private String statusDesc;


	public String getTrackingPk() {
		return trackingPk;
	}


	public void setTrackingPk(String trackingPk) {
		this.trackingPk = trackingPk;
	}


	public Date getResponseTime() {
		return responseTime;
	}


	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}


	public String getStatusCode() {
		return statusCode;
	}


	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}


	public String getStatusDesc() {
		return statusDesc;
	}


	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}


	public String getStatusSystem() {
		return statusSystem;
	}


	public void setStatusSystem(String statusSystem) {
		this.statusSystem = statusSystem;
	}
	
	
	
	
}
