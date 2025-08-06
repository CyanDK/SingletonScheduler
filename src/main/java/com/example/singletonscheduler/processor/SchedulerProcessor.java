package com.example.singletonscheduler.processor;

import com.example.singletonscheduler.lock.RedisLockService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class SchedulerProcessor implements Processor {
    private static final Log LOG = LogFactory.getLog(SchedulerProcessor.class);
    @Autowired
    RedisLockService redisLockService;

    @Override
    public void process(Exchange exchange) throws Exception {
        String lockKey = "singleton-job-lock";
        boolean locked = redisLockService.tryLock(lockKey, 0, 30, TimeUnit.SECONDS);
        try {
            if(locked) {
                executeBusinessLogic();
            } else {
                LOG.error("Skip.");
            }
        } catch (Exception e) {
            LOG.error(e);
        } finally {
            if (locked) {
                redisLockService.unlock(lockKey);
            }
        }
    }

    private void executeBusinessLogic() throws Exception {
        Thread.sleep(30000);
    }
}
