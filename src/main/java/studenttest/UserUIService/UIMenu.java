package studenttest.UserUIService;

import studenttest.databaseService.DBService;
import studenttest.datamodel.Egzaminas;
import studenttest.datamodel.User;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class UIMenu {

    public static void letsStart() {
        boolean iki = true;
        while (iki) {
            int pasirinkimas = mainMeniu();
            switch (pasirinkimas) {
                //sprendziam testus
                case 1:
                    User student = UIMethods.gautiUser();
                    if (student.getId() == 0) {
                        break;
                    }
                    Egzaminas egz = pasirinktiEgzamina();
                    if (egz == null)
                        System.out.println( "neteisingai nurodėte testo numerį" );
                    else {
                        UIMethods.sprendziam( egz, student );
                    }
                    break;
                //tvarkome klausimus
                case 2:
                    int permis = checkPermision();
                    if (permis == 2) {
                        System.out.println( "neteisingas vartotojo vardas" );
                        break;
                    }
                    if (permis == 1) {
                        System.out.println( "neteisingas slaptazodis" );
                        break;
                    }
                    int egzaminoPasirinkimas;
                    boolean poz = true;
                    while (poz) {
                        egzaminoPasirinkimas = egzaminoMeniu();
                        switch (egzaminoPasirinkimas) {
                            //ikelsim egzamina is failo
                            case 1:
                                DBService.naujasEgzaminas();

                                break;
                            //redaguosim egzamino klausimus
                             case 2:
                                 System.out.println("vienu metu galite redaguoti tik vieno egzamino klausimų rinkinį.");
                                 Egzaminas egzaminas=pasirinktiEgzamina();
                                 if (egzaminas!=null){
                                     klausimuMeniu(egzaminas);
                                 }

                                break;
                            default:
                                poz = false;

                        }
                    }
                    break;
                case 3:

                    System.out.println( "statistika" );
                    Statistika.usersStatistic();
                    Statistika.examsStatistic();
                    break;

                default:
                    iki = false;

            }
        }
    }

    private static void klausimuMeniu(Egzaminas egzaminas) {
        System.out.println( "Ką norite daryti:" );
        System.out.println( "1 - jei norite pridėti klausimus" );
        System.out.println( "2 - jei norite redaguoti klausimus" );
        Scanner sc = new Scanner( System.in );
        String ivesta = sc.next();
        switch (ivesta) {
            case "1":
                boolean kartoti=true;
               while(kartoti) {
                   try {
                       UIMethods.pridetiklausima( egzaminas );
                   } catch (IOException e) {
                       System.out.println( "penki pirstai nesusisneka su klavisais" );
                   }
                   System.out.println( "paspauskite 1, jie norite prideti dar viena klausima" );
                   String kas = sc.next();
                   if (!kas.equals( "1" )) kartoti=false;
               }
               break;
            case "2":


                try {
                    UIMethods.redaguotiKlausima( egzaminas );
                } catch (IOException e) {
                    System.out.println("penki pirstai nesusisneka su klavisais");
                }

            default:
                break;
        }
    }
    public static int mainMeniu() {

        System.out.println( "Ką norite daryti:" );
        System.out.println( "1 - jei norite spręsti testą" );
        System.out.println( "2 - jei norite tvarkyti egzaminų duomenų bazę" );
        System.out.println( "3 - jei norite matyti statistines suvestines" );
        System.out.println( "bent koks kitas skaičius, jei norite baigti darbą" );
        Scanner sc = new Scanner( System.in );
        String ivesta = sc.next();
        switch (ivesta) {
            case "1":
                return 1;
            case "2":
                return 2;
            case "3":
                return 3;
            default:
                return 0;
        }

    }

    public static int egzaminoMeniu() {
        Scanner sc = new Scanner( System.in );
        System.out.println( "Ką norite daryti:" );
        System.out.println( "1 - jei norite įkelti naują egzaminą" );
        System.out.println( "2 - jei norite redaguoti egzamino klausimus" );
        System.out.println( "bent koks kitas skaičius, jei norite baigti dirbti su klausimais" );
        String ivesta = sc.next();

        switch (ivesta) {
            case "1":
                return 1;
            case "2":
                return 2;
            default:
                return 0;
        }

    }

    public static int checkPermision() {
        Scanner sc = new Scanner( System.in );
        System.out.println( "Norėdami dirbti su klausimais, turite prisijungti " );
        System.out.print( "Vartotojo vardas: " );
        String vardas = sc.next();
        List<User> vart = DBService.getvisifromclass( "from users where vardas='" + vardas + "'" );
        if (vart.size() == 0) {

            return 2;
        }
        User vartotojas = vart.get( 0 );
        System.out.print( "iveskite slaptazodi -->" );
        String slapt = sc.next();
        if (slapt.equals( vartotojas.getPassword() )) {
            return 0;
        }
        return 1;
    }

    static public Egzaminas pasirinktiEgzamina() {
        DBService db = new DBService();
        Scanner sc = new Scanner( System.in );
        List<Egzaminas> egzaminai = db.getvisifromclass( "from egzaminai" );
        System.out.println( "kurį tastą renkiesi:" );
        for (Egzaminas eg : egzaminai
        ) {
            System.out.println( eg.getId() + " -- " + eg.getPavadinimas() );
            ;
        }
        String ivesta = sc.next();

        String pattern = "[0-9]+";
        int egzas;
        if (ivesta.matches( pattern )) {
            egzas = Integer.parseInt( ivesta );

            int aryra = (int) egzaminai.stream()
                    .filter( e -> e.getId() == egzas )
                    .count();
            if (aryra == 1) {
                Egzaminas yra = egzaminai.stream()
                        .filter( e -> e.getId() == egzas )
                        .findFirst().get();

                return yra;
            }
        }
        return null;
    }

}
