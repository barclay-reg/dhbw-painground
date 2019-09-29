/**
 * 
 */
package net.kleinschmager.dhbw.tfe.painground.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.kleinschmager.dhbw.tfe.painground.persistence.model.MemberProfile;


/**
 * @author robertkleinschmager
 */
public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {

}
