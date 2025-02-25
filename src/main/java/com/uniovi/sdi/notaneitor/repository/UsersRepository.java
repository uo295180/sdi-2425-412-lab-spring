package com.uniovi.sdi.notaneitor.repository;

import com.uniovi.sdi.notaneitor.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {
    User findByDni(String dni);

    @Query("SELECT u from User u WHERE (LOWER(u.name) LIKE LOWER(?1) OR LOWER(u.lastName) LIKE LOWER(?1) )")
    Page<User> searchByNameAndSurname(Pageable pageable, String searchText);
}
