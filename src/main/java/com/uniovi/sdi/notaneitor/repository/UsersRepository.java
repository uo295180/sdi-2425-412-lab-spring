package com.uniovi.sdi.notaneitor.repository;

import com.uniovi.sdi.notaneitor.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {
    User findByDni(String dni);
}
