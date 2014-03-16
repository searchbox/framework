package com.searchbox.collection;

import java.util.Date;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

public interface SynchronizedCollection {

  public Date getLastUpdate();

  public void synchronize() throws JobExecutionAlreadyRunningException,
      JobRestartException, JobInstanceAlreadyCompleteException,
      JobParametersInvalidException;

}
