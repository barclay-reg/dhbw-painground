package net.kleinschmager.dhbw.tfe15.painground.business;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class MemberProfileCsvTransformatorTest {
	

	private MemberProfileCsvTransformator cut;

	@Before
	public void setUp() throws Exception {
		this.cut = new MemberProfileCsvTransformator();
	}

	@Test
	public void testTransformStringArrayArray() {
		// GIVEN
		// WHEN
		cut.transform(new String[0][0]);
		// THEN
		fail("Not yet implemented");
	}

	@Test
	public void testTransformStringArray() {
		// GIVEN
		// WHEN
		// THEN
		fail("Not yet implemented");
	}

}
