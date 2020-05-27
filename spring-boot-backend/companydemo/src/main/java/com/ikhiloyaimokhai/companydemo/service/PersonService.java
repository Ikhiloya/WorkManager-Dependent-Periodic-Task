package com.ikhiloyaimokhai.companydemo.service;

import com.ikhiloyaimokhai.companydemo.entity.Person;
import com.ikhiloyaimokhai.companydemo.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PersonService {
    private final Logger log = LoggerFactory.getLogger(PersonService.class);

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    /**
     * Save a staff.
     *
     * @param person the entity to save
     * @return the persisted entity
     */
    public Person save(Person person) {
        log.info("Request to save Staff : {}", person);
        return personRepository.save(person);
    }


    /**
     * Get all the staff.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Person> findAll() {
        log.debug("Request to get all staff");
        return personRepository.findAll();
    }

}
