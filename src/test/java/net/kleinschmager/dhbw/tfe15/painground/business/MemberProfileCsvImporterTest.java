package net.kleinschmager.dhbw.tfe15.painground.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.assertj.core.util.Files;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import net.kleinschmager.dhbw.tfe15.painground.persistence.model.MemberProfile;

/**
 * Test Classes {@link MemberProfileCsvImporter} and
 * {@link MemberProfileCsvTransformator} together
 * 
 * @author robertkleinschmager
 *
 */
public class MemberProfileCsvImporterTest {

	private CsvImporter sut;

	@Before
	public void setUp() throws Exception {
		this.sut = new AlternativeMemberProfileCsvImporter();
		//this.sut = new MemberProfileCsvImporter();
		//this.sut.setCsvTransformator(new MemberProfileCsvTransformator());

	}

	@Test
	public void testImportFile() throws IOException {
		// GIVEN
		String content = "MemberId;Surname;Givenname;DateOfBirth;Skills" + "\n"
				+ "id1;Kleinschmager;Robert;06.12.1980;java, html, scrum, jenkins, eclipse, oracleDb, complex event processing"
				+ "\n" + "mickni;Knight;Michael;09.01.1949;driving, punch, investigate, charming, bleached";

		File testFile = getAsFile(content);

		// WHEN
		List<MemberProfile> result = sut.importFile(testFile);

		// THEN
		assertEquals("Two profiles should be returned", 2, result.size());

	}

	@Test
	public void firstLineShouldBeIgnored() throws IOException {
		// GIVEN
		String content = "id1;Kleinschmager;Robert;06.12.1980;java, html, scrum, jenkins, eclipse, oracleDb, complex event processing"
				+ "\n" + "mickni;Knight;Michael;09.01.1949;driving, punch, investigate, charming, bleached";

		File testFile = getAsFile(content);

		// WHEN
		List<MemberProfile> result = sut.importFile(testFile);

		// THEN
		assertEquals("Just one profiles should be returned", 1, result.size());
		assertEquals("mickni", result.get(0).getMemberId());
	}

	@Test
	public void wrongSeparatorShouldFail() throws IOException {
		// GIVEN
		String content = "id1,Kleinschmager,Robert,06.12.1980,java, html, scrum, jenkins, eclipse, oracleDb, complex event processing"
				+ "\n" + "mickni,Knight,Michael,09.01.1949,driving, punch, investigate, charming, bleached";

		File testFile = getAsFile(content);

		// WHEN
		try {
			sut.importFile(testFile);
			fail("This code should never be reached: expecting an exception");
		} catch (Exception e) {

			// THEN
			assertTrue(e instanceof ArrayIndexOutOfBoundsException);
			assertEquals("Exception Message should match", "1", e.getMessage());
		}
	}

	@Test
	public void missingFileShoulsFail() {

		// GIVEN
		File nonExistingFile = new File("dummy.txt");

		// WHEN
		List<MemberProfile> result = sut.importFile(nonExistingFile);

		// THEN

		assertTrue("Result must be empty", result.isEmpty());
	}

	
	@Test
	public void wrongDateformatShouldLeaveBirthdateEmpty() throws IOException {
		// GIVEN
		String content = "MemberId;Surname;Givenname;DateOfBirth;Skills" + "\n"
				+ ";Kleinschmager;Robert;06-12-1980;java, html, scrum, jenkins, eclipse, oracleDb, complex event processing" + "\n" + 
				"mickni;Knight;Michael;09-01-1949;driving, punch, investigate, charming, bleached";

		File testFile = getAsFile(content);

		// WHEN
		List<MemberProfile> result = sut.importFile(testFile);

		// THEN
		assertNull("One profile should be returned", result.get(0).getDateOfBirth());
	}

	@Test
	public void emptyMemberIdShouldSkipEntry() throws IOException {
		// GIVEN
		String content = "MemberId;Surname;Givenname;DateOfBirth;Skills" + "\n"
				+ ";Kleinschmager;Robert;06.12.1980;java, html, scrum, jenkins, eclipse, oracleDb, complex event processing" + "\n" + 
				"mickni;Knight;Michael;09.01.1949;driving, punch, investigate, charming, bleached";

		File testFile = getAsFile(content);

		// WHEN
		List<MemberProfile> result = sut.importFile(testFile);

		// THEN
		assertEquals("One profile should be returned", 1, result.size());

	}

	@Test
	public void oneLineMustHaveAtLeastTwoElements() throws IOException {

		// GIVEN
		String content = "MemberId;Surname;Givenname;DateOfBirth;Skills" + "\n" + "" + "\n" + "";

		File testFile = getAsFile(content);

		// WHEN
		try {
			sut.importFile(testFile);
			fail("This code should never be reached: expecting an exception");
		} catch (Exception e) {

			// THEN
			assertTrue(e instanceof ArrayIndexOutOfBoundsException);
			assertEquals("Exception Message should match", "1", e.getMessage());
		}

	}

	@Test
	@Ignore
	public void lockedFileShouldFail() throws IOException {

		// GIVEN
		File file = getAsFile("somecontent");
		final RandomAccessFile fileLock = new RandomAccessFile(file, "rw");
		fileLock.getChannel().lock();

		// WHEN
		List<MemberProfile> result = sut.importFile(file);

		// THEN

		assertTrue("Result must be empty", result.isEmpty());

	}

	private File getAsFile(String content) throws IOException {

		File temporaryFile = Files.newTemporaryFile();
		FileUtils.writeStringToFile(temporaryFile, content, Charset.forName("UTF-8"));
		return temporaryFile;
	}

}
