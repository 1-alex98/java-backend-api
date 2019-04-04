package org.grouporga.java.back.end.api.data.domain;

import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.OffsetDateTime;

@MappedSuperclass
@Setter
public abstract class AbstractEntity {

  protected OffsetDateTime createTime;
  protected OffsetDateTime updateTime;

  @CreatedDate
  @Column(name = "create_time")
  public OffsetDateTime getCreateTime() {
    return createTime;
  }

  @LastModifiedDate
  @Column(name = "update_time")
  public OffsetDateTime getUpdateTime() {
    return updateTime;
  }

  /**
   * Supplement method for @EqualsAndHashCode overriding the default lombok implementation
   */
  protected boolean canEqual(Object other) {
    return other instanceof AbstractEntity && this.getClass() == other.getClass();
  }
}
