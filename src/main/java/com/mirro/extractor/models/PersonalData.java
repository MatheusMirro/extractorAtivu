package com.mirro.extractor.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PERSONAL_DATA")
public class PersonalData {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO) 
  @Column(name = "ID", nullable = false, unique = true)
  private Long id;

  @Size(max = 255)
  @Column(name = "NAME", nullable = false)
  private String name;

  @Column(name = "AGE", nullable = false)
  private int age;

  @Size(max = 255)
  @Column(name = "ADDRESS", nullable = false)
  private String address;

  @Size(max = 255)
  @Column(name = "CITY", nullable = false)
  private String city;

  @Size(max = 255)
  @Column(name = "ZIP_CODE", nullable = false)
  private String zipCode;

  @Size(max = 255)
  @Column(name = "EMAIL", nullable = false)
  private String email;

  @Size(max = 255)
  @Column(name = "PHONE", nullable = false)
  private String phone;
}
