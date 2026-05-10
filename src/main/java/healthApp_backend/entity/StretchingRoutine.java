package healthApp_backend.entity;

import healthApp_backend.enums.StretchingTiming;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "stretching_routines")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StretchingRoutine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User와 연결 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // StretchingItem 목록 (1:N)
    @OneToMany(mappedBy = "stretchingRoutine",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    private List<StretchingItem> stretchingItems = new ArrayList<>();

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "ai_summary", columnDefinition = "TEXT")
    private String aiSummary;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    // 운동 부위 (어떤 부위 운동 후 스트레칭인지)
    @Column(name = "target_muscles", nullable = false)
    private String targetMuscles;      // "가슴,삼두"

    // 운동 전/후/언제든지 구분
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StretchingTiming timing;   // BEFORE / AFTER / ANYTIME

    // AI가 생성했는지 유튜브 기반인지
    @Column(nullable = false)
    @Builder.Default
    private Boolean isAiGenerated = false;

    // 유튜브 기반 스트레칭이면 유튜브 정보
    @Embedded
    private YoutubeInfo youtubeInfo;

    // 비즈니스 로직
    public void addStretchingItem(StretchingItem item) {
        this.stretchingItems.add(item);
    }

    public List<String> getTargetMuscleList() {
        return List.of(this.targetMuscles.split(","));
    }
}