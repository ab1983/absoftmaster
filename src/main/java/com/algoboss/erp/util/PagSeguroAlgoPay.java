/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.util;

import br.com.uol.pagseguro.domain.AccountCredentials;
import br.com.uol.pagseguro.domain.Currency;
import br.com.uol.pagseguro.domain.PaymentRequest;
import br.com.uol.pagseguro.exception.PagSeguroServiceException;
import java.math.BigDecimal;
import java.net.URL;

/**
 *
 * @author Agnaldo
 */
public class PagSeguroAlgoPay {

    PaymentRequest paymentRequest = new PaymentRequest();

    public void pay() {
        try {
            paymentRequest.setReference("REF1234");
            paymentRequest.setSender("José Comprador", "comprador@uol.com.br", "11", "56273440");
            //paymentRequest.setRedirectURL(null);
            paymentRequest.setCurrency(Currency.BRL);
            paymentRequest.addItem(  
                "0001",  
                "Notebook Prata",   
                new Integer(1),   
                new BigDecimal("2430.00"),   
                new Long(1000),   
                null  
            );              
            URL paymentURL = paymentRequest.register(new AccountCredentials(
                    "loginValido@dominio.com.br",
                    "1234567890ABCDEF1234567890ABCDEF"));
            
        } catch (PagSeguroServiceException ex) {
            System.err.println(ex.toString());  
            //Logger.getLogger(PagSeguroAlgoPay.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
