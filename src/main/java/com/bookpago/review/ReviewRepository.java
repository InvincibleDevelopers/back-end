package com.bookpago.review;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query(value = "SELECT id, content, isbn, rating, author_profile_id FROM review WHERE author_profile_id = :userId ORDER BY id ASC LIMIT :size", nativeQuery = true)
    List<Review> findFirstReviewsByLastBookIsbn(@Param("userId") Long userId,
            @Param("size") int size);

    @Query(value = "SELECT id, content, isbn, rating, author_profile_id FROM review WHERE author_profile_id = :userId AND isbn > :lastBookIsbn ORDER BY id ASC LIMIT :size", nativeQuery = true)
    List<Review> findReviewsByLastBookIsbnAfterCursor(@Param("userId") Long userId,
            @Param("lastBookIsbn") Long lastBookIsbn, @Param("size") int size);
}
