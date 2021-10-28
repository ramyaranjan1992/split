package payer.restservices.dto;

public class PayerInformation {

	private String payerId;
	private String startDate;
	private String endDate;
	private boolean totalData;

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public boolean isTotalData() {
		return totalData;
	}

	public void setTotalData(boolean totalData) {
		this.totalData = totalData;
	}

	@Override
	public String toString() {
		return "PayerInformation [payerId=" + payerId + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", totalData=" + totalData + "]";
	}

}
