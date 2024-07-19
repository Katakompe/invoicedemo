DROP TABLE IF EXISTS invoices;

CREATE TABLE invoices (
                          invoice_number  VARCHAR(255)  NOT NULL PRIMARY KEY,
                          due_date        DATE          NOT NULL,
                          billing_amount  DECIMAL(25, 2)       NOT NULL,
                          name            VARCHAR(255)  NOT NULL,
                          address         VARCHAR(255)  NOT NULL
);
