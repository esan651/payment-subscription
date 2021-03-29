package com.ezypay.payment.subscription;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class SubscriptionDTO {

	private BigDecimal amount;

	private String subscriptionType;

	private String dayOfWeekOrMonth;

	@JsonFormat(pattern = "dd/MM/yyy", timezone = "GMT+8")
	private Date startDate;

	@JsonFormat(pattern = "dd/MM/yyy", timezone = "GMT+8")
	private Date endDate;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public String getDayOfWeekOrMonth() {
		return dayOfWeekOrMonth;
	}

	public void setDayOfWeekOrMonth(String dayOfWeekOrMonth) {
		this.dayOfWeekOrMonth = dayOfWeekOrMonth;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
