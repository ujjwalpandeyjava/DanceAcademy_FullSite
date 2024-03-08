package pandeyDanceAcademy.pda_backend.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Document("customerQuery")
public class CustomerQueryEntity {
	@Id
	private ObjectId id;
	private String name;
	@NotNull(message = "Gender cannot be null")
	@NotBlank(message = "Must specify gender")
	private String gender;
	@NotNull(message = "DanceForm cannot be null")
	@NotBlank(message = "DanceForm must be filled")
	private String danceForm;
	private String emailID;
	@NotNull(message = "Contact cannot be null")
	@NotBlank(message = "Contact must be given for connecting purpose")
	private String contactNo;
	private String address;
	private String extraDetail;
	private Date createdDate;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDanceForm() {
		return danceForm;
	}

	public void setDanceForm(String danceForm) {
		this.danceForm = danceForm;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getExtraDetail() {
		return extraDetail;
	}

	public void setExtraDetail(String extraDetail) {
		this.extraDetail = extraDetail;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "CustomerQueryEntity [id=" + id + ", name=" + name + ", gender=" + gender + ", danceForm=" + danceForm
				+ ", emailID=" + emailID + ", contactNo=" + contactNo + ", address=" + address + ", extraDetail="
				+ extraDetail + ", createdDate=" + createdDate + "]";
	}

}
