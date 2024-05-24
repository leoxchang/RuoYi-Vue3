package com.truntao.quartz.util;

import org.quartz.JobExecutionContext;
import com.truntao.quartz.domain.po.SysJob;

/**
 * 定时任务处理（允许并发执行）
 *
 * @author truntao
 */
public class QuartzJobExecution extends AbstractQuartzJob {
    @Override
    protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception {
        JobInvokeUtil.invokeMethod(sysJob);
    }
}
