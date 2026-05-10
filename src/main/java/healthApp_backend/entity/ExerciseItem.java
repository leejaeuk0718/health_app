package healthApp_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "exercise_items")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id")
    private Routine routine;

    @Column(name = "exercise_name", nullable = false)
    private String exerciseName;

    @Column(name = "exercise_set", nullable = false)
    private Integer exerciseSet;

    @Column(name = "reps", nullable = false)
    private String reps;

    @Column(name = "rest_seconds", nullable = false)
    private Integer restSeconds;

    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @Column(name = "notes")
    private String notes;

    // 전체 수정
    public void update(String exerciseName, Integer exerciseSet,
                       String reps, Integer restSeconds,
                       Integer orderIndex, String notes) {
        this.exerciseName = exerciseName;
        this.exerciseSet = exerciseSet;
        this.reps = reps;
        this.restSeconds = restSeconds;
        this.orderIndex = orderIndex;
        this.notes = notes;
    }

    // 메모만 수정
    public void updateNotes(String notes) {
        this.notes = notes;
    }

    // 순서만 수정 (드래그로 순서 바꿀 때)
    public void updateOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

    public void updateExerciseSet(Integer exerciseSet) {
        this.exerciseSet = exerciseSet;
    }

    public void updateRestSeconds(Integer restSeconds) {
        this.restSeconds = restSeconds;
    }

    public void updateReps(String reps) {
        this.reps = reps;
    }

    public void updateExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }


}
