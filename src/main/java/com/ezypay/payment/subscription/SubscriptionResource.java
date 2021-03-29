package com.ezypay.payment.subscription;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SubscriptionResource {

	private final Logger log = LoggerFactory.getLogger(SubscriptionResource.class);

	@Autowired
	SubscriptionService subsriptionService;

	@SuppressWarnings("rawtypes")
	@PostMapping("/v1/subscription")
	public ResponseEntity createSubscription(@Valid @RequestBody SubscriptionDTO subscriptionDTO, HttpServletRequest request) throws URISyntaxException {
		log.debug("REST request to save subscription : {}", subscriptionDTO);
		boolean validRange = false;
		try {
			if (subscriptionDTO.getAmount() == null || subscriptionDTO.getAmount().compareTo(BigDecimal.ZERO) == 0) {
				return ResponseEntity.badRequest().body("Amount not exist and cannot be 0");
			} else {
				validRange = subsriptionService.checkDateRange(subscriptionDTO.getStartDate(), subscriptionDTO.getEndDate());
				if (validRange) {
					List<String> dateList = new ArrayList<>();
					List<Date> allDate = subsriptionService.getInvoiceDates(subscriptionDTO);
					if (!allDate.isEmpty()) {
						for (Date date : allDate) {
							String dateAsString = new SimpleDateFormat("dd/MM/yyyy").format(date);
							dateList.add(dateAsString);
						}
					}
					return ResponseEntity.created(new URI("/api/subscription/" + subscriptionDTO.getAmount())).body(dateList);
				} else {
					return ResponseEntity.status(500).body("Date Range exceed 3 months");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(500).body("Failed to insert subscription");
		}
	}
}
