package springrestapidemo.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @author Nikita Gvardeev
 * 27.12.2021
 */

@Entity
@Table(name = "events", schema = "local_db")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private FileEntity fileEntity;

    private String description;

}