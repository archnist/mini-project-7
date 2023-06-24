package id.co.indivara.jdt12.wharehouseApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.indivara.jdt12.wharehouseApp.entity.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private WebApplicationContext webApplicationContext;
    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void showAllTransaction() throws Exception {
        mockMvc.perform(
                        get("/transaction/all")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]transactionId").exists())
                .andExpect(jsonPath("$.[0]type").exists())
                .andExpect(jsonPath("$.[0]goods.goodsId").exists())
                .andExpect(jsonPath("$.[0]dateTime").exists());
    }
    @Test
    public void showTransactionByType() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setType("Warehouse To Warehouse");

        mockMvc.perform(
                        get("/transaction/{type}",transaction.getType())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]transactionId").exists())
                .andExpect(jsonPath("$.[0]type").exists())
                .andExpect(jsonPath("$.[0]goods.goodsId").exists())
                .andExpect(jsonPath("$.[0]dateTime").exists());
    }
    @Test
    public void showSuppToWarehouseTransaction() throws Exception {
        mockMvc.perform(
                        get("/transaction/suppToWhouse")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]transactionId").exists())
                .andExpect(jsonPath("$.[0]warehouse.warehouseId").exists())
                .andExpect(jsonPath("$.[0]goods.goodsId").exists())
                .andExpect(jsonPath("$.[0]total").exists())
                .andExpect(jsonPath("$.[0]dateTime").exists());
    }
    @Test
    public void showWarehouseToWarehouseTransaction() throws Exception {
        mockMvc.perform(
                        get("/transaction/whouseToWhouse")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]transactionId").exists())
                .andExpect(jsonPath("$.[0]warehouseSource.warehouseId").exists())
                .andExpect(jsonPath("$.[0]warehouseDestination.warehouseId").exists())
                .andExpect(jsonPath("$.[0]goods.goodsId").exists())
                .andExpect(jsonPath("$.[0]total").exists())
                .andExpect(jsonPath("$.[0]dateTime").exists());
    }
    @Test
    public void showWarehouseToStoreTransaction() throws Exception {
        mockMvc.perform(
                        get("/transaction/whouseToStore")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]transactionId").exists())
                .andExpect(jsonPath("$.[0]warehouseSrc.warehouseId").exists())
                .andExpect(jsonPath("$.[0]storeDest.storeId").exists())
                .andExpect(jsonPath("$.[0]goods.goodsId").exists())
                .andExpect(jsonPath("$.[0]total").exists())
                .andExpect(jsonPath("$.[0]dateTime").exists());
    }
}
