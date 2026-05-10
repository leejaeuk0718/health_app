package healthApp_backend.entity;

import healthApp_backend.enums.MealType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "diet_records")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DietRecord extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime completedAt;

    @Embedded
    private NutritionInfo nutritionInfo;

    @Column(name = "ai_summary", columnDefinition = "TEXT")
    private String aiSummary;

    @Column(name = "food_url")
    private String foodUrl;

    @Enumerated(EnumType.STRING)
    private MealType mealType;

    @Column(name = "food_items",columnDefinition = "TEXT")
    private String foodItems;

    public void updateDietRecord(String foodItems, String foodUrl, MealType mealType, LocalDateTime completedAt) {
        this.foodItems = foodItems;
        this.foodUrl = foodUrl;
        this.mealType = mealType;
        this.completedAt = completedAt;
    }

    public void updateNutritionInfo(Integer calories, Integer carbs, Integer protein, Integer fat) {
        this.nutritionInfo.updateNutritionInfo(calories, carbs, protein, fat);
    }

}
