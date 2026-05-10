package healthApp_backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Difficulty {
    BEGINNER("BEGINNER_USER"), // 초급
    INTERMEDIATE("INTERMEDIATE_USER"), // 중급
    ADVANCED("ADVANCED_USER"),; // 고급
    private final String value;

    }
