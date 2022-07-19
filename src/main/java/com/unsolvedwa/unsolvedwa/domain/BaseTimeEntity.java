package com.unsolvedwa.unsolvedwa.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass //공통맵핑정보
@EntityListeners(AuditingEntityListener.class) //entity를감시?
public abstract class BaseTimeEntity {

  @CreatedDate
  @Column(nullable = false)
  private LocalDateTime createAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  protected void setCreateAt(LocalDateTime createAt)
  {
    this.createAt = createAt;
  }
}
