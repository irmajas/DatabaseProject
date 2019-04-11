package studenttest.fileService;

import studenttest.datamodel.Atsakymas;
import studenttest.datamodel.Egzaminas;
import studenttest.datamodel.Klausimas;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadData {

    public static HashMap<Egzaminas, List<Klausimas>> readfromFile(Path kelias) {
        Egzaminas egzaminas = new Egzaminas();

        List<Klausimas> klausimasList = new ArrayList<>();
        BufferedReader reader;


        try {
            reader = Files.newBufferedReader( kelias );

            String line = "";
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            egzaminas.setPavadinimas( line );
            egzaminas.setKlausimusk( 8 );
            String kl = "", ats1 = "", ats2 = "", ats3 = "";
            int teis = 0;
            while (true) {

                try {
                    if (!((line = reader.readLine()) != null)) break;
                    kl = line;
                    ats1 = reader.readLine();
                    ats2 = reader.readLine();
                    ats3 = reader.readLine();
                    teis = Integer.parseInt( reader.readLine() );
                } catch (IOException e1) {
                    e1.printStackTrace();

                }
                Klausimas klausimas = new Klausimas();
                klausimas.setKlausimas( kl );
                klausimas.setEgzaminas( egzaminas );
                Atsakymas at1 = new Atsakymas( ats1, false );
                Atsakymas at2 = new Atsakymas( ats2, false );
                Atsakymas at3 = new Atsakymas( ats3, false );
                switch (teis) {
                    case 1:
                        at1.setArTeisingas( true );
                        break;
                    case 2:
                        at2.setArTeisingas( true );
                        break;
                    case 3:
                        at3.setArTeisingas( true );
                        break;

                }
                ;
                List<Atsakymas> atsakymasList = new ArrayList<>();
                atsakymasList.add( at1 );
                atsakymasList.add( at2 );
                atsakymasList.add( at3 );
                klausimas.setAtsakymas( atsakymasList );
                klausimasList.add( klausimas );
                ;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        HashMap<Egzaminas, List<Klausimas>> egz = new HashMap<>();
        egz.put( egzaminas, klausimasList );

        return egz;
    }

}



