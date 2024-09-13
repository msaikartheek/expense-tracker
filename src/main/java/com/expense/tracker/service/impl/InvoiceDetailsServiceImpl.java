package com.expense.tracker.service.impl;

import com.expense.tracker.service.IInvoiceDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.util.Random;
@Service
@Slf4j
public class InvoiceDetailsServiceImpl implements IInvoiceDetailsService {


    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int INVOICE_NUMBER_LENGTH = 10;
    private final Random random = new SecureRandom();

    /**
     * @return random invoice number as string
     */
    @Override
    public Mono<String> generateInvoiceNumber() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < INVOICE_NUMBER_LENGTH; i++) {
            int character = random.nextInt(ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return Mono.justOrEmpty(builder.toString());
    }
}
