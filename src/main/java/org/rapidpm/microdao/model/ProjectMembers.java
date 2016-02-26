package org.rapidpm.microdao.model;

import java.time.LocalDateTime;

public class ProjectMembers {
  private Long repoId;
  private Long userId;
  private LocalDateTime createdAt;
  private String extRefId;

  public Long getRepoId() {
    return repoId;
  }

  public void setRepoId(Long repoId) {
    this.repoId = repoId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public String getExtRefId() {
    return extRefId;
  }

  public void setExtRefId(String extRefId) {
    this.extRefId = extRefId;
  }
}
