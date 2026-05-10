package healthApp_backend.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StretchingTiming {
    BEFORE("BEFORE_USER"),  // 운동 전
    AFTER("AFTER_USER"),   // 운동 후
    ANYTIME("ANYTIME_USER");  // 언제든지 (별도 탭에서)

    private final String value;
}
