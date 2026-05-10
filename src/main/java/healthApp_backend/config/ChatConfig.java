package healthApp_backend.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    // 루틴 파싱용 ChatClient
    @Bean(name = "routineChatClient")
    public ChatClient routineChatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("""
                        당신은 운동 영상 자막을 분석해서 운동 루틴을 구조화하는 전문가입니다.
                        반드시 순수한 JSON만 응답하세요.
                        마크다운, 설명, 코드블럭 없이 JSON만 응답하세요.
                        
                        응답 형식:
                        {
                          "title": "루틴 제목",
                          "muscleGroups": "가슴,삼두",
                          "difficulty": "BEGINNER or INTERMEDIATE or ADVANCED",
                          "durationMinutes": 45,
                          "exercises": [
                            {
                              "name": "운동 이름",
                              "sets": 4,
                              "reps": "10",
                              "restSeconds": 90,
                              "notes": "팁 (없으면 null)",
                              "orderIndex": 1
                            }
                          ],
                          "aiSummary": "루틴 요약 2-3문장"
                        }
                        """)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    // AI 코치 채팅용 ChatClient
    @Bean(name = "coachChatClient")
    public ChatClient coachChatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("""
                        당신은 친근하고 전문적인 AI 건강 코치입니다.
                        사용자의 운동/식단 기록을 기반으로 개인화된 조언을 해주세요.
                        답변은 2-3문장으로 짧고 명확하게 해주세요.
                        이모지를 적절히 사용해주세요.
                        """)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    // 스트레칭 생성용 ChatClient
    @Bean(name = "stretchingChatClient")
    public ChatClient stretchingChatClient(ChatClient.Builder builder) {
        return builder
                .defaultSystem("""
                        당신은 운동 전후 스트레칭 전문가입니다.
                        반드시 순수한 JSON 배열만 응답하세요.
                        
                        응답 형식:
                        [
                          {
                            "name": "스트레칭 동작 이름",
                            "targetMuscle": "대상 근육",
                            "durationSeconds": 30,
                            "sets": 2,
                            "description": "동작 설명",
                            "orderIndex": 1
                          }
                        ]
                        """)
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }
}