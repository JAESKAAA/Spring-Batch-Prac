package com.example.springbatch.excutionContextPrac.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class ExecutionTasklet2 implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("step2 실행됨");

        ExecutionContext jobExecutionContext = chunkContext.getStepContext().getStepExecution()
            .getJobExecution().getExecutionContext();

        ExecutionContext stepExecutionContext = chunkContext.getStepContext().getStepExecution()
            .getExecutionContext();

        System.out.println("jobName : " + jobExecutionContext.get("jobName"));
        System.out.println("stepName : " + stepExecutionContext.get("stepName")); //stepExecution은 Step간 공유가 안되기때문에 조회 안될 것

        String stepName = chunkContext.getStepContext().getStepExecution().getStepName();

        if (stepExecutionContext.get("ExecutionPracJob_step2") == null) {
            stepExecutionContext.put("stepName", stepName);
        }

        return RepeatStatus.FINISHED;
    }
}
