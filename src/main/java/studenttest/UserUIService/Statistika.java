package studenttest.UserUIService;

import studenttest.databaseService.GetStatistic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Statistika {

    public static void usersStatistic() {
        String query1 = "SELECT users.vardas,COUNT(sesija.arBaigtas) FROM  users left join sesija ON (users.id=sesija.user_id AND sesija.arBaigtas=1)\n" +
                " WHERE users.role='user'\n" +
                " GROUP BY users.vardas";
        String query2 = "SELECT users.vardas, AVG(TIMESTAMPDIFF(SECOND, pradzia, pabaiga)), \n" +
                "MIN(TIMESTAMPDIFF(SECOND, pradzia, pabaiga)), MAX(TIMESTAMPDIFF(SECOND, pradzia, pabaiga))\n" +
                " FROM  users left join sesija ON (users.id=sesija.user_id AND sesija.arBaigtas=1)\n" +
                " WHERE users.role='user'\n" +
                " GROUP BY users.vardas";
        String query3 = " SELECT users.vardas,COUNT(sesija.arBaigtas) FROM  users left join sesija ON (users.id=sesija.user_id)\n" +
                " WHERE users.role='user'\n" +
                " GROUP BY users.vardas";
        String query4 = "  SELECT users.vardas, AVG( kiekteis.kiekteisingu) AS vidteis,\n" +
                "  MIN(kiekteis.kiekteisingu) AS maz, MAX(kiekteis.kiekteisingu) AS did\n" +
                "  FROM  users LEFT  JOIN sesija  ON users.id=sesija.user_id\n" +
                "  LEFT JOIN \n" +
                "(SELECT s.id AS katra, COUNT(a.id) AS kiekteisingu\n" +
                "        FROM sesija AS s\n" +
                "        JOIN pasirinkimai  as p ON s.id=p.sesijos_id\n" +
                "        JOIN atsakymai AS a ON (p.atsakymo_id=a.id AND a.arTeisingas=1)\n" +
                "       GROUP BY s.id) \n" +
                "         AS kiekteis\n" +
                "ON sesija.id=kiekteis.katra\n" +
                " WHERE users.role='user'\n" +
                " GROUP BY users.vardas";
        List<Object[]> kiekTestuBaige = GetStatistic.getInfo( query1 );
        List<Object[]> laikymolaikas = GetStatistic.getInfo( query2 );
        List<Object[]> listKiekKartuLaike = GetStatistic.getInfo( query3 );
        List<Object[]> listTestoVidurkis = GetStatistic.getInfo( query4 );
        int kiek = listKiekKartuLaike.size();
        HashMap<String,List<String>> useStatistika = new HashMap<>(  );
        for (Object[] ob:listKiekKartuLaike
        ) {
            String  name= (String) ob[0];
            String rez;
            if (ob[1]==null)rez="";
            else rez =ob[1].toString() ;
            List<String>listStat= new ArrayList<>(  );
            listStat.add( rez );
            useStatistika.put( name, listStat );
        }
        for (Object[] ob:kiekTestuBaige
        ) {
            String  name= (String) ob[0];
            String rez;
            if (ob[1]==null)rez="";
            else rez =ob[1].toString() ;
            List<String> stat=useStatistika.get( name );
            stat.add( rez );
            useStatistika.put( name,stat);
        }

        for (Object[] ob:listTestoVidurkis
        ) {
            String  name= (String) ob[0];
            String rez1, rez2, rez3;
            if (ob[3]==null) rez1="";
            else rez1 =ob[3].toString() ;
            if (ob[2]==null) rez2="";
            else  rez2 = ob[2].toString() ;
            if (ob[2]==null) rez3="";
            else  rez3 = ob[1].toString() ;
            List<String> stat=useStatistika.get( name );
            stat.add( rez1 );
            stat.add( rez2 );
            stat.add( rez3 );
            useStatistika.put( name,stat);
        }
        for (Object[] ob:laikymolaikas
        ) {
            String  name= (String) ob[0];
            String rez1, rez2, rez3;
            if (ob[3]==null) rez1="";
            else rez1 =ob[3].toString() ;
            if (ob[2]==null) rez2="";
            else  rez2 = ob[2].toString() ;
            if (ob[2]==null) rez3="";
            else  rez3 = ob[1].toString() ;
            List<String> stat=useStatistika.get( name );
            stat.add( rez1 );
            stat.add( rez2 );
            stat.add( rez3 );
            useStatistika.put( name,stat);
        }
        System.out.println( "viso studentu yra: " + kiek );
        System.out.println("Vardas\t Laike\tBaigė  \tMax_teisingu\tMin_teisingu\tVid_teisingu\tMax_laikas\tMin_laikas\tVid_laikas");
        for (Map.Entry<String,List<String>> v: useStatistika.entrySet()
             ) {
            System.out.print(v.getKey()+"\t  ");
                   List<String> sk= v.getValue();
            for (String skai:sk
                 ) {
                System.out.print(skai+"\t\t\t");
            }
            System.out.println();
        }

        }
    public static void examsStatistic() {
        String query1 = "SELECT egzaminai.pavadinimas,COUNT(sesija.arBaigtas) FROM  egzaminai left join sesija ON (egzaminai.id=sesija.egzamino_id AND sesija.arBaigtas=1)\n" +
                "left JOIN USERs  ON (sesija.user_id=users.id AND users.role='user')\n" +
                "GROUP BY egzaminai.pavadinimas";
        String query2 = "SELECT egzaminai.pavadinimas, AVG(TIMESTAMPDIFF(SECOND, pradzia, pabaiga)), \n" +
                "MIN(TIMESTAMPDIFF(SECOND, pradzia, pabaiga)), MAX(TIMESTAMPDIFF(SECOND, pradzia, pabaiga))\n" +
                "FROM  egzaminai left join sesija ON (egzaminai.id=sesija.egzamino_id AND sesija.arBaigtas=1)\n" +
                "left     JOIN USERs  ON (sesija.user_id=users.id AND users.role='user')\n" +
                "GROUP BY egzaminai.pavadinimas";
        String query3 = " SELECT egzaminai.pavadinimas,COUNT(sesija.arBaigtas) FROM  egzaminai left join sesija ON (egzaminai.id=sesija.egzamino_id)\n" +
                "left JOIN USERs  ON (sesija.user_id=users.id AND users.role='user')\n" +
                "GROUP BY egzaminai.pavadinimas";
        String query4 = " SELECT egzaminai.pavadinimas, AVG( kiekteis.kiekteisingu) AS vidteis,\n" +
                " MIN(kiekteis.kiekteisingu) AS maz, MAX(kiekteis.kiekteisingu) AS did\n" +
                "FROM  egzaminai LEFT  JOIN sesija  ON egzaminai.id=sesija.egzamino_id\n" +
                "LEFT JOIN \n" +
                "(SELECT s.id AS katra, COUNT(a.id) AS kiekteisingu\n" +
                "FROM sesija AS s\n" +
                "JOIN pasirinkimai  as p ON s.id=p.sesijos_id\n" +
                "JOIN atsakymai AS a ON (p.atsakymo_id=a.id AND a.arTeisingas=1)\n" +
                "GROUP BY s.id)\n" +
                " AS kiekteis\n" +
                "ON sesija.id=kiekteis.katra\n" +
                "left JOIN USERs  ON (sesija.user_id=users.id AND users.role='user')\n" +
                "GROUP BY egzaminai.pavadinimas;";
        List<Object[]> kiekTestuBaige = GetStatistic.getInfo( query1 );
        List<Object[]> laikymolaikas = GetStatistic.getInfo( query2 );
        List<Object[]> listKiekKartuLaike = GetStatistic.getInfo( query3 );
        List<Object[]> listTestoVidurkis = GetStatistic.getInfo( query4 );
        System.out.println();
        int kiek = listKiekKartuLaike.size();
        HashMap<String, List<String>> useStatistika = new HashMap<>();
        for (Object[] ob : listKiekKartuLaike
        ) {
            String name = (String) ob[0];
            String rez;
            if (ob[1] == null) rez = "";
            else rez = ob[1].toString();
            List<String> listStat = new ArrayList<>();
            listStat.add( rez );
            useStatistika.put( name, listStat );
        }
        for (Object[] ob : kiekTestuBaige
        ) {
            String name = (String) ob[0];
            String rez;
            if (ob[1] == null) rez = "";
            else rez = ob[1].toString();
            List<String> stat = useStatistika.get( name );
            stat.add( rez );
            useStatistika.put( name, stat );
        }

        for (Object[] ob : listTestoVidurkis
        ) {
            String name = (String) ob[0];
            String rez1, rez2, rez3;
            if (ob[3] == null) rez1 = "";
            else rez1 = ob[3].toString();
            if (ob[2] == null) rez2 = "";
            else rez2 = ob[2].toString();
            if (ob[2] == null) rez3 = "";
            else rez3 = ob[1].toString();
            List<String> stat = useStatistika.get( name );
            stat.add( rez1 );
            stat.add( rez2 );
            stat.add( rez3 );
            useStatistika.put( name, stat );
        }
        for (Object[] ob : laikymolaikas
        ) {
            String name = (String) ob[0];
            String rez1, rez2, rez3;
            if (ob[3] == null) rez1 = "";
            else rez1 = ob[3].toString();
            if (ob[2] == null) rez2 = "";
            else rez2 = ob[2].toString();
            if (ob[2] == null) rez3 = "";
            else rez3 = ob[1].toString();
            List<String> stat = useStatistika.get( name );
            stat.add( rez1 );
            stat.add( rez2 );
            stat.add( rez3 );
            useStatistika.put( name, stat );
        }
        System.out.println( "viso testu yra: " + kiek );
        System.out.println( "Pavadinimas\t \tLaike\tBaigė  \tMax_teisingu\tMin_teisingu\tVid_teisingu\tMax_laikas\tMin_laikas\tVid_laikas" );
        for (Map.Entry<String, List<String>> v : useStatistika.entrySet()
        ) {
            System.out.print( v.getKey() + "\t " );
            List<String> sk = v.getValue();
            for (String skai : sk
            ) {
                System.out.print( skai + "\t\t\t" );
            }
            System.out.println();
        }
    }
    }

