package engsoft.allfood.tests;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import engsoft.allfood.application.Application;
import engsoft.allfood.controller.PurchaseItemController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
@AutoConfigureMockMvc
public class PurchaseItemControllerTests {
	
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PurchaseItemController pItem;
    
    
    //Tests if all controllers are not null
    @Test
    public void sanityCheck() throws Exception{
    	assertNotNull(pItem);
    }
    
    @Test
    public void insertPItem() throws Exception {

        this.mockMvc.perform(get("/purchaseitem/insert")
        		.param("productId", "1")
        		.param("quantity", "12")
        		.param("purchaseId", "1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value("1"))
                .andExpect(jsonPath("$.quantity").value("12"))
                .andExpect(jsonPath("$.purchaseId").value("1"));
    }
    
    @Test
    public void updatePItem() throws Exception{
    	this.mockMvc.perform(get("/purchaseitem/updatequantity")
        		.param("purchaseitemid", "1")
        		.param("quantity", "13"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value("1"))
                .andExpect(jsonPath("$.quantity").value("13"))
                .andExpect(jsonPath("$.purchaseId").value("1"));

    	
    	
    	
    }
    
    //LACKING TEST TO delete
}
