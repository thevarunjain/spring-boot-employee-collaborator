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

import javax.transaction.Transactional;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@Transactional
public class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void createEmployee_on_valid_parameter_should_save_employee() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(post("/employee?name=sahil&email=sahil@sjsu.edu&employerId=20"))
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
    @Sql("/db/employee_crud_operations.sql")
    public void createEmployee_on_invalid_email_should_return_bad_request() throws Exception {
        mockMvc.perform(post("/employee?name=saurabh&email=saurabh&employerId=7"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    // Same Email ID should not create new Employee for same employerID
    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void createEmployee_with_existing_email_should_return_bad_request() throws Exception {
        mockMvc.perform(post("/employee?name=apurav&email=apurav@sjsu.edu&employerId=7"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    // Same Email ID should not create new Employee for different employerID
    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void createEmployee_with_existing_email_but_for_different_employerID_should_return_bad_request() throws Exception {
        mockMvc.perform(post("/employee?name=apurav&email=apurav@sjsu.edu&employerId=10"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    //Name not present in parameter
    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void createEmployee_on_null_name_should_return_bad_request() throws Exception {
        mockMvc.perform(post("/employee?name=&email=saurabh@sjsu.edu&employerId=1"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    //Email Id not present in parameter
    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void createEmployee_on_null_emailId_should_return_bad_request() throws Exception {
        mockMvc.perform(post("/employee?name=saurabh&email=&employerId=3"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    //EmployerID not present in parameter
    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void createEmployee_on_null_employerID_should_return_bad_request() throws Exception {
        mockMvc.perform(post("/employee?name=saurabh&email=saurabh@sjsu.edu&employerId="))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    //Create Employee with ManagerId that doesn't exist for that employer and is from another employer
    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void createEmployee_on_managerId_passed_Doesnt_Exist_Different_Employer_parameter_should_return_bad_request() throws Exception {
        mockMvc.perform(post("/employee?name=varun&email=varun.jindal@sjsu.edu&employerId=7" +
                "&managerId=110"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    //Create Employee with ManagerId that doesn't exist for that employer
    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void createEmployee_on_managerId_passed_Doesnt_Exist_Same_Employer_parameter_should_return_bad_request() throws Exception {
        mockMvc.perform(post("/employee?name=raghav&email=raghav@sjsu.edu&employerId=7" +
                "&managerId=120"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    @Test
    @Sql("/db/delete_employee_with_collaboration.sql")
    public void deleteEmployee_withCollaboration_shouldRemoveCollaboration() throws Exception {
        mockMvc.perform(delete("/employee/2"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //Create Employee for ManagerId exist for that employer
    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void createEmployee_on_managerId_passed_Exist_parameter_should_return_created() throws Exception {
        mockMvc.perform(post("/employee?name=raghav&email=raghav@sjsu.edu&employerId=7" +
                "&managerId=101"))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    //Get Employee Details
    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void getEmployeeDetails_correctParametersPassed() throws Exception {
        mockMvc.perform(get("/employee/101"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //Get Employee Details if Employee Id doesn't exist
    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void getEmployeeDetails_incorrectIDParametersPassed() throws Exception {
        mockMvc.perform(get("/employee/301"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    //Get Employee Details when the manager shifts to another employer
    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void getEmployeeDetails_managerChangedEmployer() throws Exception {
        mockMvc.perform(get("/employee/110"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/employee/111"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(put("/employee/110?employerId=20&email=rajat@amazon.com"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/employee/111"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/employee/110"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //Update Employee Details when the manager shifts to another employer
    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void updateEmployeeDetails_employerIDChanged() throws Exception {
        mockMvc.perform(put("/employee/110?employerId=20&email=rajat@sjsu.edu"))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get("/employee/110"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
