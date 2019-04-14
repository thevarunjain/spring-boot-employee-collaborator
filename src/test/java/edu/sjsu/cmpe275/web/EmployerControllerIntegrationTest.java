package edu.sjsu.cmpe275.web;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;


import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sjsu.cmpe275.Main;
import edu.sjsu.cmpe275.domain.entity.Employer;
import edu.sjsu.cmpe275.domain.repository.EmployerRepository;
import edu.sjsu.cmpe275.web.model.response.EmployerDto;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private EmployerRepository employerRepository;
    
    @Test
    @Sql("/db/employer_with_existing_employee.sql")
    public void a_deleteEmployer_withMoreThanOneEmployee_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(delete("/employer/1"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
    
    @Test
    public void b_createEmployer_on_valid_parameter_should_save_employer() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(post("/employer?name=google&description=TechCompany&city=SanJose&state=CA&street=7thStreet&zip=95664"))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andReturn();

        final EmployerDto employerResponseDto = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                EmployerDto.class
        );

        final Optional<Employer> employer = employerRepository.findById(employerResponseDto.getId());

        assertTrue(employer.isPresent());
    }
    
    @Test
    public void c_createEmployer_on_missing_name_should_return_bad_request() throws Exception {
        mockMvc.perform(post("/employer?description=TechCompany&city=SanJose&state=CA&street=7thStreet&zip=95664"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
    
    @Test
    public void d_updateEmployer_on_valid_request_should_save_employer() throws Exception {
    	mockMvc.perform(put("/employer/1?name=Google_updated"))
    			.andExpect(status().isOk())
    			.andDo(print())
    			.andExpect(jsonPath("$.name", is("Google_updated")));
    }
    
    @Test
    public void e_updateEmployer_on_invalid_request_should_save_employer() throws Exception {
    	mockMvc.perform(put("/employer/1?street=Google_updated"))
    			.andExpect(status().isBadRequest())
    			.andDo(print());
    }
}