package studenttest.databaseService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public  class Factory {
    static  private EntityManagerFactory emf ;
    static  private EntityManager em ;
   static EntityManager getEntityManager () {
       if (emf == null) {
           emf = Persistence.createEntityManagerFactory( "my-persistence-unit" );
           em = emf.createEntityManager();
       }
       return em;
   }

    public static EntityManagerFactory getEmf() {
        return emf;
    }

    public static EntityManager getEm() {
        return em;
    }
}
