package id.co.indivara.jdt12.wharehouseApp;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.indivara.jdt12.wharehouseApp.entity.Warehouse;
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
public class WarehouseInventoryControllerTest {
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
    public void showInventory() throws Exception {
        mockMvc.perform(
                        get("/warehouse/find/all")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]id").exists())
                .andExpect(jsonPath("$.[0]goods.goodsId").exists())
                .andExpect(jsonPath("$.[0]warehouse.warehouseId").exists())
                .andExpect(jsonPath("$.[0]stock").exists());
    }

    @Test
    public void showInventoryByWarehouse() throws Exception {
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseId("wh1");

        mockMvc.perform(
                        get("/warehouse/find/{warehouse}",warehouse.getWarehouseId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("whuser:warehouse".getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]id").exists())
                .andExpect(jsonPath("$.[0]goods.goodsId").exists())
                .andExpect(jsonPath("$.[0]warehouse.warehouseId").value(warehouse.getWarehouseId()))
                .andExpect(jsonPath("$.[0]stock").exists());
    }
}
