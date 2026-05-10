package healthApp_backend.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NutritionInfo {

    private Integer calories;    // 칼로리
    private Integer carbs;       // 탄수화물
    private Integer protein;     // 단백질
    private Integer fat;         // 지방


    // 탄수화물+단백질+지방으로 칼로리 직접 계산
    public Integer calculateCalories() {
        if (carbs == null || protein == null || fat == null) return 0;
        return (carbs * 4) + (protein * 4) + (fat * 9);
    }

    // 탄수화물 비율 (%)
    public Double carbsRatio() {
        if (calories == null || calories == 0) return 0.0;
        return (carbs * 4.0 / calories) * 100;
    }

    // 단백질 비율 (%)
    public Double proteinRatio() {
        if (calories == null || calories == 0) return 0.0;
        return (protein * 4.0 / calories) * 100;
    }

    // 지방 비율 (%)
    public Double fatRatio() {
        if (calories == null || calories == 0) return 0.0;
        return (fat * 9.0 / calories) * 100;
    }

    public void updateNutritionInfo(Integer calories, Integer carbs, Integer protein, Integer fat){
        this.calories = calories;
        this.carbs = carbs;
        this.protein = protein;
        this.fat = fat;
    }
}
