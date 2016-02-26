package org.rapidpm.microdao.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Projects {

  private Long id;
  private String url;
  private Long ownerId;
  private String name;
  private String description;
  private String language;
  private LocalDateTime createdAt;
  private Long forkedFrom;
  private String deleted;
  private java.sql.Timestamp updatedAt;

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(final String url) {
    this.url = url;
  }

  public Long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(final Long ownerId) {
    this.ownerId = ownerId;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(final String language) {
    this.language = language;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(final LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public Long getForkedFrom() {
    return forkedFrom;
  }

  public void setForkedFrom(final Long forkedFrom) {
    this.forkedFrom = forkedFrom;
  }

  public String getDeleted() {
    return deleted;
  }

  public void setDeleted(final String deleted) {
    this.deleted = deleted;
  }

  public Timestamp getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(final Timestamp updatedAt) {
    this.updatedAt = updatedAt;
  }
}
