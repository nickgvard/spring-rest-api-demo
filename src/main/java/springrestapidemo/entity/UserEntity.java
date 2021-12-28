package springrestapidemo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * @author Nikita Gvardeev
 * 27.12.2021
 */

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "userEntity")
    @ToString.Exclude
    private List<EventEntity> eventEntities;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity userEntity = (UserEntity) o;
        return id.equals(userEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}