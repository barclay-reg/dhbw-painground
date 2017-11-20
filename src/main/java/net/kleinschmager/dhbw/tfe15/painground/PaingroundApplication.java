/**
 * copyright by Robert Kleinschmager
 */
package net.kleinschmager.dhbw.tfe15.painground;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import kr.pe.kwonnam.slf4jlambda.LambdaLogger;
import kr.pe.kwonnam.slf4jlambda.LambdaLoggerFactory;
import net.kleinschmager.dhbw.tfe15.painground.persistence.model.MemberProfile;
import net.kleinschmager.dhbw.tfe15.painground.persistence.repository.MemberProfileRepository;

@SpringBootApplication
public class PaingroundApplication {
	
	private static final LambdaLogger log = LambdaLoggerFactory.getLogger(PaingroundApplication.class);

	/**
	 * the main method
	 * 
	 * @param args
	 */
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
		return args -> {
			
			deleteAllExistingProfiles(repository);
			saveSomeProfiles(repository);
			fetchAndPrintAllProfiles(repository);
		};
	}

	private void deleteAllExistingProfiles(MemberProfileRepository repository) {
		repository.deleteAll();
		repository.flush();
	}

	private void fetchAndPrintAllProfiles(MemberProfileRepository repository) {
		log.info(() -> "MemberProfiles found with findAll():");
		log.info(() -> "-------------------------------");
		for (MemberProfile profile : repository.findAll()) {
			log.info(() -> "Profile: " + profile.toString());
		}
		log.info(() -> "");
	}

	private void saveSomeProfiles(MemberProfileRepository repository) {
		// save a couple of profiles
		repository.save(new MemberProfile("robkle", "Kleinschmager"));
		repository.save(new MemberProfile("mickni", "Knight"));
		repository.save(new MemberProfile("geolaf", "Laforge"));
		repository.flush();
	}
}
