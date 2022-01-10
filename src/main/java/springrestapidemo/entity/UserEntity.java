package springrestapidemo.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @author Nikita Gvardeev
 * 27.12.2021
 */

@Entity
@Table(name = "users", schema = "local_db")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<RoleEntity> roles;

    @OneToMany(mappedBy = "userEntity")
    @ToString.Exclude
    private List<EventEntity> eventEntities;

}