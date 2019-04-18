package pp.pokemon.pm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
// service, component, bean扫描
@ComponentScan(basePackages={"pp.pokemon"})
@MapperScan(basePackages={"pp.pokemon.pm.dao"})
// 允许事务
@EnableScheduling
public class PmApplication {

	public static void main(String[] args) {
		SpringApplication.run(PmApplication.class, args);
	}

}
