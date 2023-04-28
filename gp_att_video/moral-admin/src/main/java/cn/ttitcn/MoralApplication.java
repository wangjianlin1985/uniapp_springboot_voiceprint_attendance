package cn.ttitcn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling 
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class MoralApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoralApplication.class, args);
		System.out.println("(♥◠‿◠)ﾉﾞ  考勤系统启动成功   ლ(´ڡ`ლ)ﾞ  ");
	}
	
}
