package payer.restservices.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.Size;

public class DefaultPayer implements Serializable {
	private static final long serialVersionUID = 1L;

	private String payStatus;
	private String payMode;

	private Double rateOfInterset;

	private Double penalInterset;

	private Double penalIntersetAmount;

	private Double penalGst;

	private Double penalGstAmount;

	private Double gst;

	private int loanTenure;

	private Long loanAmount;

	private Double interset;

	private Double principal;

	private Double gstOnPrincipal;

	private Double balance;

	private Double paybleEmi;

	private Double totalPayble;

	private Double defaulttotalPayble;

	private Date emiDate;

	private Long paymentConfirmation;

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public Double getRateOfInterset() {
		return rateOfInterset;
	}

	public void setRateOfInterset(Double rateOfInterset) {
		this.rateOfInterset = rateOfInterset;
	}

	public Double getPenalInterset() {
		return penalInterset;
	}

	public void setPenalInterset(Double penalInterset) {
		this.penalInterset = penalInterset;
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

	public Double getInterset() {
		return interset;
	}

	public void setInterset(Double interset) {
		this.interset = interset;
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

	public Double getPenalIntersetAmount() {
		return penalIntersetAmount;
	}

	public void setPenalIntersetAmount(Double penalIntersetAmount) {
		this.penalIntersetAmount = penalIntersetAmount;
	}

	public Double getPenalGstAmount() {
		return penalGstAmount;
	}

	public void setPenalGstAmount(Double penalGstAmount) {
		this.penalGstAmount = penalGstAmount;
	}

	public Double getDefaulttotalPayble() {
		return defaulttotalPayble;
	}

	public void setDefaulttotalPayble(Double defaulttotalPayble) {
		this.defaulttotalPayble = defaulttotalPayble;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

}
