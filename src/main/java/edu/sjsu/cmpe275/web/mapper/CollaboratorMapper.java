package edu.sjsu.cmpe275.web.mapper;

import edu.sjsu.cmpe275.domain.entity.Collaborator;
//import edu.sjsu.cmpe275.web.model.response.CreateCollaboratorResponseDto;
import edu.sjsu.cmpe275.domain.entity.Employee;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class CollaboratorMapper {
	public Collaborator map(final Employee id1, final Employee id2 ) {
        return Collaborator.builder()
                .id1(id1)
                .id2(id2)
                .build();
    }
}