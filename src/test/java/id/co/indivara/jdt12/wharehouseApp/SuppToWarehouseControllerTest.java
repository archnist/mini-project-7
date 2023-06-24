package id.co.indivara.jdt12.wharehouseApp;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.indivara.jdt12.wharehouseApp.entity.*;
import id.co.indivara.jdt12.wharehouseApp.repo.SuppToWarehouseRepository;
import id.co.indivara.jdt12.wharehouseApp.repo.TransactionRepository;
import id.co.indivara.jdt12.wharehouseApp.repo.WarehouseInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class SuppToWarehouseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void SupplaiToWarehouse() throws Exception{
        Warehouse warehouse = new Warehouse();
        warehouse.setWarehouseId("wh1");
        Goods goods = new Goods();
        goods.setGoodsId("kaos");
        SuppToWarehouse suppToWarehouse = new SuppToWarehouse();
        suppToWarehouse.setTotal(1000);

        mockMvc.perform(
                        post("/supp/add/{whouseid}/{goodsid}",warehouse.getWarehouseId(),goods.getGoodsId())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(suppToWarehouse)))
                .andExpect(jsonPath("$.transactionId").exists())
                .andExpect(jsonPath("$.goods.goodsId").value("kaos"))
                .andExpect(jsonPath("$.warehouse.warehouseId").value("wh1"))
                .andExpect(jsonPath("$.total").value(1000))
                .andExpect(jsonPath("$.dateTime").exists());
    }
}
