package id.co.indivara.jdt12.wharehouseApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.indivara.jdt12.wharehouseApp.entity.*;
import id.co.indivara.jdt12.wharehouseApp.repo.WarehouseInventoryRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WarehouseToWarehouseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    WarehouseInventoryRepository warehouseInventoryRepository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

    @Test
    public void warehouseToWarehouse() throws Exception {
        Warehouse warehouseSrc = new Warehouse();
        warehouseSrc.setWarehouseId("wh1");
        Warehouse warehouseDest = new Warehouse();
        warehouseDest.setWarehouseId("wh2");
        Goods goods = new Goods();
        goods.setGoodsId("roti");
        WarehouseToWarehouse warehouseToWarehouse = new WarehouseToWarehouse();
        warehouseToWarehouse.setTotal(1000);

        mockMvc.perform(
                        post("/warehouse/transfer/{goodsId}/{source}/to/{destination}", goods.getGoodsId(), warehouseSrc.getWarehouseId(), warehouseDest.getWarehouseId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(warehouseToWarehouse))
                                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("whuser:warehouse".getBytes())))
                .andExpect(jsonPath("$.transactionId").exists())
                .andExpect(jsonPath("$.warehouseSource.warehouseId").value("wh1"))
                .andExpect(jsonPath("$.goods.goodsId").value("roti"))
                .andExpect(jsonPath("$.warehouseDestination.warehouseId").value("wh2"))
                .andExpect(jsonPath("$.total").value(1000))
                .andExpect(jsonPath("$.dateTime").exists());
    }

    @Test
    public void warehouseSourceInventoryCheck() throws Exception {
        Warehouse warehouseSrc = new Warehouse();
        warehouseSrc.setWarehouseId("wh1");
        Warehouse warehouseDest = new Warehouse();
        warehouseDest.setWarehouseId("wh2");
        Goods goods = new Goods();
        goods.setGoodsId("roti");
        WarehouseInventory warehouseInventory = warehouseInventoryRepository.findByGoodsAndWarehouse(goods, warehouseSrc);
        mockMvc.perform(
                        get("/warehouse/find/{warehouseId}/{goodsId}", warehouseSrc.getWarehouseId(), goods.getGoodsId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("whuser:warehouse".getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.warehouse.warehouseId").value("wh1"))
                .andExpect(jsonPath("$.goods.goodsId").value("roti"))
                .andExpect(jsonPath("$.stock").value(warehouseInventory.getStock()));
    }

    @Test
    public void warehouseDestinationInventoryCheck() throws Exception {
        Warehouse warehouseSrc = new Warehouse();
        warehouseSrc.setWarehouseId("wh1");
        Warehouse warehouseDest = new Warehouse();
        warehouseDest.setWarehouseId("wh2");
        Goods goods = new Goods();
        goods.setGoodsId("roti");
        WarehouseInventory warehouseInventory = warehouseInventoryRepository.findByGoodsAndWarehouse(goods, warehouseDest);
        mockMvc.perform(
                        get("/warehouse/find/{warehouseId}/{goodsId}", warehouseDest.getWarehouseId(), goods.getGoodsId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("whuser:warehouse".getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.warehouse.warehouseId").value("wh2"))
                .andExpect(jsonPath("$.goods.goodsId").value("roti"))
                .andExpect(jsonPath("$.stock").value(warehouseInventory.getStock()));
    }
}
