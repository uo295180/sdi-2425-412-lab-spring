package com.uniovi.sdi.notaneitor.services;

import com.uniovi.sdi.notaneitor.entities.Professor;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProfessorsService {

    private final List<Professor> professors = new LinkedList<>();

    @PostConstruct
    public void init() {
        professors.add(new Professor(1L, "Nombre1", "Apellido1", "1","Category1"));
        professors.add(new Professor(2L, "Nombre2", "Apellido2", "2","Category2"));
        professors.add(new Professor(3L, "Nombre3", "Apellido3", "3","Category3"));
    }

    public List<Professor> getProfessors() {
        return new LinkedList<>(professors);
    }

    public void addProfessor(Professor professor) {
        professors.add(professor);
    }

    public void removeProfessor(Long id) {
        Professor p = professors.stream().filter(professor -> professor.getId() == id).findFirst().orElse(null);
        if (p != null) {
            professors.remove(p);
        }
    }

    public void editProfessor(Professor professor) {
        int index = professors.indexOf(professor);
        professors.set(index, professor);
    }

    public Professor detailsProfessor(Long id) {
        for (Professor professor : professors) {
            if (professor.getId().equals(id)) {
                return professor;
            }
        }
        return null;
    }
}
