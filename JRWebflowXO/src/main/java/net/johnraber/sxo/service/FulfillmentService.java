package net.johnraber.sxo.service;

import net.johnraber.sxo.model.BillingAgreement;
import net.johnraber.sxo.model.XOSession;

public interface FulfillmentService {

	BillingAgreement update( XOSession xoSession );
}
