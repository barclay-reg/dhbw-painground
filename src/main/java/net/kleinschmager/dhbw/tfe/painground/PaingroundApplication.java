package net.kleinschmager.dhbw.tfe.painground;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import kr.pe.kwonnam.slf4jlambda.LambdaLogger;
import kr.pe.kwonnam.slf4jlambda.LambdaLoggerFactory;
import net.kleinschmager.dhbw.tfe.painground.business.CsvImporter;
import net.kleinschmager.dhbw.tfe.painground.persistence.model.CompanyProject;
import net.kleinschmager.dhbw.tfe.painground.persistence.model.Level;
import net.kleinschmager.dhbw.tfe.painground.persistence.model.MemberProfile;
import net.kleinschmager.dhbw.tfe.painground.persistence.model.ProjectMembership;
import net.kleinschmager.dhbw.tfe.painground.persistence.model.Rating;
import net.kleinschmager.dhbw.tfe.painground.persistence.model.Skill;
import net.kleinschmager.dhbw.tfe.painground.persistence.model.SkillRating;
import net.kleinschmager.dhbw.tfe.painground.persistence.repository.CompanyProjectRepository;
import net.kleinschmager.dhbw.tfe.painground.persistence.repository.MemberProfileRepository;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class PaingroundApplication extends SpringBootServletInitializer {

   private static final LambdaLogger log = LambdaLoggerFactory.getLogger(PaingroundApplication.class);

   @Autowired
   CsvImporter csvImporter;

   @Autowired
   private CompanyProjectRepository companyProjectRepository;

   public static void main(String[] args) {
      SpringApplication.run(PaingroundApplication.class, args);
   }

   @Override
   protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
      return builder.sources(PaingroundApplication.class);
   }

   /**
    * Using the CommandLineRunner feature of spring-boot
    * <p>
    * The annotation @Bean ensures, that my {@link CommandLineRunner} is in the
    * spring context, spring-boot ensures, that this runner is executed on startup
    */
   @Bean
   public CommandLineRunner loadData(MemberProfileRepository repository) {
      return args -> {

         deleteAllExistingProfiles(repository);
         importProfiles(repository);
         addMoreProfiles(repository);
         fetchAndPrintAllProfiles(repository);
      };
   }
   
   //@Bean(name = "multipartResolver")
   //public CommonsMultipartResolver multipartResolver() {
   //    return new CommonsMultipartResolver();
   //}

   private void addMoreProfiles(MemberProfileRepository repository) {
      CompanyProject webProjects = new CompanyProject("web_client");
      CompanyProject frontendProject = new CompanyProject("frontend");
      CompanyProject backendProject = new CompanyProject("backend");

      companyProjectRepository.save(webProjects);
      companyProjectRepository.save(frontendProject);
      companyProjectRepository.save(backendProject);

      MemberProfile mp = new MemberProfile("erigam01", "Gamma");
      mp.setGivenName("Eric");
      Skill skill = new Skill("ood");
      skill.setLevel(Level.EXPERT);
      skill.addRating(new SkillRating(Rating.ACKNOWLEDGE));
      mp.addSkill(skill);
      skill = new Skill("java");
      skill.setLevel(Level.EXPERT);
      mp.addSkill(skill);

      mp.addFinishedProjectMembership(webProjects, LocalDate.of(2010, 3, 20), LocalDate.of(2015, 12, 3));
      mp.addFinishedProjectMembership(frontendProject, LocalDate.of(2014, 1, 12), LocalDate.of(2017, 8, 30));
      mp.addRunningProjectMembership(backendProject, LocalDate.of(2017, 12, 4));

      mp.setPicture(loadImageFromClasspath("profile-icon.png"));

      repository.save(mp);

      repository.flush();

   }

   private byte[] loadImageFromClasspath(String imagePath) {
      try {
         InputStream inputImageStream = PaingroundApplication.class.getClassLoader().getResourceAsStream(imagePath);

         byte[] image = IOUtils.toByteArray(inputImageStream);
         log.info(() -> "Imported Image as " + (image.length / 1000.0) + " KB");
         return image;
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   private Date toDate(LocalDate localDate) {
      Instant asInstant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
      return Date.from(asInstant);
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

   private void importProfiles(MemberProfileRepository repository) {

      URL inputFileUrl = PaingroundApplication.class.getClassLoader().getResource("db/initial_data.csv");

      File inputFile = new File(inputFileUrl.getFile());

      log.info(() -> "Reading file: " + inputFile.getAbsolutePath());

      List<MemberProfile> profiles = csvImporter.importFile(inputFile);

      for (MemberProfile memberProfile : profiles) {
         repository.save(memberProfile);
      }

      log.info(() -> "Imported " + profiles.size() + " profiles");

      repository.flush();
   }
}
