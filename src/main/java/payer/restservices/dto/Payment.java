package payer.restservices.dto;

public class Payment {

	private String payerId;
	private String name;
	private String mobileNumber;
	private String email;
	private String amount;
	private String address;
	private String emiMonth;

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
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

	@Override
	public String toString() {
		return "Payment [payerId=" + payerId + ", name=" + name + ", mobileNumber=" + mobileNumber + ", email=" + email
				+ ", amount=" + amount + ", address=" + address + ", emiMonth=" + emiMonth + "]";
	}

}
