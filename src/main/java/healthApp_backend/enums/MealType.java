package healthApp_backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MealType {
    BREAKFAST("BREAKFAST_USER"),  // 아침
    LUNCH("LUNCH_USER"),      // 점심
    DINNER("DINNER_USER"),     // 저녁
    SNACK("SNACK_USER");       // 간식

    private final String value;
}
