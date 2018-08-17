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
import engsoft.allfood.controller.PurchaseController;
@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
@AutoConfigureMockMvc
public class PurchaseControllerTests {
	
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PurchaseController purch;
    
    
    //Tests if all controllers are not null
    @Test
    public void sanityCheck() throws Exception{
    	assertNotNull(purch);
    }
    
    @Test
    public void insertPurchase() throws Exception{
    	//Insertion with complement parameter
    	this.mockMvc.perform(get("/purchase/insert")
    			.param("clientId", "1")
    			.param("deliveryType", "1")
    		)
    	.andDo(print()).andExpect(status().isOk())
    	.andExpect(jsonPath("$.clientId").value("1"))
    	.andExpect(jsonPath("$.deliveryType").value("1"));
    	
    	
    	
    }
    
    //LACKING TESTS TO processpurchase, cancelpurchase, getitemsbypurchase and finishpurchase
}
