package studenttest.datamodel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name="egzaminai")
@Getter
@Setter
@NoArgsConstructor
public class Egzaminas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    int id;
    @Column
    String pavadinimas;
    int klausimusk;

    @Override
    public String toString() {
        return "Egzaminas{" +
                "id=" + id +
                ", pavadinimas='" + pavadinimas + '\'' +
                ", klausimusk=" + klausimusk +
                '}';
    }
}
