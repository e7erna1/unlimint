package com.e7erna1.unlimint.configuration.batch;

import static java.util.stream.Collectors.toMap;

import com.e7erna1.unlimint.converter.CSVItemPaymentConverter;
import com.e7erna1.unlimint.converter.JSONItemPaymentConverter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class JobConfiguration {

  public final Map<String, JobInterface> jobTypeMap;
  private final JobRepository jobRepository;

  public JobConfiguration(JobRepository jobRepository,
      List<JobInterface> jobImpls) {
    this.jobRepository = jobRepository;
    this.jobTypeMap = jobImpls.stream().collect(toMap(JobInterface::myType, Function.identity()));
  }

  @Bean
  public ThreadPoolTaskExecutor taskExecutor() {
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    taskExecutor.setCorePoolSize(15);
    taskExecutor.setMaxPoolSize(20);
    taskExecutor.setQueueCapacity(30);
    return taskExecutor;
  }

  @Bean("Converter-CSV")
  public ConversionService conversionServiceCSV() {
    DefaultConversionService defaultConversionService = new DefaultConversionService();
    defaultConversionService.addConverter(new CSVItemPaymentConverter());
    return defaultConversionService;
  }

  @Bean("Converter-JSON")
  public ConversionService conversionServiceJSON() {
    DefaultConversionService defaultConversionService = new DefaultConversionService();
    defaultConversionService.addConverter(new JSONItemPaymentConverter());
    return defaultConversionService;
  }

  @Bean
  public JobLauncher asyncJobLauncher() {
    SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
    jobLauncher.setJobRepository(jobRepository);
    jobLauncher.setTaskExecutor(taskExecutor());
    return jobLauncher;
  }
}
