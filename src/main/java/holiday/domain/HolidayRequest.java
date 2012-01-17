package holiday.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class HolidayRequest implements Serializable {

	private static final long serialVersionUID = 8476851885097632521L;

	@NotNull
	private String employee;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "S-")
	private Date fromDate;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "S-")
	private Date toDate;

	@Pattern(regexp = "\\w*")
	private String approvedBy;

	@Pattern(regexp = "\\p{ASCII}*")
	private String comment;

	public String getEmployee() {
		return this.employee;
	}

	public void setEmployee(String employee) {
		this.employee = employee;
	}

	public Date getFromDate() {
		return this.fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return this.toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getApprovedBy() {
		return this.approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Employee: ").append(getEmployee()).append(", ");
		sb.append("FromDate: ").append(getFromDate()).append(", ");
		sb.append("ToDate: ").append(getToDate()).append(", ");
		sb.append("ApprovedBy: ").append(getApprovedBy()).append(", ");
		sb.append("Comment: ").append(getComment()).append(", ");
		sb.append("Id: ").append(getId()).append(", ");
		sb.append("Version: ").append(getVersion());
		return sb.toString();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Version
	@Column(name = "version")
	private Integer version;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
