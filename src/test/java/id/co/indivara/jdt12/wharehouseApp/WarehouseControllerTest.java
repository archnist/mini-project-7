package id.co.indivara.jdt12.wharehouseApp;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.indivara.jdt12.wharehouseApp.controller.SuppToWarehouseController;
import id.co.indivara.jdt12.wharehouseApp.entity.Goods;
import id.co.indivara.jdt12.wharehouseApp.entity.SuppToWarehouse;
import id.co.indivara.jdt12.wharehouseApp.entity.Warehouse;
import id.co.indivara.jdt12.wharehouseApp.repo.SuppToWarehouseRepository;
import id.co.indivara.jdt12.wharehouseApp.repo.WarehouseRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
@SpringBootTest
@AutoConfigureMockMvc
class WarehouseControllerTest{
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    @Autowired
    SuppToWarehouseRepository suppToWarehouseRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

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
                                .content(objectMapper.writeValueAsString(warehouse)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.warehouseId").exists())
                .andExpect(jsonPath("$.warehouseName").value("Wuhan Warehouse"))
                .andExpect(jsonPath("$.location").value("Wuhan"))
                .andExpect(jsonPath("$.date").exists());
    }

    @Test
    public void findSupplierToWarehouseTransaction() throws Exception {

    }

}
