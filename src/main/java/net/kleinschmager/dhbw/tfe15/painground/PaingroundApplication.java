package net.kleinschmager.dhbw.tfe15.painground;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import net.kleinschmager.dhbw.tfe15.painground.persistence.model.MemberProfile;
import net.kleinschmager.dhbw.tfe15.painground.persistence.repository.MemberProfileRepository;

@SpringBootApplication

public class PaingroundApplication {
	
	private static final Logger log = LoggerFactory.getLogger(PaingroundApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(PaingroundApplication.class, args);
	}
	
	/**
	 * Using the CommandLineRunner feature of spring-boot
	 * 
	 * The annotation @Bean ensures, that my {@link CommandLineRunner} is in the spring context, 
	 * spring-boot ensures, that this runner is executed 
	 *  
	 */
	@Bean
	public CommandLineRunner loadData(MemberProfileRepository repository) {
		return (args) -> {
			// save a couple of profiles
			repository.save(new MemberProfile("robkle", "Kleinschmager"));
			repository.save(new MemberProfile("mickni", "Knight"));
			repository.save(new MemberProfile("geolaf", "Laforge"));
			
			// fetch all profiles
			log.info("MemberProfiles found with findAll():");
			log.info("-------------------------------");
			for (MemberProfile profile : repository.findAll()) {
				log.info(profile.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			MemberProfile profile = repository.findOne(1L);
			log.info("Profile found with findOne(1L):");
			log.info("--------------------------------");
			log.info(profile.toString());
			log.info("");

		};
	}
}
