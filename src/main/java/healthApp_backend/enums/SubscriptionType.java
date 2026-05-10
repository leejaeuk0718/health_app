package healthApp_backend.enums;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SubscriptionType {

    FREE("FREE_USER"),
    PREMIUM("PREMIUM_USER"),
    ANNUAL("ANNUAL_USER");

    private final String value;

    }
