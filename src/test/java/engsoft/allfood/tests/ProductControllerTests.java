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
import engsoft.allfood.controller.ProductController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
@AutoConfigureMockMvc
public class ProductControllerTests {
	
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductController prod;
    
    
    @Test
    public void sanityCheck() throws Exception{
    	assertNotNull(prod);
    }
    
    @Test
    public void insertProduct() throws Exception{
    	//Insertion with complement parameter
    	this.mockMvc.perform(get("/product/insert")
    			.param("name", "air")
    			.param("price", "0.2")
    			.param("quantityInStock", "200000")
    			.param("categoryId", "1")
    		)
    	.andDo(print()).andExpect(status().isOk())
    	.andExpect(jsonPath("$.name").value("air"))
    	.andExpect(jsonPath("$.price").value("0.2"))
    	.andExpect(jsonPath("$.quantityInStock").value("200000"))
    	.andExpect(jsonPath("$.categoryId").value("1"));
    	
    	
    	
    }
  //UPDATE
    @Test
    public void updateClient() throws Exception{
    	this.mockMvc.perform(get("/product/update")
    			.param("price", "0.21")
    			.param("quantityInStock", "190000")
    			)
    	.andDo(print()).andExpect(status().isOk())
    	.andExpect(jsonPath("$.name").value("air"))
    	.andExpect(jsonPath("$.price").value("0.21"))
    	.andExpect(jsonPath("$.quantityInStock").value("190000"))
    	.andExpect(jsonPath("$.categoryId").value("1"));
    	
    	
    	
    }
}
