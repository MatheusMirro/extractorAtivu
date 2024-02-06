package com.mirro.extractor.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Embeddable
@Getter
@Setter
@Table(name = "PROFESSIONAL_HISTORY")
public class ProfessionalHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", nullable = false)
  private Long id;

  @Size(max = 255)
  @Column(name = "POSITION", nullable = false)
  private String position;

  @Size(max = 255)
  @Column(name = "COMPANY", nullable = false)
  private String company;

  @Size(max = 255)
  @Column(name = "PERIOD", nullable = false)
  private String period;
}
