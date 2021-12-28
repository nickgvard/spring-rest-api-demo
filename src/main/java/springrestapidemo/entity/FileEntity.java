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
@Table(name = "files")
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String location;

    @OneToMany(mappedBy = "fileEntity")
    @ToString.Exclude
    private List<EventEntity> eventEntities;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileEntity fileEntity = (FileEntity) o;
        return id.equals(fileEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
