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
    private String fName;
    private String lName;
    private String phone;
   // private String address;

    public PersonDTO(Person p) {
        this.fName = p.getFirstName();
        this.lName = p.getLastName();
        this.phone = p.getPhone();
        this.id = p.getId();
    }

    public PersonDTO(String fn, String ln, String phone) {
        this.fName = fn;
        this.lName = ln;
        this.phone = phone;
        //this.address = address;
    }

    public PersonDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
  //  public String getAddress() {
   //     return address;
   // }

  //  public void setAddress(String address) {
   //     this.address = phone;
  //  }
}
