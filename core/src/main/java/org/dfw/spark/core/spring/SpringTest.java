package org.dfw.spark.core.spring;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#testing
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringMain.class})
@WebAppConfiguration
//@Commit
//@Rollback
//@Transactional
public abstract class SpringTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;


    @Before
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    public MockMvc getMockMvc() {
        return mockMvc;
    }
}
