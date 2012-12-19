package net.johnraber.sxo.service;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.johnraber.sxo.model.BillingAgreement;
import net.johnraber.sxo.model.PaymentPlan;
import net.johnraber.sxo.model.XOSession;


@Service
public class XOParallelUpdateServiceImpl implements XOUpdateService {

	
	private static int NTHREDS = Runtime.getRuntime().availableProcessors();
	
	private static final Logger log = LoggerFactory.getLogger(XOParallelUpdateServiceImpl.class);
	
	
	@Autowired
	private PaymentPlanService paymentPlanService;
	
	@Autowired
	private FulfillmentService fulfillmentService;

	
	public Boolean updateSession( XOSession xoSession )
	{
	
	    final XOSession xoSessionCopy = xoSession;
	    
		ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
		
        Callable<PaymentPlan> planWorker = new Callable<PaymentPlan>(){
 		  	public PaymentPlan call() throws Exception
 		  	{
 		  		return paymentPlanService.update( xoSessionCopy );
 		  	}
    	};
    	
    	Callable<BillingAgreement> fulfillmentWorker = new Callable<BillingAgreement>() {
 		  	public BillingAgreement call() throws Exception
 		  	{
 		  		return fulfillmentService.update( xoSessionCopy );
 		  	}
    	};
    	
	    
    	// You could read this in at runtime or a static config file but keep in mind
    	// this value should really be part of the SLA of the service and therefore
    	// declared in the service interface
	    long timeoutVal = 500;
	    TimeUnit timeoutUnit = TimeUnit.MILLISECONDS;
	    
	    PaymentPlan paymentPlan = null;
	    BillingAgreement billingAgreement = null;
	    
	   try
	   {
	    	Future<PaymentPlan> planSubmit = executor.submit(planWorker);
	    	Future<BillingAgreement> fulfillmentSubmit = executor.submit(fulfillmentWorker);
		   
	    	
	    	paymentPlan = planSubmit.get(timeoutVal, timeoutUnit);
	    	billingAgreement = fulfillmentSubmit.get(timeoutVal, timeoutUnit);
		  
	   }
	   catch (InterruptedException e) {
		   log.error("Unable to compete one or more of the tasks for updating XO session", e);
	   }
	   catch (ExecutionException e) {
		   log.error("Unable to compete one or more of the tasks for updating XO session", e);
	   }
	   catch(CancellationException e)
	   {
		   // this can be thrown on the get() call on each task
		   log.error("Cancel .... Unable to compete one or more of the tasks for updating XO session", e);
	   }
	   catch (TimeoutException e)
	   {
		   log.error("Timeout ..... Unable to compete one or more of the tasks for updating XO session", e);
	   }
	   finally
	   {
	    executor.shutdownNow();
	   }
	   
	   if( paymentPlan != null && billingAgreement != null )
	   {
		   return true;
	   }
	   else
	   {
		   return false;
	   }
	}
}
