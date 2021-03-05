package com.e7erna1.unlimint.reader;

import com.e7erna1.unlimint.entity.Payment;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@StepScope
@Component("Reader-CSV")
public class CSVItemReader implements ItemReader<Payment> {

  private final ConversionService conversionService;
  private File file;
  private Scanner scanner;
  private final Class<Payment> classType = Payment.class;
  private Integer lineNumber = 0;

  public CSVItemReader(@Qualifier("Converter-CSV") ConversionService conversionService) {
    this.conversionService = conversionService;
  }

  @Value("#{jobParameters['path']}")
  private void setFile(String path) {
    this.file = new File(path);
  }

  @Override
  public Payment read() {
    if (scanner.hasNextLine()) {
      lineNumber++;
      String line = scanner.nextLine();
      Payment tempPayment = conversionService.convert(line, classType);
      if (tempPayment != null) {
        tempPayment.setFilename(file.getName());
        tempPayment.setLine(lineNumber);
        return tempPayment;
      } else {
        return null;
      }
    } else {
      return null;
    }
  }

  @BeforeStep
  public void open() throws FileNotFoundException {
    scanner = new Scanner(file);
  }

  @AfterStep
  public void destroy() {
    if (scanner != null) {
      scanner.close();
    }
  }
}
