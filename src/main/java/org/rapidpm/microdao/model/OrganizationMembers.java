package org.rapidpm.microdao.model;


import java.time.LocalDateTime;

public class OrganizationMembers {
  private Long orgId;
  private Long userId;
  private LocalDateTime createdAt;

  public Long getOrgId() {
    return orgId;
  }

  public void setOrgId(final Long orgId) {
    this.orgId = orgId;
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
