package com.example.springbatch.excutionContextPrac.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class ExecutionTasklet3 implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("step3  실행됨");

        /**
         * 잡이 실패한 지점을 잡고, 해당 지점부터 수행할 수 있을지 테스트
         */

        Object name = chunkContext.getStepContext().getStepExecution().getJobExecution()
            .getExecutionContext()
            .get("name");

        if (name == null) {
            chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext()
                .put("name", "user1");
            throw new RuntimeException("step3 was failed");
        }
        return RepeatStatus.FINISHED;
    }
}
