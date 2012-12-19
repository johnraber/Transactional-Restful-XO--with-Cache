package net.johnraber.sxo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import net.johnraber.sxo.model.PaymentPlan;
import net.johnraber.sxo.model.XOSession;

@Service
public class PaymentPlanServiceImpl implements PaymentPlanService {

	private static final Logger log = LoggerFactory.getLogger(PaymentPlanServiceImpl.class);
	
	public PaymentPlan update( XOSession xoSession )
	{
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("Creating/updating a payment plan in response to a XO Session: " + xoSession.getDisplayString()  );
		
		return new PaymentPlan();
	}
}
