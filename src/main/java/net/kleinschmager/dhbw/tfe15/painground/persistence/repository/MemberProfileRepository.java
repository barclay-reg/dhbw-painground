/**
 * 
 */
package net.kleinschmager.dhbw.tfe15.painground.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.kleinschmager.dhbw.tfe15.painground.persistence.model.MemberProfile;

/**
 * @author robertkleinschmager
 */
public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {

}
