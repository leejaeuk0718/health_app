package healthApp_backend.entity;

import healthApp_backend.enums.Difficulty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "routines")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Routine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User와 연결 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ExerciseItem> exerciseItems = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutRecord>  workoutRecords = new ArrayList<>();


    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "ai_summary", columnDefinition = "TEXT")
    private String aiSummary;

    @Column(name = "duration_minutes",nullable = false)
    private Integer durationMinutes;   // 소요 시간 (분)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    // 운동 부위
    @Column(name = "muscle_groups",nullable = false)
    private String muscleGroups;

    // YouTube 정보
    @Embedded
    private YoutubeInfo youtubeInfo;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isUserAdded = false;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isAiGenerated = false;

    @Column(nullable = false)
    @Builder.Default
    private Integer completedCount = 0;

    // 비즈니스 로직
    public void addExerciseItem(ExerciseItem item) {
        this.exerciseItems.add(item);
    }

    public void incrementCompletedCount() {
        this.completedCount++;
    }

    public List<String> getMuscleGroupList() {
        return List.of(this.muscleGroups.split(","));
    }
}


