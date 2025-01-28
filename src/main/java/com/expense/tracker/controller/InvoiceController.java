package com.expense.tracker.controller;

import com.expense.tracker.service.IInvoiceDetailsService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/invoices")
@AllArgsConstructor
@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class InvoiceController {

    IInvoiceDetailsService invoiceDetailsService;

    @GetMapping(value = "/generate/invoice-number", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Mono<String>> generateInvoiceNumber() {
        return ResponseEntity.ok(invoiceDetailsService.generateInvoiceNumber());
    }
}
