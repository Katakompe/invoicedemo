package me.komposch.invoicedemo.service;


import me.komposch.invoicedemo.persistence.InvoiceRepository;
import me.komposch.invoicedemo.server.endpoint.InvoiceApiDelegate;
import me.komposch.invoicedemo.server.endpoint.model.Invoice;
import me.komposch.invoicedemo.server.endpoint.model.InvoiceListItem;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the API Delegate Interface responsible for handling invoice API calls.
 * Implements methods to fetch Invoice data.
 */
@Service
public class InvoiceService implements InvoiceApiDelegate {

    private final InvoiceRepository repository;

    public InvoiceService(InvoiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<Invoice> invoiceInvoiceNumberGet(String invoiceNumber) {
        if (invoiceNumber == null) {
            return ResponseEntity.notFound().build();
        }

        return repository.findByNumber(invoiceNumber)
                .map(invoice -> ResponseEntity.ok()
                        .header("Content-Type", "application/json;charset=utf-8")
                        .body(invoice)
                )
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<InvoiceListItem>> invoicesGet() {
        return ResponseEntity.ok()
                .header("Content-Type", "application/json;charset=utf-8")
                .body(repository.selectAll()
                        .stream()
                        .map(InvoiceMappers::toInvoiceListItem)
                        .toList()
                );
    }
}