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
@NamedQuery(name = "F2w1.deleteAllRows", query = "DELETE from F2w1"),
@NamedQuery(name = "F2w1.getAll", query = "SELECT m FROM F2w1 m"),
@NamedQuery(name = "F2w1.getByName", query = "SELECT m FROM F2w1 m WHERE m.name LIKE :name")
})
public class F2w1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int year;
    private String name;
    private String[] actors;
    
       public F2w1() {
    }

    public F2w1(int year, String name, String[] actors) {
        this.year = year;
        this.name = name;
        this.actors = actors;
    }
     @Override
    public String toString() {
        return "F2w1{" + "id=" + id + ", year=" + year + ", name=" + name + ", actors=" + actors + '}';
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getActors() {
        return actors;
    }

    public void setActors(String[] actors) {
        this.actors = actors;
    }
  
}
