package id.co.indivara.jdt12.wharehouseApp;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.indivara.jdt12.wharehouseApp.entity.Warehouse;
import id.co.indivara.jdt12.wharehouseApp.repo.SuppToWarehouseRepository;
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
}
