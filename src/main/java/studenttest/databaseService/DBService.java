package studenttest.databaseService;

import studenttest.datamodel.Atsakymas;
import studenttest.datamodel.Pasirinkimas;
import studenttest.datamodel.Sesija;
import studenttest.datamodel.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DBService {


    static public void inicializacija() {

        Long rez = getOneResult( "select count(vardas)as viso from users " );
        if (rez > 0) return;
        User user = new User( "admin", "admin", "admin" );
        SaveClasses.saveoneclass( user );
        Path kelias = Paths.get( "egzamData.txt" );
        SaveClasses.saveegzam( kelias );
        //   session.close();
    }

    static public <T> List<T> getvisifromclass(String fromwhere) {
        EntityManager em = Factory.getEntityManager();

        Session session = em.unwrap( Session.class );
        //     em.getTransaction().begin();
        String prep = fromwhere;
        Query q = session.createQuery( prep );

        List<T> visi = new ArrayList<>();
        visi = q.list();
        return visi;

    }

    static public long getOneResult(String isKur) {
        EntityManager em = Factory.getEntityManager();

        Session session = em.unwrap( Session.class );
        if (!em.getTransaction().isActive()) em.getTransaction().begin();

        Query q1 = session.createQuery( isKur );
        Long rez = (Long) q1.getSingleResult();
        return rez;
    }

    static public long getTrueAnswersEgzm(Sesija sesija) {
        EntityManager em = Factory.getEntityManager();

        Session session = em.unwrap( Session.class );
        if (em.getTransaction().isActive()) em.getTransaction().rollback();
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = cb.createQuery( Long.class );
        Root<Atsakymas> atsakymasRoot = criteriaQuery.from( Atsakymas.class );
        Root<Pasirinkimas> pasirinkimasRoot = criteriaQuery.from( Pasirinkimas.class );

        criteriaQuery.select( cb.count( atsakymasRoot.get( "id" ) ) );
        criteriaQuery.where( cb.equal( pasirinkimasRoot.get( "atsakymas" ), atsakymasRoot.get( "id" ) ), cb.isTrue( atsakymasRoot.get( "arTeisingas" ) ), cb.equal( pasirinkimasRoot.get( "sesija" ), sesija ) );

        Query<Long> query = session.createQuery( criteriaQuery );


        Long rez = (Long) query.getSingleResult();

        return rez;
    }

    static public void naujasEgzaminas() {
        System.out.println( "is kokio failo nori ikelti egzamino klausimus (full path)" );
        Scanner sc = new Scanner( System.in );
        Path kelias = Paths.get( sc.next() );
        if (Files.notExists( kelias )) {
            System.out.println( "neteisingas failo adresas" );
            return;
        }

        SaveClasses.saveegzam(kelias );
    }

  static public List<String > getNames (String query){
      EntityManager em = Factory.getEntityManager();

      Session session = em.unwrap( Session.class );
      //     em.getTransaction().begin();
      String prep = query;
      Query q = session.createQuery( prep );

      List<String> visi = new ArrayList<>();
      visi = q.list();
      return visi;
  }
}
