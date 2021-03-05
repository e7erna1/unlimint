package com.e7erna1.unlimint.configuration;

import com.e7erna1.unlimint.configuration.batchConfigurations.Conf;
import com.e7erna1.unlimint.configuration.batchConfigurations.generalMethods.JOBInterface;
import java.util.Arrays;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Startup implements CommandLineRunner {

  private final JobLauncher jobLauncher;
  private final Conf conf;

  public Startup(JobLauncher jobLauncher,
      Conf conf) {
    this.jobLauncher = jobLauncher;
    this.conf = conf;
  }

  @Override
  public void run(String... args) {
    Arrays.stream(args).parallel().forEach(s -> {
      try {
        JOBInterface jobInterface = conf.jobTypeMap.get(s.substring(s.lastIndexOf(".") + 1));
        jobLauncher.run(jobInterface.Job(), new JobParametersBuilder().addString("path", s).toJobParameters());
      } catch (Exception ignored) { }
    });
  }
}
