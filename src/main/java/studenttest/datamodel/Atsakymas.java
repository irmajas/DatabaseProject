package studenttest.datamodel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="atsakymai")
@Getter
@Setter
@NoArgsConstructor
public class Atsakymas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    int id;
    @Column
    String atsakymas;
    @Column
    boolean arTeisingas;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "klausimo_id",referencedColumnName = "id" )
    private Klausimas klausimas;

    public Atsakymas(String atsakymas, boolean arTeisingas) {
        this.atsakymas = atsakymas;
        this.arTeisingas = arTeisingas;
    }

}
