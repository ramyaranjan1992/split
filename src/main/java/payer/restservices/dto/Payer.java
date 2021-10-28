package payer.restservices.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.Size;

public class Payer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Size(min = 4, max = 50)
	private String fullName;
	
	private String payerId;

	private String email;

	private String organisation;

	private String pan;

	private String parentName;

	private String dob;

	private String loanEmiDate;

	private String gender;

	private String aadhaar;

	private String accountId;

	@Size(min = 10, max = 12)
	private Long phoneNumber;

	@Size(min = 10, max = 12)
	private Long alternativePhoneNumber;

	private String payStatus;

	private String payMode;

	private String city;

	private String state;

	private String address;

	private int pin;

	private String schemeName;

	private Double rateOfInterest;

	private Double penalInterest;

	private Double penalGst;

	private Double gst;

	private int loanTenure;

	private Long loanAmount;

	private int emiMonth;

	private String merchantId;

	private Double interest;

	private Double principal;

	private Double gstOnPrincipal;

	private Double balance;

	private Double paybleEmi;

	private Double totalPayble;

	private Date emiDate;

	private Long paymentConfirmation;

	public DefaultPayer defaultPayer;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
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

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getLoanEmiDate() {
		return loanEmiDate;
	}

	public void setLoanEmiDate(String loanEmiDate) {
		this.loanEmiDate = loanEmiDate;
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

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
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

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
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

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public Double getRateOfInterest() {
		return rateOfInterest;
	}

	public void setRateOfInterest(Double rateOfInterest) {
		this.rateOfInterest = rateOfInterest;
	}

	public Double getPenalInterest() {
		return penalInterest;
	}

	public void setPenalInterest(Double penalInterest) {
		this.penalInterest = penalInterest;
	}

	public Double getPenalGst() {
		return penalGst;
	}

	public void setPenalGst(Double penalGst) {
		this.penalGst = penalGst;
	}

	public Double getGst() {
		return gst;
	}

	public void setGst(Double gst) {
		this.gst = gst;
	}

	public int getLoanTenure() {
		return loanTenure;
	}

	public void setLoanTenure(int loanTenure) {
		this.loanTenure = loanTenure;
	}

	public Long getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Long loanAmount) {
		this.loanAmount = loanAmount;
	}

	public int getEmiMonth() {
		return emiMonth;
	}

	public void setEmiMonth(int emiMonth) {
		this.emiMonth = emiMonth;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getPrincipal() {
		return principal;
	}

	public void setPrincipal(Double principal) {
		this.principal = principal;
	}

	public Double getGstOnPrincipal() {
		return gstOnPrincipal;
	}

	public void setGstOnPrincipal(Double gstOnPrincipal) {
		this.gstOnPrincipal = gstOnPrincipal;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getPaybleEmi() {
		return paybleEmi;
	}

	public void setPaybleEmi(Double paybleEmi) {
		this.paybleEmi = paybleEmi;
	}

	public Double getTotalPayble() {
		return totalPayble;
	}

	public void setTotalPayble(Double totalPayble) {
		this.totalPayble = totalPayble;
	}

	public Date getEmiDate() {
		return emiDate;
	}

	public void setEmiDate(Date emiDate) {
		this.emiDate = emiDate;
	}

	public Long getPaymentConfirmation() {
		return paymentConfirmation;
	}

	public void setPaymentConfirmation(Long paymentConfirmation) {
		this.paymentConfirmation = paymentConfirmation;
	}

	public DefaultPayer getDefaultPayer() {
		return defaultPayer;
	}

	public void setDefaultPayer(DefaultPayer defaultPayer) {
		this.defaultPayer = defaultPayer;
	}

	@Override
	public String toString() {
		return "Payer [fullName=" + fullName + ", payerId=" + payerId + ", email=" + email + ", organisation="
				+ organisation + ", pan=" + pan + ", parentName=" + parentName + ", dob=" + dob + ", loanEmiDate="
				+ loanEmiDate + ", gender=" + gender + ", aadhaar=" + aadhaar + ", accountId=" + accountId
				+ ", phoneNumber=" + phoneNumber + ", alternativePhoneNumber=" + alternativePhoneNumber + ", payStatus="
				+ payStatus + ", payMode=" + payMode + ", city=" + city + ", state=" + state + ", address=" + address
				+ ", pin=" + pin + ", schemeName=" + schemeName + ", rateOfInterest=" + rateOfInterest
				+ ", penalInterest=" + penalInterest + ", penalGst=" + penalGst + ", gst=" + gst + ", loanTenure="
				+ loanTenure + ", loanAmount=" + loanAmount + ", emiMonth=" + emiMonth + ", merchantId=" + merchantId
				+ ", interest=" + interest + ", principal=" + principal + ", gstOnPrincipal=" + gstOnPrincipal
				+ ", balance=" + balance + ", paybleEmi=" + paybleEmi + ", totalPayble=" + totalPayble + ", emiDate="
				+ emiDate + ", paymentConfirmation=" + paymentConfirmation + ", defaultPayer=" + defaultPayer + "]";
	}

}
