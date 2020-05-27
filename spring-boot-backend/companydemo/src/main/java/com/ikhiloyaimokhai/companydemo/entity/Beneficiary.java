package com.ikhiloyaimokhai.companydemo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Beneficiary {
    @Id
    @GeneratedValue(generator = "beneficiary_generator")
    @SequenceGenerator(
            name = "beneficiary_generator",
            sequenceName = "beneficiary_sequence",
            initialValue = 1
    )
    private Long id;
    private Long personId;
    private String firstName;
    private String lastName;
    private String relationship;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }


    @Override
    public String toString() {
        return "Beneficiary{" +
                "id=" + id +
                ", personId=" + personId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", relationship='" + relationship + '\'' +
                '}';
    }
}
