package healthApp_backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OpenAiService {

    private final ChatClient routineChatClient;
    private final ChatClient coachChatClient;
    private final ChatClient stretchingChatClient;

    // @Qualifier로 어떤 Bean인지 지정
    public OpenAiService(
            @Qualifier("routineChatClient") ChatClient routineChatClient,
            @Qualifier("coachChatClient") ChatClient coachChatClient,
            @Qualifier("stretchingChatClient") ChatClient stretchingChatClient) {
        this.routineChatClient = routineChatClient;
        this.coachChatClient = coachChatClient;
        this.stretchingChatClient = stretchingChatClient;
    }

    /**
     * 유튜브 자막 → 운동 루틴 파싱
     */
    public String parseRoutineFromCaption(String caption,
                                          String videoTitle,
                                          String channelName) {
        String userPrompt = String.format("""
                유튜브 영상 정보:
                - 채널: %s
                - 제목: %s
                
                자막/설명:
                %s
                
                위 영상에서 운동 루틴을 추출해서 JSON으로 응답해주세요.
                """, channelName, videoTitle, caption);

        return routineChatClient
                .prompt()
                .user(userPrompt)
                .call()
                .content();  // 응답 텍스트만 바로 꺼내요
    }

    /**
     * 스트레칭 루틴 생성
     */
    public String generateStretching(String muscleGroups, String timing) {
        String timingKr = timing.equals("before") ? "운동 전 워밍업" : "운동 후 마무리";

        String userPrompt = String.format("""
                오늘 운동한 부위: %s
                스트레칭 목적: %s
                
                4-6가지 스트레칭을 JSON 배열로 응답해주세요.
                총 소요시간 8-12분 내외로 구성해주세요.
                """, muscleGroups, timingKr);

        return stretchingChatClient
                .prompt()
                .user(userPrompt)
                .call()
                .content();
    }

    /**
     * AI 코치 채팅
     */
    public String chat(String userMessage, String userContext) {

        // 사용자 현황을 시스템 프롬프트에 동적으로 추가
        String fullMessage = String.format("""
                사용자 현황:
                %s
                
                질문: %s
                """, userContext, userMessage);

        return coachChatClient
                .prompt()
                .user(fullMessage)
                .call()
                .content();
    }
}