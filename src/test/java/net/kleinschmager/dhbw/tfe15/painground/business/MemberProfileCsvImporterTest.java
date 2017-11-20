package net.kleinschmager.dhbw.tfe15.painground.business;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class MemberProfileCsvImporterTest {

	private MemberProfileCsvImporter cut;
	private MemberProfileCsvTransformator mockedTransformator;

	@Before
	public void setUp() throws Exception {
		this.cut = new MemberProfileCsvImporter();
		
		this.mockedTransformator = mock(MemberProfileCsvTransformator.class);
		
		this.cut.setCsvTransformator(mockedTransformator);
		
	}

	@Test
	@Ignore
	public void testImportFile() {
		fail("Not yet implemented");
	}

}
