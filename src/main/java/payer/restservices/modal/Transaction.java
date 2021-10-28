package payer.restservices.modal;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String payerId;// entered by client

	private String pgRespCode;
	private String pgTxnNo;
	private String sabPaisaTxId;
	private String issuerRefNo;
	private String authIdCode;
	private String amount; // entered by client
	private String clientTxnId;

	private String name; // fullName entered by client

	private String firstName;
	private String lastName;
	
	private String payMode;

	private String email;// entered by client
	private String mobileNumber;// entered by client

	private String spRespCode;
	private String clientCode;
	private String transDate;
	private String spRespStatus;
	private String challanNo;

	private String resMsg;
	private String orgTxnAmount;
	private String programId;
	private String address;
	
	private String emiMonth;

	@OneToOne(cascade = CascadeType.ALL)
	private PayerBO payerBO;

	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getPgRespCode() {
		return pgRespCode;
	}

	public void setPgRespCode(String pgRespCode) {
		this.pgRespCode = pgRespCode;
	}

	public String getPgTxnNo() {
		return pgTxnNo;
	}

	public void setPgTxnNo(String pgTxnNo) {
		this.pgTxnNo = pgTxnNo;
	}

	public String getSabPaisaTxId() {
		return sabPaisaTxId;
	}

	public void setSabPaisaTxId(String sabPaisaTxId) {
		this.sabPaisaTxId = sabPaisaTxId;
	}

	public String getIssuerRefNo() {
		return issuerRefNo;
	}

	public void setIssuerRefNo(String issuerRefNo) {
		this.issuerRefNo = issuerRefNo;
	}

	public String getAuthIdCode() {
		return authIdCode;
	}

	public void setAuthIdCode(String authIdCode) {
		this.authIdCode = authIdCode;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getClientTxnId() {
		return clientTxnId;
	}

	public void setClientTxnId(String clientTxnId) {
		this.clientTxnId = clientTxnId;
	}

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getSpRespCode() {
		return spRespCode;
	}

	public void setSpRespCode(String spRespCode) {
		this.spRespCode = spRespCode;
	}

	public String getClientCode() {
		return clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getSpRespStatus() {
		return spRespStatus;
	}

	public void setSpRespStatus(String spRespStatus) {
		this.spRespStatus = spRespStatus;
	}

	public String getChallanNo() {
		return challanNo;
	}

	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}

	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	public String getOrgTxnAmount() {
		return orgTxnAmount;
	}

	public void setOrgTxnAmount(String orgTxnAmount) {
		this.orgTxnAmount = orgTxnAmount;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	



	public String getEmiMonth() {
		return emiMonth;
	}

	public void setEmiMonth(String emiMonth) {
		this.emiMonth = emiMonth;
	}

	public PayerBO getPayerBO() {
		return payerBO;
	}

	public void setPayerBO(PayerBO payerBO) {
		this.payerBO = payerBO;
	}
	
	

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", payerId=" + payerId + ", pgRespCode=" + pgRespCode + ", pgTxnNo=" + pgTxnNo
				+ ", sabPaisaTxId=" + sabPaisaTxId + ", issuerRefNo=" + issuerRefNo + ", authIdCode=" + authIdCode
				+ ", amount=" + amount + ", clientTxnId=" + clientTxnId + ", name=" + name + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", payMode=" + payMode + ", email=" + email + ", mobileNumber="
				+ mobileNumber + ", spRespCode=" + spRespCode + ", clientCode=" + clientCode + ", transDate="
				+ transDate + ", spRespStatus=" + spRespStatus + ", challanNo=" + challanNo + ", resMsg=" + resMsg
				+ ", orgTxnAmount=" + orgTxnAmount + ", programId=" + programId + ", address=" + address + ", emiMonth="
				+ emiMonth + ", payerBO=" + payerBO + "]";
	}

}
