package studenttest.datamodel;

import lombok.*;

import javax.persistence.*;

@Entity(name="users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    int id;
    @Column(unique = true)
    @NonNull
    String vardas;
    @Column
    String role;
    String password;

    public User(@NonNull String vardas, String role, String password) {
        this.vardas = vardas;
        this.role = role;
        this.password = password;
    }

    public User(int id) {
        this.id = id;
    }
}
