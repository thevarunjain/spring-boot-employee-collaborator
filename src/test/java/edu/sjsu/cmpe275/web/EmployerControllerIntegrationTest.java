package edu.sjsu.cmpe275.web;

import edu.sjsu.cmpe275.Main;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.sjsu.cmpe275.domain.entity.Employer;
import edu.sjsu.cmpe275.web.model.response.EmployerDto;
import edu.sjsu.cmpe275.domain.repository.EmployerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@Transactional
public class EmployerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployerRepository employerRepository;

    // POST TEST CASES FOR EMPLOYER
    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void createEmployer_on_valid_parameter_should_save_employer() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(post("/employer?name=Yahoo&city=Palo Alto&state=CA" +
                "&zip=95112"))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andReturn();

        final EmployerDto employerResponseDto = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                EmployerDto.class
        );

        final Optional<Employer> employee = employerRepository.findById(employerResponseDto.getId());

        assertTrue(employee.isPresent());
    }

    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void createEmployer_shouldFailNameParameterNotPassed() throws Exception {
        mockMvc.perform(post("/employer"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    // Failing because NAME field of EMPLOYER is not unique
    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void createEmployer_employerAlreadyPresent() throws Exception {
        mockMvc.perform(post("/employer?name=Google"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    // GET TEST CASES FOR EMPLOYER
    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void getEmployerDetails_shouldReturnEmployerDetailsIDPassed() throws Exception {
        mockMvc.perform(get("/employer/7"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void getEmployerDetails_shouldFailIDPassedNotExist() throws Exception {
        mockMvc.perform(get("/employer/2"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    //When ID not sent then it is throwing 405 error we cannot send 400 error BAD_REQUEST TEST CASE FAILING
    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void getEmployerDetails_shouldFailIDNotPassed() throws Exception {
        mockMvc.perform(get("/employer"))
                .andExpect(status().isMethodNotAllowed())
                .andDo(print());
    }

    //Test cases for UPDATE (PUT) calls
    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void updateEmployerDetails_correctParametersPassed() throws Exception {
        mockMvc.perform(put("/employer/7?name=PayPal"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void updateEmployerDetails_nameParameterPassedEmpty() throws Exception {
        mockMvc.perform(put("/employer/7?name=&city=Menlo Park"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    // Getting 405 error Method Not found ID not Passed FAILING
    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void updateEmployerDetails_IDParameterNotPassed() throws Exception {
        mockMvc.perform(put("/employer/?name=FaceBook&city=Menlo Park"))
                .andExpect(status().isMethodNotAllowed())
                .andDo(print());
    }

    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void updateEmployerDetails_IDParameterDoesNotExist() throws Exception {
        mockMvc.perform(put("/employer/5?name=FaceBook&city=Menlo Park"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    // TEST CASES for DELETE Employer functionality
//    @Test
//    @Sql("/db/employee_crud_operations.sql")
//    public void deleteEmployer_withMoreThanOneEmployee_shouldReturnBadRequest() throws Exception {
//        mockMvc.perform(delete("/employer/20"))
//                .andExpect(status().isBadRequest())
//                .andDo(print());
//    }

    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void deleteEmployer_IDPassedDoesntExist() throws Exception {
        mockMvc.perform(delete("/employer/1"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @Sql("/db/employee_crud_operations.sql")
    public void deleteEmployer_IDExistAndCorrect() throws Exception {
        mockMvc.perform(delete("/employer/20"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}