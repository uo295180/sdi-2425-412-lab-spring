package com.uniovi.sdi.notaneitor.services;

import com.uniovi.sdi.notaneitor.entities.User;
import com.uniovi.sdi.notaneitor.repository.UsersRepository;
import javax.annotation.PostConstruct;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public UsersService(UsersRepository usersRepository, BCryptPasswordEncoder
            bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @PostConstruct
    public void init() {
    }
    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }
    public User getUser(Long id) {
        return usersRepository.findById(id).get();
    }
    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }
    public User getUserByDni(String dni) {
        return usersRepository.findByDni(dni);
    }
    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }
    public void updateUser(User user) {
        User oldUser = usersRepository.findById(user.getId()).get();
        oldUser.setDni(user.getDni());
        oldUser.setName(user.getName());
        oldUser.setLastName(user.getLastName());
        usersRepository.save(oldUser);
    }

    public Page<User> searchUserByNameAndSurname(Pageable pageable, String searchText) {
        Page<User> users = new PageImpl<User>(new LinkedList<User>());
        searchText='%'+searchText+'%';
        users = usersRepository.searchByNameAndSurname(pageable, searchText);
        return users;
    }
}