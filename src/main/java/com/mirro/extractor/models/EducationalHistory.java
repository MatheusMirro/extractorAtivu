package com.mirro.extractor.models;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Embeddable
@Getter
@Setter
@Table(name = "EDUCATIONAL_HISTORY")
public class EducationalHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", nullable = false)
  private Long id;

  @Size(max = 255)
  @Column(name = "DEGREE", nullable = false)
  private String degree;

  @Size(max = 255)
  @Column(name = "UNIVERSITY", nullable = false)
  private String university;

  @Size(max = 255)
  @Column(name = "COMPLETION_DATE", nullable = false)
  private String completionDate;
}
