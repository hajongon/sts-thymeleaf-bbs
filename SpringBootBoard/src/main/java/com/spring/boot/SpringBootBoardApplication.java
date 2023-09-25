package com.spring.boot;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SpringBootBoardApplication {

	public static void main(String[] args) { // main
		SpringApplication.run(SpringBootBoardApplication.class, args);
	}

	@Bean // 이 메서드를 객체 생성해줘야 한다.
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {

		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource); // 의존성 주입

		Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/*.xml"); // *를써서
																													// 여러개
																													// xml을
																													// 받아서
																													// 배열씀

		sessionFactory.setMapperLocations(res);

		return sessionFactory.getObject();
		// .xml 형태의 모든 파일을 받기때문에 배열로 받는다.
		// 만약 한가지만 받으면 배열로[] 받을 필요가 없다.
	}
}