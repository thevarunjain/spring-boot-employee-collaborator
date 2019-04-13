package edu.sjsu.cmpe275.web;

import edu.sjsu.cmpe275.Main;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class EmployerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql("/db/employer_with_existing_employee.sql")
    public void deleteEmployer_withMoreThanOneEmployee_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(delete("/employer/1"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}