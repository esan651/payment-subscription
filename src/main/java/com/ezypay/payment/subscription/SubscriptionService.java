package com.ezypay.payment.subscription;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

	private final Logger log = LoggerFactory.getLogger(SubscriptionService.class);

	public List<Date> getInvoiceDates(SubscriptionDTO subscriptionDTO) {
		Calendar startCalendar = Calendar.getInstance();
		Calendar endcalendar = Calendar.getInstance();
		List<Date> dateList = new ArrayList<>();
		startCalendar.setTime(subscriptionDTO.getStartDate());
		endcalendar.setTime(subscriptionDTO.getEndDate());
		
		if ("DAILY".equalsIgnoreCase(subscriptionDTO.getSubscriptionType())) {
			dateList.add(startCalendar.getTime());
			while (!startCalendar.equals(endcalendar)) {
				startCalendar.add(Calendar.DATE, 1);
				dateList.add(startCalendar.getTime());
			}
		} else if ("WEEKLY".equalsIgnoreCase(subscriptionDTO.getSubscriptionType())) {
			int valueOfDay = getIntValue(subscriptionDTO.getDayOfWeekOrMonth());
			while (!startCalendar.equals(endcalendar)) {
				startCalendar.add(Calendar.DATE, 1);
				if (startCalendar.get(Calendar.DAY_OF_WEEK) == valueOfDay) {
					dateList.add(startCalendar.getTime());
				}
			}
		} else if ("MONTHLY".equalsIgnoreCase(subscriptionDTO.getSubscriptionType())) {
			while (!startCalendar.equals(endcalendar)) {
				startCalendar.add(Calendar.DATE, 1);
				if (startCalendar.get(Calendar.DAY_OF_MONTH) == Integer
						.parseInt(subscriptionDTO.getDayOfWeekOrMonth())) {
					dateList.add(startCalendar.getTime());
				}
			}
		}
		
		log.info(dateList.toString());
		return dateList;
	}

	public int getIntValue(String dayOfWeek) {
		int value = 0;
		switch (dayOfWeek.toUpperCase()) {
		case "MONDAY":
			value = Calendar.MONDAY;
			break;
		case "TUESDAY":
			value = Calendar.TUESDAY;
			break;
		case "WEDNESDAY":
			value = Calendar.WEDNESDAY;
			break;
		case "THURSDAY":
			value = Calendar.THURSDAY;
			break;
		case "FRIDAY":
			value = Calendar.FRIDAY;
			break;
		case "SATURDAY":
			value = Calendar.SATURDAY;
			break;
		case "SUNDAY":
			value = Calendar.SUNDAY;
			break;
		default:
		}
		return value;
	}

	public boolean checkDateRange(Date startDate, Date endDate) {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		startCalendar.add(Calendar.MONTH, 3);
		if (startCalendar.getTime().before(endDate)) {
			return false;
		} else {
			return true;
		}
	}

}
