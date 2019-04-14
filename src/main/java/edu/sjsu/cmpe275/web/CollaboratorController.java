package edu.sjsu.cmpe275.web;


import edu.sjsu.cmpe275.domain.entity.Collaborator;
import edu.sjsu.cmpe275.domain.entity.Employee;
import edu.sjsu.cmpe275.service.CollaboratorService;
import edu.sjsu.cmpe275.web.exception.ConstraintViolationException;
import edu.sjsu.cmpe275.web.mapper.CollaboratorMapper;
import edu.sjsu.cmpe275.web.mapper.EmployeeMapper;
import edu.sjsu.cmpe275.web.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/collaborators")
public class CollaboratorController {

   private final CollaboratorService collaboratorService;
    private final CollaboratorMapper collaboratorMapper;


    @Autowired
    public CollaboratorController(
            CollaboratorService collaboratorService,
            CollaboratorMapper collaboratorMapper
    ) {
        this.collaboratorService = collaboratorService;
        this.collaboratorMapper = collaboratorMapper;
    }


    @PutMapping(value = "/{id1}/{id2}", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void createCollaboration(@PathVariable @NotNull Long id1,
                                               @PathVariable @NotNull Long id2)
            throws ConstraintViolationException {
        //storing the smaller one in first columns
                if(id1>id2){
                    long temp = id1;
                    id1 = id2;
                    id2 = temp;
                }
          collaboratorService.createCollaboration(
                  id1, id2
//                  collaboratorMapper.map(id1, id2)
            );
    }

    @DeleteMapping(value = "/{id1}/{id2}", produces = "application/json")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void deleteEmployee(@PathVariable long id1, @PathVariable long id2) {
        //storing the smaller one in first columns
        if(id1>id2){
            long temp = id1;
            id1 = id2;
            id2 = temp;
        }
        collaboratorService.deleteCollaboration(id1, id2);
    }

}
