package com.e7erna1.unlimint.configuration.batchConfigurations.CSV;

import com.e7erna1.unlimint.entity.Payment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component("Convert-CSV")
public class CSVItemPaymentConverter implements Converter<String, Payment> {

  @Override
  public Payment convert(String s) {
    String[] split;
    split = s.split(",");
    Payment tempPayment = new Payment();

    try {
      tempPayment.setId(Integer.parseInt(split[0]));
    } catch (NumberFormatException exception) {
      tempPayment.setResult("Id cant be parsed to integer");
    }

    try {
      tempPayment.setAmount(Double.parseDouble(split[1]));
    } catch (NumberFormatException exception) {
      if (tempPayment.getResult() == null || "".equals(tempPayment.getResult())) {
        tempPayment.setResult("Amount cant be parsed to double");
      } else {
        tempPayment.setResult(tempPayment.getResult() + ", amount cant be parsed to double");
      }
    }

    if ("".equals(split[2])) {
      if (tempPayment.getResult() == null || "".equals(tempPayment.getResult())) {
        tempPayment.setResult("Currency is empty");
      } else {
        tempPayment.setResult(tempPayment.getResult() + ", currency is empty");
      }
    } else {
      tempPayment.setCurrency(split[2]);
    }

    if ("".equals(split[3])) {
      if (tempPayment.getResult() == null || "".equals(tempPayment.getResult())) {
        tempPayment.setResult("Comment is empty");
      } else {
        tempPayment.setResult(tempPayment.getResult() + ", comment is empty");
      }
    } else {
      tempPayment.setComment(split[3]);
    }

    if (tempPayment.getResult() == null || "".equals(tempPayment.getResult())) {
      tempPayment.setResult("OK");
    }

    return tempPayment;
  }

}
