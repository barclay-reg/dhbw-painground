package net.kleinschmager.dhbw.tfe.painground.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import net.kleinschmager.dhbw.tfe.painground.business.CsvImporter;
import net.kleinschmager.dhbw.tfe.painground.persistence.model.MemberProfile;

public class BadMemberProfileCsvImporterTest {

   private CsvImporter sut;

   @Before
   public void setUp() throws Exception {
       //this.sut = new AlternativeMemberProfileCsvImporter();
       this.sut = new BadMemberProfileCsvImporter();

   }

   // happy case
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
       assertEquals("First Element must match", "Kleinschmager", result.get(0).getSurName());
       assertEquals("Second Element must match", "Knight", result.get(1).getSurName());

   }


   private File getAsFile(String content) throws IOException {

       File temporaryFile = File.createTempFile("test", "csv");
       FileUtils.writeStringToFile(temporaryFile, content, Charset.forName("UTF-8"));
       return temporaryFile;
   }

}
