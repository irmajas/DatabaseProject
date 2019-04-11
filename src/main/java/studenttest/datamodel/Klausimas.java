package studenttest.datamodel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity(name="klausimai")
@Getter
@Setter
@NoArgsConstructor
public class Klausimas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    int id;
    String klausimas;
    @OneToMany
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name="egzamino_id")
    List<Atsakymas> atsakymas;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "egzamino_id",referencedColumnName = "id",updatable = false)
    private Egzaminas egzaminas;

    public void print(){
        String raides[] = {"a", "b", "c"};
        System.out.println( this.getKlausimas() );
        int j = 0;
        for (Atsakymas at :this.getAtsakymas()
        ) {
            System.out.println( "" + raides[j] + ")" + at.getAtsakymas() );
            j++;
        }
    }
}
