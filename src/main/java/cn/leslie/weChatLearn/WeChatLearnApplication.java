package cn.leslie.weChatLearn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("cn.leslie.weChatLearn.mapper")
@EnableTransactionManagement
public class WeChatLearnApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeChatLearnApplication.class, args);
	}

}
