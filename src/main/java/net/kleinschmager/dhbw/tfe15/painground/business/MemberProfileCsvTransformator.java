/**
 * copyright by Robert Kleinschmager
 */
package net.kleinschmager.dhbw.tfe15.painground.business;

import static org.apache.commons.lang3.StringUtils.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import jersey.repackaged.com.google.common.collect.Lists;
import kr.pe.kwonnam.slf4jlambda.LambdaLogger;
import kr.pe.kwonnam.slf4jlambda.LambdaLoggerFactory;
import net.kleinschmager.dhbw.tfe15.painground.persistence.model.MemberProfile;

/**
 * Transform a csv file to a list of {@link MemberProfile}
 * 
 * @author robertkleinschmager
 *
 */
@Component
public class MemberProfileCsvTransformator {
	
	private static final LambdaLogger log = LambdaLoggerFactory.getLogger(MemberProfileCsvTransformator.class);
	
	public List<MemberProfile> transform(String[][] multilineCsv) {
		List<MemberProfile> result = Lists.newArrayList();
		
		for(int i = 0; i < multilineCsv.length; i++) {
			Optional<MemberProfile> profile = transform(multilineCsv[i]);
			if (profile.isPresent()) {
				
				result.add(profile.get());
				
			} else {
				log.error("Unable to transform to MemberProfile: " + multilineCsv[i] );
			}
		}
		
		return result;
	}
	
	public Optional<MemberProfile> transform(String[] singleLine) {
		
		// expecting memberid, surname, givenname, dateofbirth, skills
		
		String memberId = singleLine[0];
		String surname = singleLine[1];
		
		if (isNotBlank(memberId) && isNotBlank(surname)) {
		
			MemberProfile newProfile = new MemberProfile(memberId, surname);
			
			newProfile.setGivenName(singleLine[2]);
			newProfile.setDateOfBirth(toDate(singleLine[3]));
			newProfile.setSkills(singleLine[4]);
			
			return Optional.of(newProfile);
		} else {
			return Optional.empty();
		}
	}
	
	public Date toDate(String dateAsString) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		
		try {
			return sdf.parse(dateAsString);
		} catch (ParseException e) {
			
			return null;
		}	
	}
}
