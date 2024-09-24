package com.bookpago.follow.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"follower_id", "following_id"}))
public class Follow {

    @EmbeddedId
    private FollowId id;

    @Embeddable
    @NoArgsConstructor
    @EqualsAndHashCode
    public static class FollowId implements Serializable {

        @Column(insertable = false, updatable = false)
        private Long followerId;

        @Column(insertable = false, updatable = false)
        private Long followingId;
    }
}
