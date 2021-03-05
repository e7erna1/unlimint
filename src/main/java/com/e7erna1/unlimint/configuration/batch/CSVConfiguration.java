package com.e7erna1.unlimint.configuration.batch;

import com.e7erna1.unlimint.entity.Payment;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CSVConfiguration implements JobInterface {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final ItemReader<Payment> paymentItemReader;
  private final ItemProcessor<Payment, Payment> paymentPaymentItemProcessor;
  private final ItemWriter<Payment> paymentItemWriter;

  public CSVConfiguration(
      JobBuilderFactory jobBuilderFactory,
      StepBuilderFactory stepBuilderFactory,
      @Qualifier("Reader-CSV") ItemReader<Payment> paymentItemReader,
      ItemProcessor<Payment, Payment> paymentPaymentItemProcessor,
      ItemWriter<Payment> paymentItemWriter) {
    this.jobBuilderFactory = jobBuilderFactory;
    this.stepBuilderFactory = stepBuilderFactory;
    this.paymentItemReader = paymentItemReader;
    this.paymentPaymentItemProcessor = paymentPaymentItemProcessor;
    this.paymentItemWriter = paymentItemWriter;
  }

  @Bean("CSVJob")
  public Job Job() {
    Step step = stepBuilderFactory
        .get("step")
        .<Payment, Payment>chunk(1)
        .reader(paymentItemReader)
        .processor(paymentPaymentItemProcessor)
        .writer(paymentItemWriter)
        .build();
    return jobBuilderFactory
        .get("CSVJob")
        .start(step)
        .build();
  }

  @Override
  public String myType() {
    return "csv";
  }
}
