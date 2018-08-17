package engsoft.allfood.tests;

import static org.junit.Assert.assertNotNull;
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
import engsoft.allfood.controller.CategoryController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
@AutoConfigureMockMvc
public class CategoryControllerTests {
	
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CategoryController cat;
    
    
    //Tests if all controllers are not null
    @Test
    public void sanityCheck() throws Exception{
    	assertNotNull(cat);
    }
    
    @Test
    public void insertCategory() throws Exception {

        this.mockMvc.perform(get("/category/insert").param("name", "Alimentos"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Alimentos"));
    }
   
}

