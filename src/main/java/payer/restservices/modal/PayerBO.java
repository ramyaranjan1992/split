package payer.restservices.modal;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity(name = "t_payer")
public class PayerBO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PAYER_ID")
	private long id;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "FULL_NAME", length = 500)
	private String fullName;

	@Column(name = "PASSWORD", length = 50)
	private String passwd;

	@Column(name = "EMAIL", length = 50)
	private String email;

	@Column(name = "ORGANISATION", length = 100)
	private String organisation;

	@Column(name = "PAN", length = 50)
	private String pan;

	@Column(name = "ACCOUNT_ID", length = 50)
	private String accountId;

	@Column(name = "PARENT_NAME", length = 200)
	private String parentName;

	@Column(name = "DOB")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date dob;

	@Column(name = "GENDER", length = 6)
	private String gender;

	@Column(name = "AADHAAR")
	private String aadhaar;

	@Column(name = "CITY")
	private String city;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "PIN_CODE")
	private int pin;

	@Column(name = "PHONE_NUMBER")
	private Long phoneNumber;

	@Column(name = "ALTERNATIVE_PHONE_NUMBER")
	private Long alternativePhoneNumber;

	@Column(name = "MERCHANT_ID")
	private String merchantId;

	@Column(name = "payment_clearance")
	private boolean clearance;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "UPDATED_ON")
	private Date updatedOn;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "payer")
	private PayerRateOfInterestBO payerRateOfInterest;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOrganisation() {
		return organisation;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAadhaar() {
		return aadhaar;
	}

	public void setAadhaar(String aadhaar) {
		this.aadhaar = aadhaar;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getAlternativePhoneNumber() {
		return alternativePhoneNumber;
	}

	public void setAlternativePhoneNumber(Long alternativePhoneNumber) {
		this.alternativePhoneNumber = alternativePhoneNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public PayerRateOfInterestBO getPayerRateOfInterest() {
		return payerRateOfInterest;
	}

	public void setPayerRateOfInterest(PayerRateOfInterestBO payerRateOfInterest) {
		this.payerRateOfInterest = payerRateOfInterest;
	}

	public boolean isClearance() {
		return clearance;
	}

	public void setClearance(boolean clearance) {
		this.clearance = clearance;
	}

	@Override
	public String toString() {
		return "PayerBO [id=" + id + ", status=" + status + ", fullName=" + fullName + ", passwd=" + passwd + ", email="
				+ email + ", organisation=" + organisation + ", pan=" + pan + ", accountId=" + accountId
				+ ", parentName=" + parentName + ", dob=" + dob + ", gender=" + gender + ", aadhaar=" + aadhaar
				+ ", city=" + city + ", state=" + state + ", address=" + address + ", pin=" + pin + ", phoneNumber="
				+ phoneNumber + ", alternativePhoneNumber=" + alternativePhoneNumber + ", merchantId=" + merchantId
				+ ", clearance=" + clearance + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn
				+ ", payerRateOfInterest=" + payerRateOfInterest + "]";
	}

}
