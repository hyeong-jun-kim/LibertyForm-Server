package com.example.libertyformapiserver.config.domain;

import com.example.libertyformapiserver.config.status.BaseStatus;
import jdk.jfr.Timestamp;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    private BaseStatus status = BaseStatus.ACTIVE;

    protected void setStatus(BaseStatus status) {
        this.status = status;
    }

    @PrePersist
    public void onPrePersist(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    public void onPreUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    public void changeStatusActive(){
        setStatus(BaseStatus.ACTIVE);
    }

    public void changeStatusInActive(){
        setStatus(BaseStatus.INACTIVE);
    }
}
