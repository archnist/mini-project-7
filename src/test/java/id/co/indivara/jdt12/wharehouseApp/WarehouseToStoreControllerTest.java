package id.co.indivara.jdt12.wharehouseApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.indivara.jdt12.wharehouseApp.entity.Goods;
import id.co.indivara.jdt12.wharehouseApp.entity.Store;
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
public class WarehouseToStoreControllerTest {
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
        Store storeDest = new Store();
        storeDest.setStoreId("str1");
        Goods goods = new Goods();
        goods.setGoodsId("roti");
        WarehouseToWarehouse warehouseToWarehouse = new WarehouseToWarehouse();
        warehouseToWarehouse.setTotal(400);

        mockMvc.perform(
                        post("/warehouse/delivery/{goodsId}/{whouseSrc}/to/{storeDest}",goods.getGoodsId(),warehouseSrc.getWarehouseId(),storeDest.getStoreId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(warehouseToWarehouse))
                                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("whuser:warehouse".getBytes())))
                .andExpect(jsonPath("$.transactionId").exists())
                .andExpect(jsonPath("$.warehouseSrc.warehouseId").value("wh1"))
                .andExpect(jsonPath("$.goods.goodsId").value("roti"))
                .andExpect(jsonPath("$.storeDest.storeId").value("str1"))
                .andExpect(jsonPath("$.total").value(400))
                .andExpect(jsonPath("$.dateTime").exists());
    }
}
