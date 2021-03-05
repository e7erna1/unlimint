package com.e7erna1.unlimint.writer;

import com.e7erna1.unlimint.entity.Payment;
import java.util.List;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class MyItemWriter implements ItemWriter<Payment> {

  @Override
  public void write(List<? extends Payment> list) {
    for (Payment payment : list) {
      System.out.println(payment.toString());
    }
  }
}
