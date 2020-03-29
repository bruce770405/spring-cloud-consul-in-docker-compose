package tw.org.springframework.cloud.gateway.filter.models;

public class BusinessRq {

	
	private BusinessRqHeader rqHeader;
	
	
	private Object rqData;


	
	public BusinessRqHeader getRqHeader() {
		return rqHeader;
	}


	public void setRqHeader(BusinessRqHeader rqHeader) {
		this.rqHeader = rqHeader;
	}


	public Object getRqData() {
		return rqData;
	}


	public void setRqData(Object rqData) {
		this.rqData = rqData;
	}
	
	
	
	
	
	
	
}
