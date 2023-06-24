package id.co.indivara.jdt12.wharehouseApp;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.indivara.jdt12.wharehouseApp.entity.Warehouse;
import id.co.indivara.jdt12.wharehouseApp.repo.SuppToWarehouseRepository;
import id.co.indivara.jdt12.wharehouseApp.repo.TransactionRepository;
import id.co.indivara.jdt12.wharehouseApp.repo.WarehouseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Base64;
import java.util.Date;
@SpringBootTest
@AutoConfigureMockMvc
class WarehouseControllerTest{
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    @Autowired
    SuppToWarehouseRepository suppToWarehouseRepository;
    @Autowired
    WarehouseRepository warehouseRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void addWarehouseTest() throws Exception {
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseId("wh" + (warehouseRepository.count() + 1));
        warehouse.setWarehouseName("Wuhan Warehouse");
        warehouse.setLocation("Wuhan");
        warehouse.setDate(new Date());

        mockMvc.perform(
                        post("/warehouse/register")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(warehouse))
                                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.warehouseId").exists())
                .andExpect(jsonPath("$.warehouseName").value(warehouse.getWarehouseName()))
                .andExpect(jsonPath("$.location").value(warehouse.getLocation()))
                .andExpect(jsonPath("$.date").exists());
    }
    @Test
    public void showAllWarehouses() throws Exception {
        mockMvc.perform(
                        get("/warehouse/show/all")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("whuser:warehouse".getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]warehouseId").exists())
                .andExpect(jsonPath("$.[0]warehouseName").exists())
                .andExpect(jsonPath("$.[0]location").exists())
                .andExpect(jsonPath("$.[0]date").exists());
    }
    @Test
    public void showWarehousesByName() throws Exception {
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseName("Jaya Warehouse");
        mockMvc.perform(
                        get("/warehouse/show/{warehouseName}",warehouse.getWarehouseName())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("whuser:warehouse".getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]warehouseId").exists())
                .andExpect(jsonPath("$.[0]warehouseName").value("Jaya Warehouse"))
                .andExpect(jsonPath("$.[0]location").exists())
                .andExpect(jsonPath("$.[0]date").exists());
    }
    @Test
    public void showSuppToWarehouseTransaction() throws Exception {
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseId("wh1");
        mockMvc.perform(
                        get("/warehouse/transaction/suppToWarehouse/{warehouse}",warehouse.getWarehouseId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("whuser:warehouse".getBytes())))
                .andExpect(status().isOk());
    }
    @Test
    public void showWarehouseToWarehouseTransaction() throws Exception {
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseId("wh1");
        mockMvc.perform(
                        get("/warehouse/transaction/warehouseToWarehouse/{warehouse}",warehouse.getWarehouseId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("whuser:warehouse".getBytes())))
                .andExpect(status().isOk());
    }
    @Test
    public void showWarehouseToStoreTransaction() throws Exception {
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseId("wh1");
        mockMvc.perform(
                        get("/warehouse/transaction/warehouseToStore/{warehouse}",warehouse.getWarehouseId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("whuser:warehouse".getBytes())))
                .andExpect(status().isOk());
    }
    @Test
    public void showWarehouseTransaction() throws Exception {
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseId("wh1");
        mockMvc.perform(
                        get("/warehouse/transaction/{warehouse}",warehouse.getWarehouseId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("whuser:warehouse".getBytes())))
                .andExpect(status().isOk());
    }
}