package com.uniovi.sdi.notaneitor.repository;

import com.uniovi.sdi.notaneitor.entities.Professor;
import org.springframework.data.repository.CrudRepository;

public interface ProfessorsRepository extends CrudRepository<Professor, Long> {

    Professor findByDni(String dni);
}
