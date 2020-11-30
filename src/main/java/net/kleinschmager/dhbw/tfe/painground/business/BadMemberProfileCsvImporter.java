/**
 * copyright by Robert Kleinschmager
 */
package net.kleinschmager.dhbw.tfe.painground.business;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import kr.pe.kwonnam.slf4jlambda.LambdaLogger;
import kr.pe.kwonnam.slf4jlambda.LambdaLoggerFactory;
import net.kleinschmager.dhbw.tfe.painground.persistence.model.Level;
import net.kleinschmager.dhbw.tfe.painground.persistence.model.MemberProfile;
import net.kleinschmager.dhbw.tfe.painground.persistence.model.Skill;

/**
 * Reads a file and converts this to a list of {@link MemberProfile}s
 * @author robertkleinschmager
 *
 */
@Component
public class BadMemberProfileCsvImporter implements CsvImporter {

	private static final LambdaLogger log = LambdaLoggerFactory.getLogger(BadMemberProfileCsvImporter.class);

	//Would wish to rename importFile, bad naming
	@Override
	public List<MemberProfile> importFile(File fileOfMembers) {

      try {
         List<String> rawList = readFile(fileOfMembers);
         return createProfilesAndSaveToList(rawList);
      }

      catch (ParseException e) {
         log.error(() -> "Failed to read content of given file", e);
         return Collections.emptyList();
      }

   }

   public List<String> readFile(File fileOfProfiles) {

      try (FileInputStream fileInputStream = new FileInputStream(fileOfProfiles)) {

         List<String> rawMemberProfileList = IOUtils.readLines(fileInputStream, Charset.defaultCharset());

         return rawMemberProfileList;
      }
      catch (FileNotFoundException e) {

         log.error(() -> "Could not find given file", e);

         return Collections.emptyList();
      }
      catch (IOException e) {

         log.error(() -> "Failed to read content of given file", e);
         return Collections.emptyList();
      }
   }

   private List<MemberProfile> createProfilesAndSaveToList(List<String> rawProfileList) throws ParseException {
      List<MemberProfile> profileList = Lists.newArrayList();

      String[][] profiles = splitIDsFromRestAndConvertToArray(rawProfileList);

      //array.length returns length of first dimension
      int numberOfProfiles = profiles.length;

      for (int profile = 0; profile < numberOfProfiles; profile++) {
         String[] rawProfile = profiles[profile];
         profileList.add(extractProfile(rawProfile));
      }

      return profileList;
   }

   private String[][] splitIDsFromRestAndConvertToArray(List<String> rawProfileList) {
	   int numberOfHeaderLines = 1;
      int numberOfMemberProfiles = rawProfileList.size() - numberOfHeaderLines;
      int numberOfPropertiesPerMember = StringUtils.countMatches(rawProfileList.get(0), ';');

      String[][] arrayOfProfiles = new String[numberOfMemberProfiles][numberOfPropertiesPerMember];

      for (int profile = 0; profile < numberOfMemberProfiles; profile++) {

         //splits one String in Format "ID;Surname;SKills;..." in an array [(ID),(Surname), (Skills),...]
         arrayOfProfiles[profile] = rawProfileList.get(profile + numberOfHeaderLines).split(";");
      }
      return arrayOfProfiles;
   }

   private MemberProfile extractProfile(String[] rawProfile) throws ParseException {

	   try {
         String memberID = rawProfile[0];
         String surName = rawProfile[1];
         if (isNotBlank(memberID) && isNotBlank(surName)) {

            MemberProfile newProfile = new MemberProfile(memberID, surName);

            String givenName = rawProfile[2];
            newProfile.setGivenName(givenName);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date dateOfBirth = simpleDateFormat.parse(rawProfile[3]);
            newProfile.setDateOfBirth(dateOfBirth);

            List<Skill> skills = extractSkills(rawProfile);

            skills.forEach(skill ->
               newProfile.addSkill(skill));

            return newProfile;
         }
      }
	   catch (ParseException e) {
         log.error(() -> "Failed to extract profile information of given raw data", e);
         return null;
      }
      return null;
   }

   private List<Skill> extractSkills(String[] rawProfile) {
         String[] rawSkills = rawProfile[4].split(",");

         List<Skill> skills = new ArrayList<>();
         int numberOfSkills = rawSkills.length;

         for (int skill = 0; skill < numberOfSkills; skill++)
         {
            Skill newSkill = new Skill();

            String skillName = rawSkills[skill];
            newSkill.setName(skillName);
            //default value?
            newSkill.setLevel(Level.NOVICE);

            Date creationDate = new Date();
            newSkill.setCreatedDate(creationDate);
            skills.add(newSkill);
         }

         return skills;
      }

}
