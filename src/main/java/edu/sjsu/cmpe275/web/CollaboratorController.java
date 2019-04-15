package edu.sjsu.cmpe275.web;


import edu.sjsu.cmpe275.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/collaborators")
public class CollaboratorController {

    private final EmployeeService employeeService;


    @Autowired
    public CollaboratorController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @PutMapping(value = "/{id1}/{id2}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void createCollaboration(@PathVariable @NotNull Long id1,
                                    @PathVariable @NotNull Long id2
    ) {
        employeeService.createCollaboration(
                id1, id2
        );
    }

    @DeleteMapping(value = "/{id1}/{id2}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void deleteEmployee(@PathVariable @NotNull long id1,
                               @PathVariable @NotNull long id2
    ) {
        employeeService.deleteCollaboration(
                id1, id2
        );
    }

}
