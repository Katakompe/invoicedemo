package me.komposch.invoicedemo.persistence;

import org.jooq.DSLContext;
import org.jooq.Record5;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.tools.jdbc.MockConnection;
import org.jooq.tools.jdbc.MockDataProvider;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockResult;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static me.komposch.invoicedemo.server.persistence.model.Tables.INVOICES;

class H2InvoiceRepositoryTest {

    // Mocks Database connection for Jooq
    MockDataProvider provider = new MockDataProvider() {
        @Override
        public MockResult[] execute(MockExecuteContext ctx) {
            DSLContext create = DSL.using(SQLDialect.H2);
            MockResult[] resultArray = new MockResult[1];

            Result<Record5<String, LocalDate, BigDecimal, String, String>> result = create.newResult(
                    INVOICES.INVOICE_NUMBER,
                    INVOICES.DUE_DATE,
                    INVOICES.BILLING_AMOUNT,
                    INVOICES.NAME,
                    INVOICES.ADDRESS
            );

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
        }
    };

    MockConnection connection = new MockConnection(provider);
    // DSL Context with connection to mock
    private final DSLContext context = DSL.using(connection, SQLDialect.H2);
    private final H2InvoiceRepository repository = new H2InvoiceRepository(context);

    @Test
    void selectAll() {
        //TODO: Implement
    }

    @Test
    void findByNumber() {
        //TODO Implement
    }

    @Test
    void findByNumberEmptyOptional() {
        //TODO IMPLEMENT
    }
}