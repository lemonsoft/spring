/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static com.jayway.jsonassert.impl.matcher.IsCollectionWithSize.hasSize;
import java.util.Arrays;

import com.lemonsoft.spring.config.AppConfig;
import com.lemonsoft.spring.dao.CustomerDAO;
import com.lemonsoft.spring.model.Customer;

import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import org.springframework.web.context.WebApplicationContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.MockitoAnnotations.Mock;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import test.util.TestUtil;
/**
 *
 * @author lemonsoft
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
public class CustomerRestControllerTest {

    @Autowired
    private WebApplicationContext ctx;
    private MockMvc mockMvc;

    @Mock
    private CustomerDAO customerDAO;
    
    @InjectMocks
    private CustomerRestControllerTest customerRestControllerTest;

    public CustomerRestControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(ctx).build();
    }
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders
//                .standaloneSetup(customerRestControllerTest)
//                .addFilters(new CORSFilter())
//                .build();
    }

    @Test
    public void testCustomerController() throws Exception {

        Customer c1 = new Customer();
        c1.setId(101L);
        c1.setFirstName("John");
        c1.setLastName("Doe");
        c1.setEmail("djohn@gmail.com");
        c1.setMobile("121-232-3435");
        Customer c2 = new Customer();
        c2.setId(201L);
        c2.setFirstName("Russ");
        c2.setLastName("Smith");
        c2.setEmail("sruss@gmail.com");
        c2.setMobile("343-545-2345");

        when(customerDAO.list()).thenReturn(Arrays.asList(c1, c2));

        mockMvc.perform(get("/customers"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(101)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[0].lastName", is("Doe")))
                .andExpect(jsonPath("$[0].email", is("djohn@gmail.com")))
                .andExpect(jsonPath("$[1].id", is(201)))
                .andExpect(jsonPath("$[1].firstName", is("Russ")))
                .andExpect(jsonPath("$[1].lastName", is("Smith")))
                .andExpect(jsonPath("$[1].email", is("sruss@gmail.com")))
                .andExpect(jsonPath("$[1].mobile", is("343-545-2345")));

        //verify(customerDAO, times(1)).list();
        //verifyNoMoreInteractions(customerDAO);
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
