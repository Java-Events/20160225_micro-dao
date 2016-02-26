package org.rapidpm.microdao.model;

import java.time.LocalDateTime;

public class User {

  private Long id;
  private String login;
  private String name;
  private String company;
  private String location;
  private String email;
  private LocalDateTime createdAt;
  private String type;
  private Boolean fake;
  private Boolean deleted;
  private Double longitude;
  private Double lat;
  private String countryCode;
  private String state;
  private String city;

  public User(final Long id, final String login, final String name, final String company,
              final String location, final String email, final LocalDateTime createdAt,
              final String type, final Boolean fake, final Boolean deleted, final Double longitude,
              final Double lat, final String countryCode, final String state, final String city) {
    this.id = id;
    this.login = login;
    this.name = name;
    this.company = company;
    this.location = location;
    this.email = email;
    this.createdAt = createdAt;
    this.type = type;
    this.fake = fake;
    this.deleted = deleted;
    this.longitude = longitude;
    this.lat = lat;
    this.countryCode = countryCode;
    this.state = state;
    this.city = city;
  }

  public Long getId() {
    return id;
  }

  public String getLogin() {
    return login;
  }

  public String getName() {
    return name;
  }

  public String getCompany() {
    return company;
  }

  public String getLocation() {
    return location;
  }

  public String getEmail() {
    return email;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public String getType() {
    return type;
  }

  public Boolean getFake() {
    return fake;
  }

  public Boolean getDeleted() {
    return deleted;
  }

  public Double getLongitude() {
    return longitude;
  }

  public Double getLat() {
    return lat;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public String getState() {
    return state;
  }

  public String getCity() {
    return city;
  }
}
