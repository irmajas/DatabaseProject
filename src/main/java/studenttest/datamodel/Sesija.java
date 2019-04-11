package studenttest.datamodel;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="sesija")
@Getter
@Setter
@NoArgsConstructor
public class Sesija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    int id;
    LocalDateTime pradzia;
    LocalDateTime pabaiga;
    boolean arBaigtas;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "egzamino_id",referencedColumnName = "id")
    private Egzaminas egzaminas;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User useris;

    public Sesija(LocalDateTime pradzia, boolean arBaigtas, Egzaminas egzaminas, User useris) {
        this.pradzia = pradzia;
        this.arBaigtas = arBaigtas;
        this.egzaminas = egzaminas;
        this.useris = useris;
    }

    @Override
    public String toString() {
        return "Sesija{" +
                "id=" + id +
                ", pradzia=" + pradzia +
                ", pabaiga=" + pabaiga +
                ", arBaigtas=" + arBaigtas +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sesija sesija = (Sesija) o;

        return getId() == sesija.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }
}
