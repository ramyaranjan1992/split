package payer.restservices.modal;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "t_merchant")
public class MerchantBO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "STATUS")
	private Boolean status;

	@Column(name = "MERCHANT_ID", length = 50)
	private String merchantId;

	@Column(name = "MERCHANT_NAME", length = 500)
	private String merchatName;

	@Column(name = "SECRETKEY", length = 50)
	private String secretkey;

	@Column(name = "EMAIL", length = 50)
	private String email;

	@Column(name = "ORGANISATION", length = 100)
	private String organisation;

	@Column(name = "PAN", length = 50)
	private String pan;

	@Column(name = "AADHAAR")
	private String aadhaar;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "UPDATED_ON")
	private Date updatedOn;

	@Column(name = "PHONE_NUMBER")
	private Long phoneNumber;

	@Column(name = "ALTERNATIVE_PHONE_NUMBER")
	private Long alternativePhoneNumber;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchatName() {
		return merchatName;
	}

	public void setMerchatName(String merchatName) {
		this.merchatName = merchatName;
	}

	public String getSecretkey() {
		return secretkey;
	}

	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
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

}
