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
import engsoft.allfood.controller.AddressController;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
@AutoConfigureMockMvc
public class AddressControllerTests {
	
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AddressController addr;
   
    @Test
    public void sanityCheck() throws Exception{
    	assertNotNull(addr);
    }
    
    //INSERT
    @Test
    public void insertAddress() throws Exception{
    	//Insertion with complement parameter
    	this.mockMvc.perform(get("/address/insert")
    			.param("country", "BR")
    			.param("state", "RS")
    			.param("city", "POA")
    			.param("street", "A")
    			.param("neighborhood", "B")
    			.param("houseNumber", "2")
    			.param("cep", "3")
    			.param("complement", "abc")
    		)
    	.andDo(print()).andExpect(status().isOk())
    	.andExpect(jsonPath("$.country").value("BR"))
    	.andExpect(jsonPath("$.state").value("RS"))
    	.andExpect(jsonPath("$.city").value("POA"))
    	.andExpect(jsonPath("$.street").value("A"))
    	.andExpect(jsonPath("$.neighborhood").value("B"))
    	.andExpect(jsonPath("$.complement").value("abc"))
    	.andExpect(jsonPath("$.houseNumber").value("2"))
    	.andExpect(jsonPath("$.cep").value("3"));
    	
    	//Insertion without complement parameter
    	this.mockMvc.perform(get("/address/insert")
    			.param("country", "BR")
    			.param("state", "RS")
    			.param("city", "SaoLeo")
    			.param("street", "C")
    			.param("neighborhood", "D")
    			.param("houseNumber", "4")
    			.param("cep", "5")
    		)
    	.andDo(print()).andExpect(status().isOk())
    	.andExpect(jsonPath("$.country").value("BR"))
    	.andExpect(jsonPath("$.state").value("RS"))
    	.andExpect(jsonPath("$.city").value("SaoLeo"))
    	.andExpect(jsonPath("$.street").value("C"))
    	.andExpect(jsonPath("$.neighborhood").value("D"))
    	.andExpect(jsonPath("$.complement").value(""))
    	.andExpect(jsonPath("$.houseNumber").value("4"))
    	.andExpect(jsonPath("$.cep").value("5"));
    	
    	
    	
    }
    //UPDATE
    @Test
    public void updateAddress() throws Exception{
    	this.mockMvc.perform(get("/address/update")
    			.param("id", "1")
    			.param("country", "Brasil")
    			.param("street", "B")
    			.param("houseNumber", "13")
    			.param("cep", "92")
    		)
    	.andDo(print()).andExpect(status().isOk())
    	.andExpect(jsonPath("$.country").value("Brasil"))
    	.andExpect(jsonPath("$.state").value("RS"))
    	.andExpect(jsonPath("$.city").value("POA"))
    	.andExpect(jsonPath("$.street").value("B"))
    	.andExpect(jsonPath("$.neighborhood").value("B"))
    	.andExpect(jsonPath("$.complement").value("abc"))
    	.andExpect(jsonPath("$.houseNumber").value("13"))
    	.andExpect(jsonPath("$.cep").value("92"));
    	
    	this.mockMvc.perform(get("/address/update")
    			.param("id", "2")
    			.param("state", "Rio Grande do Sul")
    			.param("city", "Sao Leo")
    			.param("neighborhood", "E")
    			.param("complement", "def")
    		)
    	.andDo(print()).andExpect(status().isOk())
    	.andExpect(jsonPath("$.country").value("BR"))
    	.andExpect(jsonPath("$.state").value("Rio Grande do Sul"))
    	.andExpect(jsonPath("$.city").value("Sao Leo"))
    	.andExpect(jsonPath("$.street").value("C"))
    	.andExpect(jsonPath("$.neighborhood").value("E"))
    	.andExpect(jsonPath("$.complement").value("def"))
    	.andExpect(jsonPath("$.houseNumber").value("4"))
    	.andExpect(jsonPath("$.cep").value("5"));
    	
    	
    	
    }
}
