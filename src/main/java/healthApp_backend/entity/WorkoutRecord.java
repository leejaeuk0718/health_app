package healthApp_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "work_records")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id")
    private Routine routine;

    @Column(name = "record_title", nullable = false)
    private String recordTitle;

    @Column(name = "muscle_groups", nullable = false)
    private String muscleGroups;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Column(name = "memo")
    private String memo;

    private LocalDateTime completedAt;

    public void update(String recordTitle, String memo) {
    this.recordTitle = recordTitle;
    this.memo = memo;
    }

}
