package me.komposch.invoicedemo.persistence;

import me.komposch.invoicedemo.server.endpoint.model.Invoice;

import java.util.List;
import java.util.Optional;

/**
 * Repository to fetch invoice information from a data source.
 */
public interface InvoiceRepository {

    /**
     * Fetches all available invoices.
     * @return a list of all invoices in the data source.
     */
    List<Invoice> selectAll();

    /**
     * Fetches the invoice with the passed number.
     * @param invoiceNumber the number of the invoice to fetch.
     * @return an {@link Optional<Invoice>} with the found invoice or an empty
     * one if no invoice can be found.
     */
    Optional<Invoice> findByNumber(String invoiceNumber);
}
