/**
 * 
 */
package net.kleinschmager.dhbw.tfe.painground.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.kleinschmager.dhbw.tfe.painground.persistence.model.Skill;

/**
 * @author robkle01
 *
 */
public interface SkillRepository extends JpaRepository<Skill, Long>  {

}
