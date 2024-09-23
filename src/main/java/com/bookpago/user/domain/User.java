package com.bookpago.user.domain;

import static jakarta.persistence.EnumType.STRING;

import com.bookpago.BaseEntity;
import com.bookpago.review.Review;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원
 */
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {

    /**
     * 회원 아이디
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 리뷰 목록
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    /**
     * 회원 이름
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * 회원 프로필
     */
    private String profileUrl;

    /**
     * 닉네임
     */
    @Column(nullable = false)
    private String nickname;

    /**
     * 성별
     */
    @Enumerated(STRING)
    private Gender gender;

    /**
     * 생년월일
     */
    private LocalDate birth;
}

