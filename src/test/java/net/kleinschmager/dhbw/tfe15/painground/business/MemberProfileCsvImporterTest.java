package net.kleinschmager.dhbw.tfe15.painground.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.assertj.core.util.Files;
import org.junit.Before;
import org.junit.Test;

import net.kleinschmager.dhbw.tfe15.painground.persistence.model.MemberProfile;

/**
 * Test Classes {@link MemberProfileCsvImporter} and {@link MemberProfileCsvTransformator} together
 * @author robertkleinschmager
 *
 */
public class MemberProfileCsvImporterTest {

	private MemberProfileCsvImporter sut;

	@Before
	public void setUp() throws Exception {
		this.sut = new MemberProfileCsvImporter();		
		this.sut.setCsvTransformator(new MemberProfileCsvTransformator());
		
	}

	@Test
	public void testImportFile() throws IOException {
		//GIVEN
		String content = 
				"MemberId;Surname;Givenname;DateOfBirth;Skills" + "\n" +
		        "id1;Kleinschmager;Robert;06.12.1980;java, html, scrum, jenkins, eclipse, oracleDb, complex event processing" + "\n" +
				"mickni;Knight;Michael;09.01.1949;driving, punch, investigate, charming, bleached";		
				
		File testFile = getAsFile(content);
		
		//WHEN
		List<MemberProfile> result = sut.importFile(testFile);
		
		//THEN
		assertEquals("Two profiles should be returned", 2, result.size());
		
	}
	
	@Test
	public void firstLineShouldBeIgnored() throws IOException {
		//GIVEN
		String content = 
				"id1;Kleinschmager;Robert;06.12.1980;java, html, scrum, jenkins, eclipse, oracleDb, complex event processing" + "\n" +
				"mickni;Knight;Michael;09.01.1949;driving, punch, investigate, charming, bleached";		
				
		File testFile = getAsFile(content);
		
		//WHEN
		List<MemberProfile> result = sut.importFile(testFile);
		
		//THEN
		assertEquals("Just one profiles should be returned", 1, result.size());
		assertEquals("mickni", result.get(0).getMemberId());
	}
	
	@Test
	public void wrongSeparatorShouldFail() throws IOException {
		//GIVEN
		String content = 
				"id1,Kleinschmager,Robert,06.12.1980,java, html, scrum, jenkins, eclipse, oracleDb, complex event processing" + "\n" +
				"mickni,Knight,Michael,09.01.1949,driving, punch, investigate, charming, bleached";		
				
		File testFile = getAsFile(content);
		
		//WHEN
		try {
			sut.importFile(testFile);
			fail("This code should never be reached: expecting an exception");
		} catch (Exception e) {
			
			//THEN
			assertTrue(e instanceof ArrayIndexOutOfBoundsException);
			assertEquals("Exception Message should match", "1", e.getMessage());			
		}
	}
	
	private File getAsFile(String content) throws IOException {
		
		File temporaryFile = Files.newTemporaryFile();
		FileUtils.writeStringToFile(temporaryFile, content, Charset.forName("UTF-8"));
		return temporaryFile;
	}

}
