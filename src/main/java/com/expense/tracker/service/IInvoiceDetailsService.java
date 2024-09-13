package com.expense.tracker.service;

import reactor.core.publisher.Mono;

public interface IInvoiceDetailsService {
   Mono<String> generateInvoiceNumber();
}
