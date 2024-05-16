package com.sparta.calendar.entitiy;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class DayStamp {
    @CreatedDate
    @Column(updatable = false)
    @Temporal( TemporalType.TIMESTAMP )
    private LocalDateTime created;
}

