package com.FDSC;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//@SpringBootApplication
@MapperScan("com.FDSC.mapper")
@SpringBootApplication(scanBasePackages="****com.FDSC***")
public class FantasyDreamStoryClubApplication {

	public static void main(String[] args) {
		SpringApplication.run(FantasyDreamStoryClubApplication.class, args);


	}

}
