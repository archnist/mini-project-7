package id.co.indivara.jdt12.wharehouseApp;
import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.indivara.jdt12.wharehouseApp.entity.Store;
import id.co.indivara.jdt12.wharehouseApp.repo.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Base64;

@SpringBootTest
@AutoConfigureMockMvc
class StoreControllerTest{
    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    StoreRepository storeRepository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }


    @Test
    public void addStoreTest() throws Exception {
        Store store = new Store();
        store.setStoreName("Brando Store");
        store.setLocation("Jakarta Timur");

        mockMvc.perform(
                        post("/store/register")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(store))
                                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("admin:admin".getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.storeId").exists())
                .andExpect(jsonPath("$.storeName").value(store.getStoreName()))
                .andExpect(jsonPath("$.location").value(store.getLocation()))
                .andExpect(jsonPath("$.date").exists());
    }
    @Test
    public void showAllStores() throws Exception {
        mockMvc.perform(
                        get("/store/show/all")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("whuser:warehouse".getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]storeId").exists())
                .andExpect(jsonPath("$.[0]storeName").exists())
                .andExpect(jsonPath("$.[0]location").exists())
                .andExpect(jsonPath("$.[0]date").exists());
    }
    @Test
    public void showStoreByName() throws Exception {
        Store store = new Store();
        store.setStoreName("Ilham Store");
        mockMvc.perform(
                        get("/store/show/{storeName}",store.getStoreName())
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("whuser:warehouse".getBytes())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]storeId").exists())
                .andExpect(jsonPath("$.[0]storeName").value("Ilham Store"))
                .andExpect(jsonPath("$.[0]location").exists())
                .andExpect(jsonPath("$.[0]date").exists());
    }
}
