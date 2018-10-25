package quartzJob;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableScheduling
public class QuartzJob {
    public void sayHello(){
        System.out.println("Hello World");
    }
}
