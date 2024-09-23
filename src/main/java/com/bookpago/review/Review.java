package com.bookpago.review;

import static jakarta.persistence.FetchType.LAZY;

import com.bookpago.BaseEntity;
import com.bookpago.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder(toBuilder = true)  // toBuilder = true 설정
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseEntity {

    /**
     * 리뷰 아이디
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 도서 고유번호
     */
    private Long isbn;

    /**
     * 리뷰 내용
     */
    private String reviewContent;

    /**
     * 평점
     */
    private double rating;

    /**
     * 좋아요 수
     */
    private int likeCount;
}
