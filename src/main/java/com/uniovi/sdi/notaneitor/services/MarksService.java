package com.uniovi.sdi.notaneitor.services;

import com.uniovi.sdi.notaneitor.entities.Mark;
import com.uniovi.sdi.notaneitor.repository.MarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

@Service
public class MarksService {
    @Autowired
    private MarksRepository marksRepository;

    private final HttpSession httpSession;

    @Autowired
    public MarksService(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public List<Mark> getMarks(){
        List<Mark> marks = new ArrayList<Mark>();
        marksRepository.findAll().forEach(marks::add);
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
}
