package com.ikhiloyaimokhai.companydemo.repository;

import com.ikhiloyaimokhai.companydemo.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PersonRepository extends JpaRepository<Person, Long> {
}
