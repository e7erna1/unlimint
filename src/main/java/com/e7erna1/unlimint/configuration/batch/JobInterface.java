package com.e7erna1.unlimint.configuration.batch;

import org.springframework.batch.core.Job;

public interface JobInterface {

  Job Job();

  String myType();
}
