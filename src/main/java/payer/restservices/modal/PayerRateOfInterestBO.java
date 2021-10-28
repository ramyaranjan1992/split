package payer.restservices.modal;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "t_payer_rate_of_interest")
public class PayerRateOfInterestBO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RATE_OF_INTEREST_ID")
	private long id;

	@Column(name = "SCHEME_NAME", length = 50)
	private String schemeName;

	@Column(name = "RATE_OF_INTEREST")
	private Double rateOfInterest;

	@Column(name = "PENAL_INTEREST")
	private Double penalInterest;

	@Column(name = "PENAL_GST")
	private Double penalGst;

	@Column(name = "GST")
	private Double gst;

	@Column(name = "LOAN_TENURE")
	private int loanTenure;

	@Column(name = "LOAN_AMOUNT")
	private Long loanAmount;

	@Column(name = "LOAN_EMI_DATE")
	private Date loanEmiDate;

	@Column(name = "CREATED_ON")
	private Date createdOn;

	@Column(name = "UPDATED_ON")
	private Date updatedOn;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAYER_ID")
	private PayerBO payer;

	@OneToMany(mappedBy = "payerRateOfInterest", fetch = FetchType.LAZY)
	private List<PayerLedgerBO> payerLedgers;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Date getLoanEmiDate() {
		return loanEmiDate;
	}

	public void setLoanEmiDate(Date loanEmiDate) {
		this.loanEmiDate = loanEmiDate;
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

	public PayerBO getPayer() {
		return payer;
	}

	public void setPayer(PayerBO payer) {
		this.payer = payer;
	}

	public List<PayerLedgerBO> getPayerLedgers() {
		return payerLedgers;
	}

	public void setPayerLedgers(List<PayerLedgerBO> payerLedgers) {
		this.payerLedgers = payerLedgers;
	}

	@Override
	public String toString() {
		return "PayerRateOfInterestBO [id=" + id + ", schemeName=" + schemeName + ", rateOfInterest=" + rateOfInterest
				+ ", penalInterest=" + penalInterest + ", penalGst=" + penalGst + ", gst=" + gst + ", loanTenure="
				+ loanTenure + ", loanAmount=" + loanAmount + ", loanEmiDate=" + loanEmiDate + ", createdOn="
				+ createdOn + ", updatedOn=" + updatedOn + ", payer=" + payer + ", payerLedgers=" + payerLedgers + "]";
	}

	
}
