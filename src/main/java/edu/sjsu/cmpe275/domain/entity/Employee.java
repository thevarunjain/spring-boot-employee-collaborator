package edu.sjsu.cmpe275.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "title")
    private String title;

    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name = "employer_id")
    private Employer employer;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Employee manager;

    @OneToMany(mappedBy = "manager")
    private List<Employee> reports;

    public void update(final Employee fromEmployee) {
        if (Objects.nonNull(fromEmployee.getName())) {
            this.setName(fromEmployee.getName());
        }
        if (Objects.nonNull(fromEmployee.getEmail())) {
            this.setEmail(fromEmployee.getEmail());
        }
        if (Objects.nonNull(fromEmployee.getTitle())) {
            this.setTitle(fromEmployee.getTitle());
        }
        if (Objects.nonNull(fromEmployee.getAddress())) {
            Address newAddress = Address.builder()
                    .street(fromEmployee.getAddress().getStreet())
                    .city(fromEmployee.getAddress().getCity())
                    .state(fromEmployee.getAddress().getState())
                    .zip(fromEmployee.getAddress().getZip())
                    .build();
            this.setAddress(newAddress);
        }
    }
}


