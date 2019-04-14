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
    public void createCollaboration(long id1, long id2) {

        final Employee e1 = employeeService.findEmployee(id1);
        final Employee e2 = employeeService.findEmployee(id2);

        e1.getCollaborators().add(e2);
        e2.getCollaborators().add(e1);

/*//         TODO If the two employees are already collaborators, do nothing, just return 200. Otherwise,
        boolean isPresent = false;
        List<Collaborator> listCollaborator = collaboratorRepository.findAll();
        for (Collaborator employee : listCollaborator) {
            if (employee.getId1().getId() == e1.getId() && employee.getId2().getId() == e2.getId()) {
                System.err.println("Coll exits");
                isPresent = true;
            }
        }

        if (!isPresent) {
            collaboratorRepository.save(
                    collaboratorMapper.map(e1, e2)
            );
        }*/
    }


    @Transactional
    public void deleteCollaboration(final Long id1, final long id2) {

        // TODO  If either employee does not exist, return 404.
        final Employee e1 = employeeService.findEmployee(id1);
        final Employee e2 = employeeService.findEmployee(id2);

/*    // TODO Remove this collaboration relation. Return HTTP code 200 and a meaningful text message if all is successful.
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
            System.err.println("Is not present");*/
    }
}
