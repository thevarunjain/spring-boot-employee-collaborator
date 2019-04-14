package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class CollaboratorService {

    private EmployeeService employeeService;


    @Autowired
    public CollaboratorService(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @Transactional
    public void createCollaboration(final long id1, final long id2) {

        final Employee e1 = employeeService.findEmployee(id1);
        final Employee e2 = employeeService.findEmployee(id2);

        // TODO Check if there is already a collaboration
        if (id1 < id2)
            e1.getCollaborators().add(e2);
        else
            e2.getCollaborators().add(e1);
    }


    @Transactional
    public void deleteCollaboration(final long id1, final long id2) {

        final Employee e1 = employeeService.findEmployee(id1);
        final Employee e2 = employeeService.findEmployee(id2);
        // TODO What if collaboration doesn't exist
        if (id1 < id2)
            e1.getCollaborators().remove(e2);
        else
            e2.getCollaborators().remove(e1);
    }
}
