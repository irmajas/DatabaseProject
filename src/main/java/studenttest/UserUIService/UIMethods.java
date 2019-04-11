package studenttest.UserUIService;

import studenttest.databaseService.DBService;
import studenttest.databaseService.SaveClasses;
import studenttest.datamodel.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class UIMethods {


    static void sprendziam(Egzaminas test, User student) {
        Scanner sc = new Scanner( System.in );
        Sesija sesija = new Sesija( LocalDateTime.now(), false, test, student );
        SaveClasses.saveoneclass( sesija );
        List<Klausimas> klsarasas = DBService.getvisifromclass( "from klausimai where egzamino_id=" + test.getId() );
        Collections.shuffle( klsarasas );
        int i = 1;
        int kiek = test.getKlausimusk();
        //   String raides[] = {"a", "b", "c"};
        ciklas:
        for (Klausimas kl : klsarasas
        ) {
            System.out.println( "testas " + test.getPavadinimas() );
            kl.print();
//            System.out.println( "" + i + ". " + kl.getKlausimas() );
//            int j = 0;
//            for (Atsakymas at : kl.getAtsakymas()
//            ) {
//                System.out.println( "" + raides[j] + ")" + at.getAtsakymas() );
//                j++;
//            }


            boolean poz = true;
            while (poz) {
                String pattern = "[abcABC]";

                System.out.print( "atsakymas--> " );
                String ats = sc.next();

                if (ats.matches( pattern )) {
                    poz = false;
                    System.out.println( "pasirinkote " + ats );
                    Atsakymas pasrinkAts = new Atsakymas();
                    switch (ats) {
                        case "a":
                        case "A":
                            pasrinkAts = kl.getAtsakymas().get( 0 );
                            break;
                        case "b":
                        case "B":
                            pasrinkAts = kl.getAtsakymas().get( 1 );
                            break;
                        case "c":
                        case "C":
                            pasrinkAts = kl.getAtsakymas().get( 2 );
                            break;
                    }
                    Pasirinkimas pasir = new Pasirinkimas( ats, kl, sesija, pasrinkAts );
                    SaveClasses.saveoneclass( pasir );
                } else {
                    System.out.println( "pasirinkite a arba b arba c" );
                    ;

                }
                i++;
                if (i > kiek) break ciklas;
            }
        }
        long rez = DBService.getTrueAnswersEgzm( sesija );
        sesija.setPabaiga( LocalDateTime.now() );
        sesija.setArBaigtas( true );
        SaveClasses.saveoneclass( sesija );
        System.out.println( "atsakėte į " + (i - 1) + " klausimus(ą/ų)" );
        System.out.println( "teisingų atsakymų skaičius " + rez );
    }

    public static User gautiUser() {
        User vartotojas = new User();
        Scanner sc = new Scanner( System.in );

        System.out.println( "Iveskite vartotojo vardą" );
        String vardas = sc.next();
        List<User> vart = DBService.getvisifromclass( "from users where vardas='" + vardas + "'" );
        if (vart.size() == 0) {
            System.out.println( "Esate naujas vartotojas. iveskite slaptažodį" );
            String pirmas = sc.next();
            System.out.println( "pakartokite slaptažodį" );
            String antras = sc.next();
            if (pirmas.equals( antras )) {
                vartotojas.setVardas( vardas );
                vartotojas.setPassword( pirmas );
                vartotojas.setRole( "user" );
                SaveClasses.saveoneclass( vartotojas );
                return vartotojas;
            } else System.out.println( "slaptazodziai nesutapo" );
        }
        vartotojas = vart.get( 0 );
        System.out.print( "iveskite slaptazodi -->" );
        String slapt = sc.next();
        if (slapt.equals( vartotojas.getPassword() )) {
            return vartotojas;
        }
        System.out.println( "nepavyko prisijungti, neteisingas slaptazodis" );
        return new User( 0 );
    }

    public static void pridetiklausima(Egzaminas egzaminas) throws IOException {
        Klausimas klausimas = new Klausimas();
        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
        System.out.println( "Iveskite klausimą:" );
        klausimas.setKlausimas( br.readLine() );
        System.out.println( "iveskite pirmą atsakymo variantą:" );
        Atsakymas atsakymas1 = new Atsakymas( br.readLine(), false );
        System.out.println( "iveskite antrą atsakymo variantą:" );
        Atsakymas atsakymas2 = new Atsakymas( br.readLine(), false );
        System.out.println( "iveskite trečią atsakymo variantą:" );
        Atsakymas atsakymas3 = new Atsakymas( br.readLine(), false );
        List<Atsakymas> atsakymas = new ArrayList<>();
        atsakymas.add( atsakymas1 );
        atsakymas.add( atsakymas2 );
        atsakymas.add( atsakymas3 );
        klausimas.setAtsakymas( atsakymas );
        klausimas.print();
        System.out.println( "Nurodykite teisingo atsakymo raidę" );

        boolean poz = true;
        while (poz) {
            String ivesta = br.readLine();
            switch (ivesta) {
                case "A":
                case "a":
                    klausimas.getAtsakymas().get( 0 ).setArTeisingas( true );
                    poz = false;
                    break;
                case "B":
                case "b":
                    klausimas.getAtsakymas().get( 1 ).setArTeisingas( true );
                    poz = false;
                    break;
                case "C":
                case "c":
                    klausimas.getAtsakymas().get( 2 ).setArTeisingas( true );
                    poz = false;
                    break;
                default:
                    System.out.println( "klaidinga teisingo atsakymo raidė, įveskite A arba B arba C" );
            }

        }
        klausimas.setEgzaminas( egzaminas );
        SaveClasses.saveKlausimas( klausimas );


    }

    public static void redaguotiKlausima(Egzaminas egzaminas) throws IOException {

        List<Klausimas> klausimai = DBService.getvisifromclass( "from klausimai where egzamino_id=" + egzaminas.getId() );
        while (true) {
            Klausimas klausimasredag = kuriKlausimaredaguosim( klausimai );
            if (klausimasredag == null) return;
            BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
            String line;
            System.out.println( "Klausimas: " + klausimasredag.getKlausimas() );
            System.out.println( "Iveskite naują klausimą arba paspauskite ENTER, jei nenorite keisti:" );
            line = br.readLine();
            if (!line.equals( "" )) klausimasredag.setKlausimas( line );
            List<Atsakymas> atsakymasList = klausimasredag.getAtsakymas();
            for (Atsakymas at : atsakymasList
            ) {

                System.out.println(at.getAtsakymas())
                ;System.out.println( "iveskite naują atsakymo variantą arba paspauskite ENTER, jei nenorite keisti:" );
                line = br.readLine();
                if (!line.equals( "" )) at.setAtsakymas( line );
            }


            klausimasredag.setAtsakymas( atsakymasList );
            klausimasredag.print();
            System.out.println( "Nurodykite teisingo atsakymo raidę" );

            boolean poz = true;
            while (poz) {
                String ivesta = br.readLine();
                switch (ivesta) {
                    case "A":
                    case "a":
                        klausimasredag.getAtsakymas().get( 0 ).setArTeisingas( true );
                        poz = false;
                        break;
                    case "B":
                    case "b":
                        klausimasredag.getAtsakymas().get( 1 ).setArTeisingas( true );
                        poz = false;
                        break;
                    case "C":
                    case "c":
                        klausimasredag.getAtsakymas().get( 2 ).setArTeisingas( true );
                        poz = false;
                        break;
                    default:
                        System.out.println( "klaidinga teisingo atsakymo raidė, įveskite A arba B arba C" );
                }

            }
            SaveClasses.saveKlausimas( klausimasredag );
        }
    }


    public static Klausimas kuriKlausimaredaguosim(List<Klausimas> klausimasList) {
        Scanner sc = new Scanner( System.in );
        for (Klausimas kl : klausimasList) {
            System.out.println( kl.getId() + " " + kl.getKlausimas() );

        }
        String pattern = "[0-9]+";

        Klausimas klausred;
        while (true) {
            System.out.print( "pasirinkite klausimo numeri arba 0, jei norite baigti-->" );

            String ivesta = sc.next();
            if (ivesta.equals( "0" )) return null;
            if (ivesta.matches( pattern )) {
                int klid = Integer.parseInt( ivesta );
                klausred = klausimasList.stream()
                        .filter( klausimas -> klausimas.getId() == klid ).findAny().orElse( null );
                if (klausred != null) return klausred;
            }

        }
    }
}


