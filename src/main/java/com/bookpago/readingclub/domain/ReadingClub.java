package com.bookpago.readingclub.domain;

import com.bookpago.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 독서 모임
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadingClub extends BaseEntity {

    /**
     * 독서 모임 아이디
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 모임 이름
     */
    private String clubName;

    /**
     * 모임 설명
     */
    private String description;
}
