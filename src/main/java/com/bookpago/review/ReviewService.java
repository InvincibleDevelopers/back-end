package com.bookpago.review;

import com.bookpago.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // 안전하게 Number 타입을 Long으로 변환하는 메서드
    private Long convertToLong(Object obj) {
        if (obj instanceof Integer) {
            return ((Integer) obj).longValue();  // Integer -> Long 변환
        }

        if (obj instanceof Long) {
            return (Long) obj;  // 이미 Long이면 그대로 반환
        }

        throw new IllegalArgumentException(
                "Unsupported type for conversion to Long: " + obj.getClass().getName());
    }

    public List<Review> getMyReviews(User user, Long lastBookId, int size) {
        if (lastBookId == null) {
            return reviewRepository.findFirstReviewsByLastBookIsbn(user.getId(), size);
        }

        return reviewRepository.findReviewsByLastBookIsbnAfterCursor(user.getId(),
                lastBookId, size);
    }
}
