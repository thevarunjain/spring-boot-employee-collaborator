package edu.sjsu.cmpe275.domain.repository;

import edu.sjsu.cmpe275.domain.entity.Collaborator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollaboratorRepository extends JpaRepository<Collaborator, Long> {
}
