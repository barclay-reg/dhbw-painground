/**
 * 
 */
package net.kleinschmager.dhbw.tfe15.painground.business;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.google.common.collect.Lists;

import net.kleinschmager.dhbw.tfe15.painground.persistence.model.MemberProfile;

/**
 * @author robertkleinschmager
 *
 */
public class AlternativeMemberProfileCsvImporter implements CsvImporter {

	@Override
	public List<MemberProfile> importFile(File csvFile) {
		

		Iterable<CSVRecord> records;
		List<MemberProfile> result = Lists.newArrayList();
		
		try {
			
			FileReader fr = new FileReader(csvFile);
			records = CSVFormat.EXCEL
					.withHeader("MemberId", "Surname", "Givenname", "DateOfBirth", "Skills")
					.withDelimiter(';')
					.withIgnoreEmptyLines(false)
					.withFirstRecordAsHeader()
					.parse(fr);
			
			
			Collections.emptyList();
		
			for (CSVRecord record : records) {
				String memberId = record.get("MemberId");
				String surname = record.get("Surname");
				String givenName = record.get("Givenname");
				String skills = record.get("Skills");
				String dateString = record.get("DateOfBirth");
				
				MemberProfile newProfile = new MemberProfile(memberId, surname);
				newProfile.setGivenName(givenName);
				newProfile.setSkills(skills);
				newProfile.setDateOfBirth(new SimpleDateFormat("dd.MM.yyyy").parse(dateString));
				
				result.add(newProfile);
			}
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

}
