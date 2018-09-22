package net.kleinschmager.dhbw.tfe16.painground.business;

import java.io.File;
import java.util.List;

import net.kleinschmager.dhbw.tfe16.painground.persistence.model.MemberProfile;


public interface CsvImporter {

	List<MemberProfile> importFile(File csvFile);

}
