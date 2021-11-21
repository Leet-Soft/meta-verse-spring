package uni.fmi.masters.metaverse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class MetaVerseApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetaVerseApplication.class, args);
	}

}
