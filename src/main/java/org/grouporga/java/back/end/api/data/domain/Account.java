package org.grouporga.java.back.end.api.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yahoo.elide.annotation.*;
import lombok.Setter;
import lombok.ToString;
import org.grouporga.java.back.end.api.data.checks.IsEntityOwner;
import org.grouporga.java.back.end.api.data.checks.Prefab;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;

@Setter
@Entity
@Table(name = "account")
@Include(rootLevel = true, type = "account")
// Needed to change leader of a clan
@ToString(of = {"displayName"})
public class Account extends AbstractIntegerIdEntity implements OwnableEntity {
  private String displayName;
  private String firstName;
  private String surName;
  private String password;
  private String email;
  private String lastLoginUserAgent;
  private String lastLoginIpAddress;
  private OffsetDateTime lastLoginTime;
  private Set<GroupMemberShip> groupMemberShips;


  @Column(name = "display_name", nullable = false)
  public String getDisplayName() {
    return displayName;
  }

  @Column(name = "password", nullable = false)
  @ReadPermission(expression = Prefab.NONE)
  public String getPassword() {
    return password;
  }

  @Column(name = "last_login_user_agent")
  @ReadPermission(expression = Prefab.NONE)
  public String getLastLoginUserAgent() {
    return lastLoginUserAgent;
  }

  @Column(name = "last_login_ip_address")
  @ReadPermission(expression = Prefab.NONE)
  public String getLastLoginIpAddress() {
    return lastLoginIpAddress;
  }

  @Column(name = "last_login_time")
  @ReadPermission(expression = Prefab.NONE)
  public OffsetDateTime getLastLoginTime() {
    return lastLoginTime;
  }

  @Column(name = "email", nullable = false)
  @ReadPermission(expression = IsEntityOwner.EXPRESSION)
  public String getEmail() {
    return email;
  }

  @Column(name = "first_name", nullable = true)
  @ReadPermission(expression = IsEntityOwner.EXPRESSION)
  public String getFirstName() {
    return firstName;
  }

  @Column(name = "sur_name", nullable = true)
  @ReadPermission(expression = IsEntityOwner.EXPRESSION)
  public String getSurName() {
    return surName;
  }

  @Transient
  public boolean isLocked() {
    return false;
  }

  @Override
  @Transient
  @JsonIgnore
  public Account getEntityOwner() {
    return this;
  }
}
