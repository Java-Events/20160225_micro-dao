package org.rapidpm.microdao.model;

import java.time.LocalDateTime;

public class Followers {
  private Long followerId;
  private Long userId;
  private LocalDateTime createdAt;

  public Long getFollowerId() {
    return followerId;
  }

  public void setFollowerId(final Long followerId) {
    this.followerId = followerId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(final Long userId) {
    this.userId = userId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(final LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
