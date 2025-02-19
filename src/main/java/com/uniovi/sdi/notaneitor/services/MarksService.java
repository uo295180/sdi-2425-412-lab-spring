package com.uniovi.sdi.notaneitor.services;

import com.uniovi.sdi.notaneitor.entities.Mark;
import com.uniovi.sdi.notaneitor.entities.User;
import com.uniovi.sdi.notaneitor.repository.MarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class MarksService {
    @Autowired
    private MarksRepository marksRepository;

    @Autowired
    private final HttpSession httpSession;

    @Autowired
    public MarksService(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public Page<Mark> getMarks(Pageable pageable){
        Page<Mark> marks = marksRepository.findAll(pageable);
        return marks;
    }

    public Mark getMark(Long id){
        Mark mark = marksRepository.findById(id).isPresent() ? marksRepository.findById(id).get() : new Mark();
        return mark;
    }

    public void addMark(Mark mark){
        marksRepository.save(mark);
    }

    public void deleteMark(Long id){
        marksRepository.deleteById(id);
    }

    public void setMarkResend(boolean revised, Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dni = auth.getName();

        Mark mark = marksRepository.findById(id).get();

        if(mark.getUser().getDni().equals(dni)){
            marksRepository.updateResend(revised, id);
        }
    }

    public Page<Mark> getMarksForUser(Pageable pageable, User user){
        Page<Mark> marks = new PageImpl<Mark>(new LinkedList<Mark>());
        if(user.getRole().equals("ROLE_STUDENT")) {
            marks = marksRepository.findAllByUser(pageable, user);
        }
        if(user.getRole().equals("ROLE_PROFESSOR") || user.getRole().equals("ROLE_ADMIN")) {
            marks = getMarks(pageable);
        }
        return marks;
    }

    public Page<Mark> searchMarksByDescriptionAndNameForUser(Pageable pageable, String searchText, User user){
        Page<Mark> marks = new PageImpl<Mark>(new LinkedList<Mark>());
        searchText= '%'+searchText+'%';
        if(user.getRole().equals("ROLE_STUDENT")) {
            marks = marksRepository.searchByDescriptionNameAndUser(pageable, searchText, user);
        }
        if(user.getRole().equals("ROLE_PROFESSOR")) {
            marks = marksRepository.searchByDescriptionAndName(pageable, searchText);
        }
        return marks;
    }
}
