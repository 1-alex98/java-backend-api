package org.grouporga.java.back.end.api.data.domain;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

/**
 * Since {@code AbstractEntity<UUID>} can't be used to specify the ID type because due to type erasure Elide can't
 * resolve the concrete type anymore, classes who use {@link UUID} as ID type are supposed to extend this class.
 */
@MappedSuperclass
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString
public abstract class AbstractUuidIdEntity extends AbstractEntity {

  protected UUID id;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  public UUID getId() {
    return id;
  }
}
