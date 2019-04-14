package edu.sjsu.cmpe275.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sjsu.cmpe275.Main;
import edu.sjsu.cmpe275.domain.entity.Employee;
import edu.sjsu.cmpe275.domain.repository.EmployeeRepository;
import edu.sjsu.cmpe275.web.model.response.EmployeeDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createEmployee_on_valid_parameter_should_save_employee() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(post("/employee?name=rahul&email=pawarrb@gmail.com&employerId=100"))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andReturn();

        final EmployeeDto employeeResponseDto = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                EmployeeDto.class
        );

        final Optional<Employee> employee = employeeRepository.findById(employeeResponseDto.getId());

        assertTrue(employee.isPresent());
    }

    @Test
    public void createEmployee_on_invalid_email_should_return_bad_request() throws Exception {
        mockMvc.perform(post("/employee?name=rahul&email=pawarrbgmail.com&employerId=100"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void createEmployee_with_existing_email_should_return_bad_request() throws Exception {
        mockMvc.perform(post("/employee?name=rahul&email=pawarrb@gmail.com&employerId=100"))
                .andExpect(status().isCreated())
                .andDo(print());

        mockMvc.perform(post("/employee?name=mayur&email=pawarrb@gmail.com&employerId=100"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void createEmployee_on_null_name_should_return_bad_request() throws Exception {
        mockMvc.perform(post("/employee?name=&email=pawarrb@gmail.com&employerId=100"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("/db/delete_employee_with_collaboration.sql")
    public void deleteEmployee_withCollaboration_shouldRemoveCollaboration() throws Exception {
        mockMvc.perform(delete("/employee/2"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
