/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Person;

/**
 *
 * @author stein
 */
public class PersonDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String[] hobbies;
    
    public PersonDTO(Person p) {
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.hobbies = p.getHobbies();
        this.id = p.getId();
    }
    public PersonDTO(String fn,String ln, String[] hobbies) {
        this.firstName = fn;
        this.lastName = ln;
        this.hobbies = hobbies;        
    }
    public PersonDTO() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String[] getHobbies() {
        return hobbies;
    }

    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }
       
}