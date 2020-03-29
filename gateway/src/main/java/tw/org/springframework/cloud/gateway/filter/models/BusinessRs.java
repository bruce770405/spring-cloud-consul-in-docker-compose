package tw.org.springframework.cloud.gateway.filter.models;

public class BusinessRs {

	private BusinessRsHeader rsHeader;
	
	
	private Object rsData;


	public BusinessRsHeader getRsHeader() {
		return rsHeader;
	}


	public void setRsHeader(BusinessRsHeader rsHeader) {
		this.rsHeader = rsHeader;
	}


	public Object getRsData() {
		return rsData;
	}


	public void setRsData(Object rsData) {
		this.rsData = rsData;
	}
	
	
	
	
}
