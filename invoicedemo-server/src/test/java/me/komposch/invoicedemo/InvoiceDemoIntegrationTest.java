package me.komposch.invoicedemo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.komposch.invoicedemo.server.endpoint.model.Invoice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class InvoiceDemoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Sql(scripts = {"test-clear.sql", "test-data.sql"})
    @Test
    void invoicesCallReceives2Elements() throws Exception {
        var result = this.mockMvc.perform(
                        get("/invoices")
                )
                .andExpect(status().isOk())
                .andReturn();


        var invoiceList = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<Invoice>>() {
                }
        );

        assertEquals(2, invoiceList.size());
    }


    @Sql(scripts = {"test-clear.sql", "test-data.sql"})
    @Test
    void invoiceCallWithValidNumberGetsCorrectElement() throws Exception {
        var result = this.mockMvc.perform(
                        get("/invoice/ATEST")
                )
                .andExpect(status().isOk())
                .andReturn();

        var invoice = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<Invoice>() {
                }
        );

        assertEquals("ATEST", invoice.getInvoiceNumber());
        assertEquals(LocalDate.parse("2024-01-01"), invoice.getDueDate());
        assertEquals(10.00, invoice.getBillingAmount());
        assertEquals("Max Mustermann", invoice.getName());
        assertEquals("Musterstraße 1, 1020 Wien", invoice.getAddress());
    }

    @Sql(scripts = {"test-clear.sql", "test-data.sql"})
    @Test
    void invoiceCallWithInvalidNumberGetsError404() throws Exception {
        this.mockMvc.perform(get("/invoice/invalidID"))
                .andExpect(status().isNotFound());
    }

}