package id.co.indivara.jdt12.wharehouseApp;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.indivara.jdt12.wharehouseApp.entity.Store;
import id.co.indivara.jdt12.wharehouseApp.repo.StoreRepository;
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
class StoreControllerTest{
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    StoreRepository storeRepository;

    @Test
    public void addStoreTest() throws Exception {
        Store store = new Store();
        store.setStoreName("Ilham Store");
        store.setLocation("Jakarta Timur");

        mockMvc.perform(
                        post("/store/register")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(store)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.storeId").exists())
                .andExpect(jsonPath("$.storeName").value("Ilham Store"))
                .andExpect(jsonPath("$.location").value("Jakarta Timur"))
                .andExpect(jsonPath("$.date").exists());
    }

}
