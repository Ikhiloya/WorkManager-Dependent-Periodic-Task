package com.ikhiloyaimokhai.companydemo.controller;

import com.ikhiloyaimokhai.companydemo.entity.Person;
import com.ikhiloyaimokhai.companydemo.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/community")
public class PersonController {

    private final Logger log = LoggerFactory.getLogger(PersonController.class);
    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/person")
    public ResponseEntity<Person> createBeneficiary(@RequestBody Person person) {
        log.debug("REST request to save Staff : {}", person);
        Person result = personService.save(person);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/person")
    public List<Person> getAllBeneficiaries() {
        log.debug("REST request to get all Staff");
        return personService.findAll();
    }
}
