package studenttest.databaseService;

import studenttest.datamodel.Sesija;
import studenttest.datamodel.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class GetStatistic {
    static public List<Object[]>  getInfo(String sqlString){
        EntityManager em =Factory.getEntityManager();
        Session session = em.unwrap( Session.class );

       if (!em.getTransaction().isActive())em.getTransaction().begin();
        Query query = session.createNativeQuery(  sqlString );
        List<Object[]> listResult1 = query.list();

       return listResult1;
    }
    static public List<Object[]>  userInfo2(){
        EntityManager em =Factory.getEntityManager();
        Session session = em.unwrap( Session.class );
        if (!em.getTransaction().isActive())em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = cb.createQuery( Object[].class);
        Root<User> userRoot = criteriaQuery.from( User.class );
        Root<Sesija> sesijaRoot = criteriaQuery.from( Sesija.class );
        criteriaQuery.multiselect( userRoot.get( "vardas" ) , cb.count( sesijaRoot ) );
        criteriaQuery.where(cb.equal(  userRoot.get( "id" ),sesijaRoot.get( "useris" ) ), cb.equal( userRoot.get( "role" ),"user" ),cb.isTrue( sesijaRoot.get( "arBaigtas" ) ) );
        criteriaQuery.groupBy( userRoot.get( "vardas" ));

        Query<Object[]> query = session.createQuery(  criteriaQuery );
        List<Object[]> list = query.getResultList();
        return list;
    }

    public static List<Object[]> sesijaTime() {
        EntityManager em =Factory.getEntityManager();
        Session session = em.unwrap( Session.class );
        if (!em.getTransaction().isActive())em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = cb.createQuery( Object[].class);
        Root<Sesija> sesijaRoot =criteriaQuery.from( Sesija.class );
        Root<User> userRoot = criteriaQuery.from( User.class );
        criteriaQuery.multiselect( userRoot.get( "vardas" ),sesijaRoot.get( "pradzia" ),sesijaRoot.get( "pabaiga" ) );
        criteriaQuery.where( cb.isTrue( userRoot.get( "arBaigtas" ) ),
                cb.equal( userRoot.get( "role" ) ,"user")) ;
        Query<Object[]> query = session.createQuery(  criteriaQuery );
        List<Object[]> list = query.getResultList();
        return list;
    }
}

