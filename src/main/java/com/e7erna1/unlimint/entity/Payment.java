package com.e7erna1.unlimint.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

  private Integer id;
  private Double amount;
  private String comment;
  private String currency;
  private String filename;
  private Integer line;
  private String result;

  @Override
  public String toString() {
    return "{" +
        "\"id\":" + id +
        ", \"amount\":" + amount +
        ", \"currency\":\"" + currency + '\"' +
        ", \"comment\":\"" + comment + '\"' +
        ", \"filename\":\"" + filename + '\"' +
        ", \"line\":" + line +
        ", \"result\":\"" + result + '\"' +
        '}';
  }
}
