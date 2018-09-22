package net.kleinschmager.dhbw.tfe16.painground.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.kleinschmager.dhbw.tfe16.painground.persistence.model.CompanyProject;

public interface CompanyProjectRepository extends JpaRepository<CompanyProject,Long> {
}
