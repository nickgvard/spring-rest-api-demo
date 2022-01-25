package springrestapidemo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @author Nikita Gvardeev
 * 04.01.2022
 */

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<UserEntity> users;
}
