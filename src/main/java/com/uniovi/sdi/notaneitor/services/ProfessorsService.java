package com.uniovi.sdi.notaneitor.services;

import com.uniovi.sdi.notaneitor.entities.Professor;
import com.uniovi.sdi.notaneitor.repository.ProfessorsRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessorsService {

    @Autowired
    private ProfessorsRepository professorsRepository;

    public List<Professor> getProfessors() {
        List<Professor> professors = new ArrayList<>();
        professorsRepository.findAll().forEach(professors::add);
        return professors;
    }

    public void addProfessor(Professor professor) {
        professorsRepository.save(professor);
    }

    public void removeProfessor(Long id) {
        professorsRepository.deleteById(id);
    }

    public void editProfessor(Professor professor) {
        Optional<Professor> op =professorsRepository.findById(professor.getId());
        if( op.isPresent() ) {
            // TODO
        }
    }

    public Professor detailsProfessor(Long id) {
        Optional<Professor> op =professorsRepository.findById(id);
        return op.orElse(null);
    }
}
