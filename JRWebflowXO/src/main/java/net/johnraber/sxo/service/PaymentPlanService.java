package net.johnraber.sxo.service;

import net.johnraber.sxo.model.PaymentPlan;
import net.johnraber.sxo.model.XOSession;

public interface PaymentPlanService {

	PaymentPlan update( XOSession xoSession );
}
