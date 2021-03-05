package com.e7erna1.unlimint.processor;

import com.e7erna1.unlimint.entity.Payment;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;


@Component
public class Processor implements ItemProcessor<Payment, Payment> {

  @Override
  public Payment process(Payment payment) {
    return payment;
  }
}
