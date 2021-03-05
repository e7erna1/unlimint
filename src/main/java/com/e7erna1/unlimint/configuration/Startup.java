package com.e7erna1.unlimint.configuration;

import com.e7erna1.unlimint.configuration.batch.JobConfiguration;
import com.e7erna1.unlimint.configuration.batch.JobInterface;
import java.util.Arrays;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Startup implements CommandLineRunner {

  private final JobLauncher jobLauncher;
  private final JobConfiguration jobConfiguration;

  public Startup(JobLauncher jobLauncher,
      JobConfiguration jobConfiguration) {
    this.jobLauncher = jobLauncher;
    this.jobConfiguration = jobConfiguration;
  }

  @Override
  public void run(String... args) {
    Arrays.stream(args).parallel().forEach(s -> {
      try {
        JobInterface jobInterface = jobConfiguration.jobTypeMap.get(s.substring(s.lastIndexOf(".") + 1));
        jobLauncher.run(jobInterface.Job(), new JobParametersBuilder().addString("path", s).toJobParameters());
      } catch (Exception ignored) { }
    });
  }
}
