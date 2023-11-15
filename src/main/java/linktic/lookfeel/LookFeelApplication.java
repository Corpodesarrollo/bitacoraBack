package linktic.lookfeel;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@Slf4j
public class LookFeelApplication implements CommandLineRunner {

	@Value("${info.app.name}")
	private String appName;

	@Value("${info.app.version}")
	private String appVersion;

	@Value("${spring.profiles.active}")
	private String profileActive;

	public static void main(String[] args) {
		SpringApplication.run(LookFeelApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("La version de {} es {} - el profile activo es {} ", appName, appVersion,profileActive);
	}
}
