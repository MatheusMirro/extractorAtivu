package com.mirro.extractor.dto;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonalDataDTO {

  @Size(max = 255)
  private String name;

  private int age;

  @Size(max = 255)
  private String address;

  @Size(max = 255)
  private String city;

  @Size(max = 255)
  private String zipCode;

  @Size(max = 255)
  private String email;

  @Size(max = 255)
  private String phone;
}
