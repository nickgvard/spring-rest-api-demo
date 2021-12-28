package springrestapidemo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Nikita Gvardeev
 * 27.12.2021
 */

@Entity
@Table(name = "events")
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private FileEntity fileEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventEntity eventEntity = (EventEntity) o;
        return id.equals(eventEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}