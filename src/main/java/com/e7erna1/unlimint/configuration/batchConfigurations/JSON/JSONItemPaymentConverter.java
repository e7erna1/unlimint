package com.e7erna1.unlimint.configuration.batchConfigurations.JSON;

import com.e7erna1.unlimint.entity.Payment;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component("Convert-JSON")
public class JSONItemPaymentConverter implements Converter<String, Payment> {

  @Override
  public Payment convert(String s) {
    Payment tempPayment = new Payment();

    JSONObject jObj;
    Object idObject;
    Object amountObject;
    Object currencyObject;
    Object commentObject;

    try {
      jObj = new JSONObject(s);
      try {
        idObject = jObj.get("orderId");
        try {
          tempPayment.setId(Integer.parseInt(idObject.toString()));
        } catch (NumberFormatException exception) {
          tempPayment.setResult("Id cant be parsed to integer");
        }
      } catch (Exception e) {
        tempPayment.setResult("Id cant be parsed or empty");
      }

      try {
        amountObject = jObj.get("amount");
        try {
          tempPayment.setAmount(Double.parseDouble(amountObject.toString()));
        } catch (NumberFormatException exception) {
          if (tempPayment.getResult() == null || "".equals(tempPayment.getResult())) {
            tempPayment.setResult("Amount cant be parsed to double");
          } else {
            tempPayment.setResult(tempPayment.getResult() + ", amount cant be parsed to double");
          }
        }
      } catch (Exception e) {
        if (tempPayment.getResult() == null || "".equals(tempPayment.getResult())) {
          tempPayment.setResult("Amount cant be parsed or empty");
        } else {
          tempPayment.setResult(tempPayment.getResult() + ", amount cant be parsed or empty");
        }
      }

      try {
        currencyObject = jObj.get("currency");
        if (!"".equals(currencyObject) && currencyObject != null) {
          tempPayment.setCurrency(currencyObject.toString());
        } else {
          if (tempPayment.getResult() == null || "".equals(tempPayment.getResult())) {
            tempPayment.setResult("Currency is empty");
          } else {
            tempPayment.setResult(tempPayment.getResult() + ", currency is empty");
          }
        }
      } catch (Exception e) {
        if (tempPayment.getResult() == null || "".equals(tempPayment.getResult())) {
          tempPayment.setResult("Currency cant be parsed or empty");
        } else {
          tempPayment.setResult(tempPayment.getResult() + ", currency cant be parsed or empty");
        }
      }

      try {
        commentObject = jObj.get("comment");
        if (!"".equals(commentObject) && commentObject != null) {
          tempPayment.setComment(commentObject.toString());
        } else {
          if (tempPayment.getResult() == null || "".equals(tempPayment.getResult())) {
            tempPayment.setResult("Comment is empty");
          } else {
            tempPayment.setResult(tempPayment.getResult() + ", comment is empty");
          }
        }
      } catch (Exception e) {
        if (tempPayment.getResult() == null || "".equals(tempPayment.getResult())) {
          tempPayment.setResult("Comment cant be parsed or empty");
        } else {
          tempPayment.setResult(tempPayment.getResult() + ", comment cant be parsed or empty");
        }
      }
    } catch (Exception e) {
      if (tempPayment.getResult() == null || "".equals(tempPayment.getResult())) {
        tempPayment.setResult("JSON cant be parsed or empty");
      }
    }

    if (tempPayment.getResult() == null || "".equals(tempPayment.getResult())) {
      tempPayment.setResult("OK");
    }

    return tempPayment;
  }

}
