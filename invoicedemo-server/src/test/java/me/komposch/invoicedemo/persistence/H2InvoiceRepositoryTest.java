package me.komposch.invoicedemo.persistence;

import org.jooq.DSLContext;
import org.jooq.Record5;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockResult;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import static me.komposch.invoicedemo.server.persistence.model.Tables.INVOICES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class H2InvoiceRepositoryTest {

    // Mocks Database connection for Jooq
    MockDataProvider provider = ctx -> {
        MockResult[] resultArray = new MockResult[1];
        DSLContext create = DSL.using(SQLDialect.H2);

        Result<Record5<String, LocalDate, BigDecimal, String, String>> result = create.newResult(
                INVOICES.INVOICE_NUMBER,
                INVOICES.DUE_DATE,
                INVOICES.BILLING_AMOUNT,
                INVOICES.NAME,
                INVOICES.ADDRESS
        );

        //single element fetch query with "invalid" invoiceNumber
        if (
                ctx.sql().toUpperCase().contains("WHERE") &&
                        Arrays.stream(ctx.bindings()).noneMatch(v -> v.equals("ATEST"))
    ) {
            resultArray[0]=new MockResult(0, result);
            return resultArray;
        }

        result.add(create
                .newRecord(
                        INVOICES.INVOICE_NUMBER,
                        INVOICES.DUE_DATE,
                        INVOICES.BILLING_AMOUNT,
                        INVOICES.NAME,
                        INVOICES.ADDRESS
                )
                .values("ATEST",
                        LocalDate.parse("2024-01-01"),
                        BigDecimal.valueOf(10.00),
                        "Max Mustermann",
                        "Musterstraße 1, 1020 Wien"
                )
        );
        resultArray[0] = new MockResult(1, result);
        return resultArray;
    };

    MockConnection connection = new MockConnection(provider);
    // DSL Context with connection to mock
    private final DSLContext context = DSL.using(connection, SQLDialect.H2);
    private final H2InvoiceRepository repository = new H2InvoiceRepository(context);

    @Test
    void selectAll() {
        var invoices = repository.selectAll();
        assertEquals(1, invoices.size());
    }

    @Test
    void findByNumber() {
        var invoice = repository.findByNumber("ATEST")
                .orElseThrow(() -> new AssertionError("Optional is expected to contain value but was empty."));

        assertEquals("ATEST", invoice.getInvoiceNumber());
        assertEquals(LocalDate.parse("2024-01-01"), invoice.getDueDate());
        assertEquals(10.00, invoice.getBillingAmount());
        assertEquals("Max Mustermann", invoice.getName());
        assertEquals("Musterstraße 1, 1020 Wien", invoice.getAddress());
    }

    @Test
    void findByNumberEmptyOptional() {
        var invoiceOptional = repository.findByNumber("invalidNumber");

        assertTrue(invoiceOptional.isEmpty());
    }

}