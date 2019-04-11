package studenttest.datamodel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="pasirinkimai")
@Getter
@Setter
@NoArgsConstructor
public class Pasirinkimas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    int id;
    @Column
    String atsakymoraide;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "klausimo_id",referencedColumnName = "id")
    private Klausimas klausimas;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sesijos_id",referencedColumnName = "id")
    private Sesija sesija;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atsakymo_id",referencedColumnName = "id")
    private Atsakymas atsakymas;

    public Pasirinkimas(String atsakymoraide, Klausimas klausimas, Sesija sesija, Atsakymas atsakymas) {
        this.atsakymoraide = atsakymoraide;
        this.klausimas = klausimas;
        this.sesija = sesija;
        this.atsakymas = atsakymas;
    }
}
