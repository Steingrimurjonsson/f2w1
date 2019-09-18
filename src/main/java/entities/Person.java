package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name = "Person.deleteAllRows", query = "DELETE from Person"),
    @NamedQuery(name = "Person.getAll", query = "SELECT m FROM Person m"),
    @NamedQuery(name = "Person.getByName", query = "SELECT m FROM Person m WHERE m.firstName LIKE :firstName")
})
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;

    private String[] hobbies;

    public Person() {
    }

    public Person(String lastName, String firstName, String[] hobbies) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.hobbies = hobbies;
    }

    @Override
    public String toString() {
        return "Person{" + "id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", hobbies=" + hobbies + '}';
    }

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
