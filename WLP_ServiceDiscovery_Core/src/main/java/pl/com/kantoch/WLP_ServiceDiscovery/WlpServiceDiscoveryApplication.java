package pl.com.kantoch.WLP_ServiceDiscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WlpServiceDiscoveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(WlpServiceDiscoveryApplication.class, args);
	}
}
