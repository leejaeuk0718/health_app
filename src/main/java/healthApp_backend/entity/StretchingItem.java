package healthApp_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "stretching_items")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StretchingItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // StretchingRoutine과 연결 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stretching_routine_id")
    private StretchingRoutine stretchingRoutine;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "sets", nullable = false)
    private Integer sets;

    @Column(name = "reps", nullable = false)
    private Integer reps;

    // 스트레칭만 있는 필드 — 유지 시간 (초)
    @Column(name = "duration_seconds", nullable = false)
    private Integer durationSeconds;

    @Column(name = "rest_seconds", nullable = false)
    private Integer restSeconds;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @Column(name = "notes")
    private String notes;

    // 비즈니스 로직
    public void update(String name, Integer sets, Integer reps,
                       Integer durationSeconds, Integer restSeconds,
                       Integer orderIndex, String notes) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
        this.durationSeconds = durationSeconds;
        this.restSeconds = restSeconds;
        this.orderIndex = orderIndex;
        this.notes = notes;
    }

    public void updateOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public void updateNotes(String notes) {
        this.notes = notes;
    }
}