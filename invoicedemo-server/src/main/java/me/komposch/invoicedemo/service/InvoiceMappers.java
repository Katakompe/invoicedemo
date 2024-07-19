package me.komposch.invoicedemo.service;

import me.komposch.invoicedemo.server.endpoint.model.Invoice;
import me.komposch.invoicedemo.server.endpoint.model.InvoiceListItem;

/**
 * Helper class to map Invoices to InvoiceListItems.
 */
public final class InvoiceMappers {

    private InvoiceMappers() {
        throw new AssertionError("No instances should exist of this class.");
    }

    /**
     * Maps an {@link Invoice} to an {@link InvoiceListItem}.
     * @param invoice The Invoice to map.
     * @return the mapped InvoiceListItem.
     */
    static InvoiceListItem toInvoiceListItem(Invoice invoice) {
        if(invoice == null) {
            return null;
        }

        return new InvoiceListItem()
                .invoiceNumber(invoice.getInvoiceNumber())
                .billingAmount(invoice.getBillingAmount())
                .dueDate(invoice.getDueDate());
    }

}
