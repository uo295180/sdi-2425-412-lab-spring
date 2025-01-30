package com.uniovi.sdi.notaneitor.services;

import com.uniovi.sdi.notaneitor.entities.Mark;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class MarksService {
    private List<Mark>  marksList = new LinkedList<>();

    @PostConstruct
    public void init() {
        marksList.add(new Mark(1L, "Ejercicio 1", 10.0));
        marksList.add(new Mark(2L, "Ejercicio 2", 9.0));
    }

    public List<Mark> getMarks(){
        return marksList;
    }

    public Mark getMark(Long id){
        return marksList.stream()
                .filter(m -> m.getId().equals(id)).findFirst().get();
    }

    public void addMark(Mark mark){
        if(mark.getId() == null){
            mark.setId(marksList.getLast().getId() + 1);
        }

        marksList.add(mark);
    }

    public void deleteMark(Long id){
        marksList.removeIf(mark -> mark.getId().equals(id));
    }
}
