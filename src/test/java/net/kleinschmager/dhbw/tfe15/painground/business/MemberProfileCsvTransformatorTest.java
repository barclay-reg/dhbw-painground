package net.kleinschmager.dhbw.tfe15.painground.business;

import static org.junit.Assert.*;

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
	public void emptyArrayShouldReturnEmptyList() {
		// GIVEN
		String[][] input = new String[0][0];
		// WHEN
		List<MemberProfile> result = cut.transform(input);
		// THEN
		assertTrue("Result should be empty list", result.isEmpty());
	}

	@Test
	@Ignore
	public void testTransformStringArray() {
		// GIVEN
		// WHEN
		// THEN
		fail("Not yet implemented");
	}

}
