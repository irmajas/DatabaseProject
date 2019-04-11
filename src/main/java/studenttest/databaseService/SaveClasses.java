package studenttest.databaseService;

import studenttest.datamodel.Atsakymas;
import studenttest.datamodel.Egzaminas;
import studenttest.datamodel.Klausimas;
import studenttest.fileService.ReadData;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveClasses {

    public static void saveegzam ( Path kelias){
        EntityManager em = Factory.getEntityManager();

        if (!em.getTransaction().isActive()) em.getTransaction().begin();
        Session session = em.unwrap( Session.class );
      if (!em.getTransaction().isActive()) em.getTransaction().begin();
        HashMap<Egzaminas, List<Klausimas>> egz = ReadData.readfromFile(kelias);
        if (egz.isEmpty()) {
            System.out.println("nepavyko ikelti is failo klausimu");
            return;
        }

        Map.Entry<Egzaminas,List<Klausimas>> entry = egz.entrySet().iterator().next();
        Egzaminas egzaminas = entry.getKey();
        List<Klausimas> klausimasList= entry.getValue();

        session.save( egzaminas );

        for (Klausimas kl:klausimasList
        ) {

            kl.setEgzaminas( egzaminas );

            session.save( kl );
            for (Atsakymas at:kl.getAtsakymas()
            ) {
                at.setKlausimas( kl );
                session.save( at );
            }
        }


        em.getTransaction().commit();


    }

    static public <T> void saveoneclass(T ob) {
           EntityManager em= Factory.getEntityManager();

           Session session = em.unwrap( Session.class );//
           if (!em.getTransaction().isActive()) {
               em.getTransaction().begin();
           }
           session.save( ob );

           em.getTransaction().commit();

       }
       static public void saveKlausimas (Klausimas klausimas){
           EntityManager em= Factory.getEntityManager();
           Session session = em.unwrap( Session.class );
          if (!em.getTransaction().isActive()) em.getTransaction().begin();

          session.save( klausimas );
           for (Atsakymas at : klausimas.getAtsakymas()
           ) {
               at.setKlausimas( klausimas );

              session.save( at );
           }
           em.getTransaction().commit();
       }
}
