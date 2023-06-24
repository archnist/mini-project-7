package id.co.indivara.jdt12.wharehouseApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.indivara.jdt12.wharehouseApp.entity.Goods;
import id.co.indivara.jdt12.wharehouseApp.entity.SuppToWarehouse;
import id.co.indivara.jdt12.wharehouseApp.entity.Warehouse;
import id.co.indivara.jdt12.wharehouseApp.entity.WarehouseToWarehouse;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class WarehouseToWarehouseControllerTest {
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
    public void WarehouseToWarehouse() throws Exception{
        Warehouse warehouseSrc = new Warehouse();
        warehouseSrc.setWarehouseId("wh1");
        Warehouse warehouseDest = new Warehouse();
        warehouseDest.setWarehouseId("wh2");
        Goods goods = new Goods();
        goods.setGoodsId("kaos");
        WarehouseToWarehouse warehouseToWarehouse = new WarehouseToWarehouse();
        warehouseToWarehouse.setTotal(500);

        mockMvc.perform(
                        post("/warehouse/transfer/{goodsId}/{source}/to/{destination}",goods.getGoodsId(),warehouseSrc.getWarehouseId(),warehouseDest.getWarehouseId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(warehouseToWarehouse))
                                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("whuser:warehouse".getBytes())))
                .andExpect(jsonPath("$.transactionId").exists())
                .andExpect(jsonPath("$.warehouseSource.warehouseId").value("wh1"))
                .andExpect(jsonPath("$.goods.goodsId").value("kaos"))
                .andExpect(jsonPath("$.warehouseDestination.warehouseId").value("wh2"))
                .andExpect(jsonPath("$.total").value(500))
                .andExpect(jsonPath("$.dateTime").exists());
    }
}
