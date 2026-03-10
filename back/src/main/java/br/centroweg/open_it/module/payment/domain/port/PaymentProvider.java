package br.centroweg.open_it.module.payment.domain.port;

import br.centroweg.open_it.module.payment.domain.model.PaymentInfo;

public interface PaymentProvider {

    PaymentInfo generatePayment(double amount, String internalPaymentId);

    String fetchStatus(String mercadoPagoPaymentId);

    String getExternalReference(String mercadoPagoPaymentId);

}
