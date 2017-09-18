/**
 * 
 */
package net.kleinschmager.dhbw.tfe15.painground.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import net.kleinschmager.dhbw.tfe15.painground.persistence.model.MemberProfile;

/**
 * @author robertkleinschmager
 */
public interface MemberProfileRepository extends CrudRepository<MemberProfile, Long>{

}
