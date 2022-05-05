package com.example.springbatch.excutionContextPrac.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class ExecutionTasklet1 implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("step1 실행됨");

        ExecutionContext jobExcutionContext = contribution.getStepExecution().getJobExecution()
            .getExecutionContext();

        ExecutionContext stepExcutionContext = contribution.getStepExecution().getExecutionContext();

        String jobName = chunkContext.getStepContext().getStepExecution().getJobExecution()
            .getJobInstance()
            .getJobName();

        String stepName = chunkContext.getStepContext().getStepExecution().getStepName();

        if (jobExcutionContext.get("ExecutionPracJob") == null) {
            jobExcutionContext.put("jobName", jobName);
        }

        if (stepExcutionContext.get("ExecutionPracJob_step1") == null) {
            stepExcutionContext.put("stepName", stepName);
        }

        System.out.println("jobName = " + jobExcutionContext.get("jobName"));
        System.out.println("stepName = " + stepExcutionContext.get("stepName"));
        return RepeatStatus.FINISHED;
    }
}
