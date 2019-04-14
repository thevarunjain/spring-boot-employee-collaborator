package edu.sjsu.cmpe275.service;

import edu.sjsu.cmpe275.domain.entity.Collaborator;
import edu.sjsu.cmpe275.domain.entity.Employee;
import edu.sjsu.cmpe275.domain.exception.EmployeeNotFoundException;
import edu.sjsu.cmpe275.domain.repository.CollaboratorRepository;
import edu.sjsu.cmpe275.domain.repository.EmployeeRepository;
import edu.sjsu.cmpe275.web.exception.ConstraintViolationException;
import edu.sjsu.cmpe275.web.mapper.CollaboratorMapper;
import edu.sjsu.cmpe275.web.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Objects;

@Component
public class CollaboratorService {

    private CollaboratorRepository collaboratorRepository;

    private CollaboratorMapper collaboratorMapper;

    private EmployeeService employeeService;


    @Autowired
    public CollaboratorService(final CollaboratorRepository collaboratorRepository,
                               final EmployeeService employeeService,
                               final CollaboratorMapper collaboratorMapper) {
        this.collaboratorRepository = collaboratorRepository;
        this.employeeService = employeeService;
        this.collaboratorMapper = collaboratorMapper;
    }


    @Transactional
    public void createCollaboration(long id1, long id2) {

//         TODO If either employee does not exist, return 404.
        final Employee e1 = employeeService.findEmployee(id1);
        final Employee e2 = employeeService.findEmployee(id2);


//         TODO If the two employees are already collaborators, do nothing, just return 200. Otherwise,
        boolean isPresent = false;
        List<Collaborator> listCollaborator = collaboratorRepository.findAll();
        for (Collaborator employee : listCollaborator) {
            if (employee.getId1().getId() == e1.getId() && employee.getId2().getId() == e2.getId()) {
                System.err.println("Coll exits");
                isPresent = true;
            }
        }

            if(!isPresent){
                collaboratorRepository.save(
                        collaboratorMapper.map(e1,e2)
                );
            }
    }


    @Transactional
    public void deleteCollaboration(final Long id1, final long id2) {

        // TODO  If either employee does not exist, return 404.
        final Employee e1 = employeeService.findEmployee(id1);
        final Employee e2 = employeeService.findEmployee(id2);


    // TODO Remove this collaboration relation. Return HTTP code 200 and a meaningful text message if all is successful.
        boolean isPresent = false;
        List<Collaborator> listCollaborator = collaboratorRepository.findAll();
        for (Collaborator employee : listCollaborator) {
            if (employee.getId1().getId() == e1.getId() && employee.getId2().getId() == e2.getId()) {
                System.err.println("DELETE : Collab");
                isPresent = true;
                collaboratorRepository.deleteById(employee.getId());
                break;
            }
            break;
        }

        // TODO If the two employees are not collaborators, return 404. Otherwise
        if(!isPresent){
            System.err.println("Is not present");
        }


}
}
