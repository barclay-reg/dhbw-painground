/**
 * copyright by Robert Kleinschmager
 */
package net.kleinschmager.dhbw.tfe15.painground.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.pe.kwonnam.slf4jlambda.LambdaLogger;
import kr.pe.kwonnam.slf4jlambda.LambdaLoggerFactory;
import net.kleinschmager.dhbw.tfe15.painground.persistence.model.MemberProfile;

/**
 * Reads a file and converts this to a list of {@link MemberProfile}s
 * @author robertkleinschmager
 *
 */
@Component
public class MemberProfileCsvImporter {

	private static final LambdaLogger log = LambdaLoggerFactory.getLogger(MemberProfileCsvImporter.class);
	
	@Autowired
	MemberProfileCsvTransformator csvTransformator;

	public List<MemberProfile> importFile(File csvFile) {
		
		try (FileInputStream fis = new FileInputStream(csvFile)) {
			
			List<String> content = IOUtils.readLines(fis, Charset.defaultCharset());
			
			int numberOfColumns = getLengthOfFirstRow(content);
			int numberOfRows = content.size();
			
			String[][] result = new String[numberOfRows-1][numberOfColumns];
			
			for (int i = 1; i < numberOfRows; i++) {
				
				result[i-1] = content.get(i).split(";");
			}
			
			return csvTransformator.transform(result);
			
			
		} catch (FileNotFoundException e) {

			log.error(() -> "Could not find given file", e);
			
			return Collections.emptyList();
			
		} catch (IOException e) {
			
			log.error(() -> "Failed to read content of given file", e);
			return Collections.emptyList();
		}
	}

	private int getLengthOfFirstRow(List<String> content) {
		
		String firstRow = content.get(0);
		
		StringTokenizer st = new StringTokenizer(firstRow, ";");
		return st.countTokens();
	}
	
	public void setCsvTransformator(MemberProfileCsvTransformator csvTransformator) {
		this.csvTransformator = csvTransformator;
	}
	
}
