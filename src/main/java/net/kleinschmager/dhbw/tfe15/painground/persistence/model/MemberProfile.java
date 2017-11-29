/**
 * 
 */
package net.kleinschmager.dhbw.tfe15.painground.persistence.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.google.common.base.Preconditions;

/**
 * @author robertkleinschmager
 */
@Entity
@Table(name = "mp01_memberprofile")
public class MemberProfile {

	protected MemberProfile() {
		// default constructor is protected, as it should onlz be used by JPA
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

	@Column(nullable = true, name = "skills")
	private String skills;

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

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
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

}
