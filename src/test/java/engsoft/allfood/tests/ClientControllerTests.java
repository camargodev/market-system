
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
import engsoft.allfood.controller.ClientController;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
@AutoConfigureMockMvc
public class ClientControllerTests {
	
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ClientController client;
    
    
    //Tests if all controllers are not null
    @Test
    public void sanityCheck() throws Exception{
    	assertNotNull(client);
    }
  //INSERT
    @Test
    public void insertClient() throws Exception{
    	//Insertion with complement parameter
    	this.mockMvc.perform(get("/client/insert")
    			.param("name", "Will")
    			.param("cpf", "12")
    			.param("email", "wil@wil.wil")
    			.param("password", "123")
    			.param("autenticated", "true")
    			.param("paymentType", "1")
    			.param("addressId", "1")
    		)
    	.andDo(print()).andExpect(status().isOk())
    	.andExpect(jsonPath("$.name").value("Will"))
    	.andExpect(jsonPath("$.cpf").value("12"))
    	.andExpect(jsonPath("$.email").value("wil@wil.wil"))
    	.andExpect(jsonPath("$.password").value("123"))
    	.andExpect(jsonPath("$.autenticated").value("true"))
    	.andExpect(jsonPath("$.paymentType").value("1"))
    	.andExpect(jsonPath("$.addressId").value("1"));
    	
    }
    //UPDATE
    @Test
    public void updateClient() throws Exception{
    	this.mockMvc.perform(get("/client/update")
    			.param("name", "William")
    			.param("email", "will@wil.wil")
    			.param("paymentType", "2")
    		)
    	.andDo(print()).andExpect(status().isOk())
    	.andExpect(jsonPath("$.name").value("William"))
    	.andExpect(jsonPath("$.cpf").value("12"))
    	.andExpect(jsonPath("$.email").value("wil@wil.wil"))
    	.andExpect(jsonPath("$.password").value("123"))
    	.andExpect(jsonPath("$.autenticated").value("true"))
    	.andExpect(jsonPath("$.paymentType").value("2"))
    	.andExpect(jsonPath("$.addressId").value("1"));
    	
    	this.mockMvc.perform(get("/client/insert")
    			.param("cpf", "13")
    			.param("password", "1234")
    		)
    	.andDo(print()).andExpect(status().isOk())
    	.andExpect(jsonPath("$.name").value("William"))
    	.andExpect(jsonPath("$.cpf").value("13"))
    	.andExpect(jsonPath("$.email").value("wil@wil.wil"))
    	.andExpect(jsonPath("$.password").value("1234"))
    	.andExpect(jsonPath("$.autenticated").value("true"))
    	.andExpect(jsonPath("$.paymentType").value("2"))
    	.andExpect(jsonPath("$.addressId").value("1"));
    	
    	
    	
    }
}
