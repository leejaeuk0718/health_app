package investlog_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")     // /api/ 로 시작하는 모든 경로에 적용
                .allowedOrigins("*")       // 모든 출처 허용 (개발용, 배포시 Flutter 앱 주소로 변경)
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드
                .allowedHeaders("*")       // 모든 헤더 허용
                .maxAge(3600);             // 1시간 캐싱
    }


}
