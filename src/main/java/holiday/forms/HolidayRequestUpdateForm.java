
package holiday.forms;

import java.io.Serializable;
import java.util.Date;

import holiday.domain.HolidayRequest;

public class HolidayRequestUpdateForm implements Serializable {

	private static final long serialVersionUID = -4567851363612270029L;

	public HolidayRequest returnHolidayRequest() {
		return holidayRequest;
	}

	public void setHolidayRequest(HolidayRequest holidayRequest) {
		this.holidayRequest = holidayRequest;
	}

	private HolidayRequest holidayRequest;

	public String getEmployee() {
		return holidayRequest.getEmployee();
	}

	public Date getFromDate() {
		return holidayRequest.getFromDate();
	}

	public Date getToDate() {
		return holidayRequest.getToDate();
	}

	public String getApprovedBy() {
		return holidayRequest.getApprovedBy();
	}

	public String getComment() {
		return holidayRequest.getComment();
	}

	public void setComment(String comment) {
		holidayRequest.setComment(comment);
	}

	public Long getId() {
		return holidayRequest.getId();
	}

	public Integer getVersion() {
		return holidayRequest.getVersion();
	}

	public boolean equals(Object obj) {
		return holidayRequest.equals(obj);
	}
	
	

}
