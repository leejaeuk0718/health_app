package investlog_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass   // "이 클래스는 테이블 아니고 부모 클래스야" — DB 테이블 안 만들어짐
@EntityListeners(AuditingEntityListener.class)  // 자동으로 시간 채워주는 리스너
public abstract class BaseEntity {

    @CreatedDate                           // 생성 시간 자동 입력
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate                      // 수정 시간 자동 업데이트
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // 추가!
    private LocalDateTime deletedAt;

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public boolean isDeleted() {
        return this.deletedAt != null;
    }
}
