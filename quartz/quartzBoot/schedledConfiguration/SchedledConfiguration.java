package com.sun.mail.quartz.schedledConfiguration;

import com.sun.mail.quartz.job.TestQuartzJob;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;



@Configuration
public class SchedledConfiguration {


    //定义工作细节
    @Bean(name = "detailFactoryBean")
    public MethodInvokingJobDetailFactoryBean detailFactoryBean(TestQuartzJob quartzJob){
        MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean ();
        bean.setTargetObject (quartzJob);
        //定义要调动的方法
        bean.setTargetMethod ("sayHello");
        //不支持并发
        bean.setConcurrent (false);
        return bean;
    }


    //定义触发器
    @Bean(name = "cronTriggerBean")
    public CronTriggerFactoryBean cronTriggerBean(MethodInvokingJobDetailFactoryBean detailFactoryBean) {
        CronTriggerFactoryBean messageSendTrigger = new CronTriggerFactoryBean();
        //触发对象
        JobDetail a = detailFactoryBean.getObject();
        messageSendTrigger.setJobDetail(a);
        messageSendTrigger.setCronExpression("0/5 * * * * ? ");//每5秒执行一次
        return messageSendTrigger;
    }

    //定义调度器
    @Bean
        public SchedulerFactoryBean schedulerFactory(CronTrigger cronTriggerBean){

            SchedulerFactoryBean bean = new SchedulerFactoryBean();

            bean.setTriggers (cronTriggerBean);
            // 设置延迟启动
            bean.setStartupDelay(5);
            //自动启动
            bean.setAutoStartup(true);

            return bean;
        }
}
