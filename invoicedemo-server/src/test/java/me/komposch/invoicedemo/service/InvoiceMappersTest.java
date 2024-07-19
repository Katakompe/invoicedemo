package me.komposch.invoicedemo.service;

import me.komposch.invoicedemo.server.endpoint.model.Invoice;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class InvoiceMappersTest {

    @Test
    void toInvoiceListItem() {
        var source = new Invoice()
                .invoiceNumber("A")
                .billingAmount(2.00)
                .dueDate(LocalDate.parse("2020-01-01"))
                .name("Max")
                .address("Hier");

        var result = InvoiceMappers.toInvoiceListItem(source);

        assertEquals(source.getInvoiceNumber(), result.getInvoiceNumber());
        assertEquals(source.getBillingAmount(), result.getBillingAmount());
        assertEquals(source.getDueDate(), result.getDueDate());
    }


    @Test
    void toInvoiceListItemNull() {
        assertNull(InvoiceMappers.toInvoiceListItem(null));
    }

}