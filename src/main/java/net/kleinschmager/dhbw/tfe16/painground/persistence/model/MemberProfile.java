/**
 *
 */
package net.kleinschmager.dhbw.tfe16.painground.persistence.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

/**
 * @author robertkleinschmager
 */
@Entity
@Table(name = "mp01_memberprofile")
public class MemberProfile {

	public MemberProfile() {
	}

	/**
	 * Constructor to use within the rest of all code
	 *
	 * @param memberId
	 *            the unique id (businesskey) for this member
	 * @param surName
	 *            the surename (family name) of this member
	 */
	public MemberProfile(@NotNull String memberId, @NotNull String surName) {
		Preconditions.checkNotNull(memberId);
		Preconditions.checkNotNull(surName);

		this.memberId = memberId;
		this.surName = surName;
	}

	@Id
	// using GenerationType.IDENTITY must along with liquibase AUTOINCREMENT .. no
	// sequence
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false, name = "memberid", length = 50)
	private String memberId;

	@Column(nullable = true, name = "givenname")
	private String givenName;

	@Column(nullable = false, name = "surname")
	private String surName;

	@Column(nullable = true, name = "dateofbirth")
	private Date dateOfBirth;

	@Column(nullable = true, name = "image")
	private byte[] picture;

	@OneToMany (
			mappedBy = "skilledProfile",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.EAGER )
	private List<Skill> skills = new ArrayList<>();

	@OneToMany(
			mappedBy = "projectProfile",
			cascade = CascadeType.ALL,
			orphanRemoval = true,
			fetch = FetchType.EAGER )
	private Set<ProjectMembership> projectMemberships = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	public void addSkill(Skill skill) {
		this.skills.add(skill);
		skill.setSkilledProfile(this);
	}

	public void removeSkill(Skill skill) {
		this.skills.remove(skill);
		skill.setSkilledProfile(null);
	}

	public List<Skill> getSkills() {
		return ImmutableList.copyOf(this.skills);
	}

	public List<Skill> getTopThreeSkills() {
		this.skills.sort(Collections.reverseOrder());
		return ImmutableList.copyOf(this.skills.stream().limit(3).collect(Collectors.toList()));
	}

	public void addProjectMembership(ProjectMembership projectMembership) {
		this.projectMemberships.add(projectMembership);
		projectMembership.setProjectMember(this);
	}

	public void addFinishedProjectMembership(CompanyProject project, LocalDate startDate, LocalDate endDate) {
		projectMemberships.add(new ProjectMembership(project, toDate(startDate), toDate(endDate), this));
	}

	public void addRunningProjectMembership(CompanyProject project, LocalDate startDate) {
		projectMemberships.add(new ProjectMembership(project, toDate(startDate), this));
	}

	public void removeProject(ProjectMembership projectMembership) {
		this.projectMemberships.remove(projectMembership);
		projectMembership.setProjectMember(null);

	}

	public List<String> getProjectName() {
		return this.projectMemberships.stream().map(p -> p.getCompanyProject().getName()).collect(Collectors.toList());
	}

	public List<ProjectMembership> getProjectMemberships() {
		return ImmutableList.copyOf(this.projectMemberships);
	}

	public List<ProjectMembership> getMembershipTopThreeTimeOrder() {
		List<ProjectMembership> reverseSortedMembership = new ArrayList<>(this.projectMemberships);
		reverseSortedMembership.sort(Comparator.reverseOrder());
		return ImmutableList.copyOf(reverseSortedMembership);
	}

	@Override
	public boolean equals(Object obj) {

		// using the "business key" strategy of the ID dilemma
		// see
		// http://www.onjava.com/pub/a/onjava/2006/09/13/dont-let-hibernate-steal-your-identity.html

		if (this == obj) {
			return true;
		}
		if (!(obj instanceof MemberProfile)) {
			return false;
		}
		MemberProfile that = (MemberProfile) obj;
		EqualsBuilder eb = new EqualsBuilder();
		eb.append(this.getMemberId(), that.getMemberId());

		return eb.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder();
		hcb.append(this.memberId);
		return hcb.toHashCode();
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toStringExclude(this, "picture");
	}
	
   private Date toDate(LocalDate localDate) {
      Instant asInstant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
      return Date.from(asInstant);
   }
}
