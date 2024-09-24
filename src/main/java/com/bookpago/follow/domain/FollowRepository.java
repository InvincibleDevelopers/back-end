package com.bookpago.follow.domain;

import com.bookpago.follow.domain.Follow.FollowId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, FollowId> {

}
