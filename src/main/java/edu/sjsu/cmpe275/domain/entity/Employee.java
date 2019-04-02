package edu.sjsu.cmpe275.domain.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    private long id;
}

