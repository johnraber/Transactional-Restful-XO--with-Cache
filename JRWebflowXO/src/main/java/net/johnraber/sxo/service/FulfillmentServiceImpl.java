package net.johnraber.sxo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import net.johnraber.sxo.model.BillingAgreement;
import net.johnraber.sxo.model.XOSession;


@Service
public class FulfillmentServiceImpl implements FulfillmentService {

	private static final Logger log = LoggerFactory.getLogger(FulfillmentServiceImpl.class);
	
	@Override
	public BillingAgreement update(XOSession xoSession) {
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("Creating/updating a billing agreement in response to a XO Session: " + xoSession.getDisplayString()  );
		
		return new BillingAgreement();
	}

}
