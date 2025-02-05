package com.uniovi.sdi.notaneitor.entities;

public class Professor {

    private Long id;
    private String name;
    private String surname;
    private String dni;
    private String category;

    public Professor() {}
    public Professor(Long id, String name, String surname, String dni, String category) {
        setId(id);
        setName(name);
        setSurname(surname);
        setDni(dni);
        setCategory(category);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dni='" + dni + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
