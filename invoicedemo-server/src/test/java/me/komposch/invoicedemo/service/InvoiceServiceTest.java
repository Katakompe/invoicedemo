package me.komposch.invoicedemo.service;

import me.komposch.invoicedemo.persistence.InvoiceRepository;
import me.komposch.invoicedemo.server.endpoint.model.Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class InvoiceServiceTest {

    private InvoiceRepository repository;
    private InvoiceService invoiceService;


    @BeforeEach
    void setup() {
        repository = Mockito.mock();
        invoiceService = new InvoiceService(repository);
    }

    @Test
    void invoiceInvoiceNumberGet() {
        when(repository.findByNumber(eq("A"))).thenReturn(
                Optional.of(new Invoice()
                        .invoiceNumber("A")
                        .dueDate(LocalDate.parse("2024-01-01"))
                        .billingAmount(1.00)
                        .name("Max")
                        .address("Somewhere 1, 1010 Wien")
                )
        );

        var result = invoiceService.invoiceInvoiceNumberGet("A");

        assertTrue(result.getStatusCode()
                .isSameCodeAs(HttpStatus.OK)
        );
    }

    @Test
    void invoiceInvoiceNumberGetEmptyResult() {
        when(repository.findByNumber(eq("A"))).thenReturn(
                Optional.empty()
        );


        var result = invoiceService.invoiceInvoiceNumberGet("A");

        assertTrue(result.getStatusCode()
                .isSameCodeAs(HttpStatus.NOT_FOUND)
        );
    }

    @Test
    void invoicesGet() {
        when(repository.selectAll()).thenReturn(
                List.of(new Invoice()
                        .invoiceNumber("A")
                        .dueDate(LocalDate.parse("2024-01-01"))
                        .billingAmount(1.00)
                        .name("Max")
                        .address("Somewhere 1, 1010 Wien")
                )
        );

        var result = invoiceService.invoicesGet();

        assertTrue(result.getStatusCode()
                .isSameCodeAs(HttpStatus.OK)
        );
        assertEquals(1, result.getBody().size());
    }
}