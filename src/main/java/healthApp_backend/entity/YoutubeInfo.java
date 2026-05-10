package healthApp_backend.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable  // "나는 테이블 아니고 다른 엔티티에 포함되는 값이야"
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YoutubeInfo {

    private String videoId;
    private String channelName;
    private Long viewCount;

    // 유튜브 URL 자동 생성
    // videoId만 있으면 URL을 만들 수 있어서 따로 저장 안 해요
    public String getVideoUrl() {
        if (videoId == null) return null;
        return "https://youtube.com/watch?v=" + videoId;
    }

    // 썸네일도 videoId로 자동 생성
    public String getThumbnailUrl() {
        if (videoId == null) return null;
        return "https://img.youtube.com/vi/" + videoId + "/maxresdefault.jpg";
    }
}