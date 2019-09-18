package facades;

import entities.F2w1;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class F2w1Facade {

    private static F2w1Facade instance;
    private static EntityManagerFactory emf;
    
    private F2w1Facade() {}
    
    
 
    public static F2w1Facade getF2w1Facade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new F2w1Facade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
 
    public long getF2w1Count(){
        EntityManager em = emf.createEntityManager();
        try{
            long F2w1Count = (long)em.createQuery("SELECT COUNT(m) FROM F2w1 m").getSingleResult();
            return F2w1Count;
        }finally{  
            em.close();
        }
        
    }
    
    public F2w1 getF2w1ByID(int id) {
        EntityManager em = emf.createEntityManager();
        try{
            F2w1 F2w1 = em.find(F2w1.class, id);
            return F2w1;
        }finally{
            em.close();
        }
    }

    public List<F2w1> getF2w1ByName(String name) {
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery <F2w1> query =
                    em.createQuery("Select m from F2w1 m where m.name =:name", F2w1.class);
            return query.setParameter("name", name).getResultList();
        } finally{
            em.close();
        }
    }

    public F2w1 addF2w1(int year, String name, String[] actors) {
        F2w1 F2w1 = new F2w1();
        F2w1 = new F2w1(year, name, actors);
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(F2w1);
            em.getTransaction().commit();
            return F2w1;
        } finally {
            em.close();
        }
    }

    public List<F2w1> getAllF2w1s() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery query
                    = em.createQuery("Select m from F2w1 m", F2w1.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<F2w1> getAcotorsByF2w1Name(String name) {
        EntityManager em = emf.createEntityManager();
        try{
            TypedQuery <F2w1> query =
                    em.createQuery("Select actors from F2w1 m where m.name =:name", F2w1.class);
            return query.setParameter("name", name).getResultList();
        } finally{
            em.close();
        }
    }
    public void populateF2w1s() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("F2w1.deleteAllRows").executeUpdate();
            em.persist(new F2w1(1932, "Nøddebo præstekjole", new String[]{"Jepser Nielsen", "Henrik Poulsen", "Freddy Fræk"}));
            em.persist(new F2w1(1933, "De døde heste", new String[]{"Ulla Tørnæse", "Pia Køl", "Freddy Fræk"}));
            em.persist(new F2w1(1933, "De bløde heste", new String[]{"Ulla Tørnæse", "Pia Køl", "Freddy Fræk"}));
            em.persist(new F2w1(1934, "De søde heste", new String[]{"Ulla Tørnæse", "Pia Køl", "Freddy Fræk"}));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}