package net.kleinschmager.dhbw.tfe.painground.business;

import java.io.File;
import java.util.List;

import net.kleinschmager.dhbw.tfe.painground.persistence.model.MemberProfile;


public interface CsvImporter {

	List<MemberProfile> importFile(File csvFile);

}
