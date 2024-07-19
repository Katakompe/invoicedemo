package me.komposch.invoicedemo.persistence;

import me.komposch.invoicedemo.server.endpoint.model.Invoice;
import org.jooq.DSLContext;
import org.jooq.Record5;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static me.komposch.invoicedemo.server.persistence.model.Tables.INVOICES;

@Repository
public class H2InvoiceRepository implements InvoiceRepository {

    private final DSLContext context;

    public H2InvoiceRepository(DSLContext context) {
        this.context = context;
    }

    @Override
    public List<Invoice> selectAll() {
        var query = context.select(
                        INVOICES.INVOICE_NUMBER,
                        INVOICES.DUE_DATE,
                        INVOICES.BILLING_AMOUNT,
                        INVOICES.NAME,
                        INVOICES.ADDRESS
                )
                .from(INVOICES);

        return query.fetch(H2InvoiceRepository::toInvoice);
    }

    @Override
    public Optional<Invoice> findByNumber(String invoiceNumber) {
        var query = context.select(
                        INVOICES.INVOICE_NUMBER,
                        INVOICES.DUE_DATE,
                        INVOICES.BILLING_AMOUNT,
                        INVOICES.NAME,
                        INVOICES.ADDRESS
                )
                .from(INVOICES)
                .where(INVOICES.INVOICE_NUMBER.eq(invoiceNumber));

        return query.fetchOptional(H2InvoiceRepository::toInvoice);
    }


    private static Invoice toInvoice(Record5<String, LocalDate, BigDecimal, String, String> record) {
        return new Invoice()
                .invoiceNumber(record.get(INVOICES.INVOICE_NUMBER))
                .dueDate(record.get(INVOICES.DUE_DATE))
                .billingAmount(record.get(INVOICES.BILLING_AMOUNT).doubleValue())
                .name(record.get(INVOICES.NAME))
                .address(record.get(INVOICES.ADDRESS));
    }
}
