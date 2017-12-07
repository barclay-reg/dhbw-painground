package net.kleinschmager.dhbw.tfe15.painground.business;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import net.kleinschmager.dhbw.tfe15.painground.persistence.model.MemberProfile;

public class MemberProfileCsvTransformatorTest {

	private MemberProfileCsvTransformator cut;

	@Before
	public void setUp() throws Exception {
		this.cut = new MemberProfileCsvTransformator();
	}

	@Test
	public void simpleOneLineShouldMatchPerfect() {
		// GIVEN
		String[][] input = new String[1][5];

		input[0][0] = "memberId1";
		input[0][1] = "Surname 1";
		input[0][2] = "Givenname 1";
		input[0][3] = "16.01.1976";
		input[0][4] = "skill1, skill2";

		// WHEN
		List<MemberProfile> result = cut.transform(input);
		// THEN
		assertEquals("List should contains one elements", 1, result.size());
		assertThat("memberId should match", result.get(0).getMemberId(), equalTo(input[0][0]));
		assertThat("surname should match", result.get(0).getSurName(), equalTo(input[0][1]));
		assertThat("givenname should match", result.get(0).getGivenName(), equalTo(input[0][2]));
		String formattedDate = new SimpleDateFormat("dd.MM.yyyy").format(result.get(0).getDateOfBirth());
		assertThat("birthdate should match", formattedDate, equalTo(input[0][3]));
		assertThat("skill-list should match", result.get(0).getSkills(), equalTo(input[0][4]));
	}

	@Test
	public void emptyArrayShouldReturnEmptyList() {
		// GIVEN
		String[][] input = new String[0][0];
		// WHEN
		List<MemberProfile> result = cut.transform(input);
		// THEN
		assertTrue("Result should be empty list", result.isEmpty());
	}

	@Test
	public void oneLineMustHaveAtLeastTwoElements() {

		// GIVEN
		String[][] input = new String[3][1];
		// WHEN
		try {
			cut.transform(input);
			fail("This code should never be reached: expecting an exception");
		} catch (Exception e) {
			// THEN
			assertEquals(e.getClass(), ArrayIndexOutOfBoundsException.class);
			assertThat(e.getMessage(), containsString("1"));
		}
	}

	@Test
	public void validArrayWithNoElementsShouldReturnEmptyList() {
		// GIVEN
		String[][] input = new String[3][3];
		// WHEN
		List<MemberProfile> result = cut.transform(input);
		// THEN
		assertTrue("Result should be empty list", result.isEmpty());
	}

	@Test
	public void wrongDateformatShouldLeaveBirthdateEmpty() {

		// GIVEN
		String[][] input = new String[1][5];

		input[0][0] = "memberId1";
		input[0][1] = "Surname 1";
		input[0][2] = "Givenname 1";
		input[0][3] = "16-05-1976";
		input[0][4] = "skill1, skill2";

		// WHEN
		List<MemberProfile> result = cut.transform(input);
		// THEN
		assertEquals("List should contains one elements", 1, result.size());
		assertThat("memberId should match", result.get(0).getMemberId(), equalTo(input[0][0]));
		assertThat("surname should match", result.get(0).getSurName(), equalTo(input[0][1]));
		assertThat("givenname should match", result.get(0).getGivenName(), equalTo(input[0][2]));
		assertNull("birthdate should be null", result.get(0).getDateOfBirth());
		assertThat("skill-list should match", result.get(0).getSkills(), equalTo(input[0][4]));
	}

	@Test
	public void emptyMemberIdShouldSkipEntry() {

		// GIVEN
		String[][] input = new String[3][5];

		input[0][0] = "memberId1";
		input[0][1] = "Surname 1";
		input[0][2] = "Givenname 1";
		input[0][3] = "16.01.1976";
		input[0][4] = "skill1, skill2";

		input[1][0] = "";
		input[1][1] = "Surname 2";
		input[1][2] = "Givenname 2";
		input[1][3] = "16.01.1976";
		input[1][4] = "skill1, skill2";

		input[2][0] = "memberId3";
		input[2][1] = "Surname 3";
		input[2][2] = "Givenname 3";
		input[2][3] = "16.11.1996";
		input[2][4] = "skill4, skill4";

		// WHEN
		List<MemberProfile> result = cut.transform(input);
		// THEN
		assertEquals("List should contains two elements", 2, result.size());
		assertThat("First memberId should match", result.get(0).getMemberId(), equalTo(input[0][0]));
		assertThat("First surname should match", result.get(0).getSurName(), equalTo(input[0][1]));
		assertThat("Third memberId should match", result.get(1).getMemberId(), equalTo(input[2][0]));
		assertThat("Third surname should match", result.get(1).getSurName(), equalTo(input[2][1]));

	}

	@Test
	public void emptySurenameShouldSkipEntry() {

		// GIVEN
		String[][] input = new String[3][5];

		input[0][0] = "memberId1";
		input[0][1] = "Surname 1";
		input[0][2] = "Givenname 1";
		input[0][3] = "16.01.1976";
		input[0][4] = "skill1, skill2";

		input[1][0] = "memberId2";
		input[1][1] = "";
		input[1][2] = "Givenname 2";
		input[1][3] = "16.01.1976";
		input[1][4] = "skill1, skill2";

		input[2][0] = "memberId3";
		input[2][1] = "Surname 3";
		input[2][2] = "Givenname 3";
		input[2][3] = "16.11.1996";
		input[2][4] = "skill4, skill4";

		// WHEN
		List<MemberProfile> result = cut.transform(input);
		// THEN
		assertEquals("List should contains two elements", 2, result.size());
		assertThat("First memberId should match", result.get(0).getMemberId(), equalTo(input[0][0]));
		assertThat("First surname should match", result.get(0).getSurName(), equalTo(input[0][1]));
		assertThat("Third memberId should match", result.get(1).getMemberId(), equalTo(input[2][0]));
		assertThat("Third surname should match", result.get(1).getSurName(), equalTo(input[2][1]));

	}
}
