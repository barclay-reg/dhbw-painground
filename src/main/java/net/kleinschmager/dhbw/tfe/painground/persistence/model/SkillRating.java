package net.kleinschmager.dhbw.tfe.painground.persistence.model;

import java.time.Instant;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@Table(name = "mp03_skillrating")
public class SkillRating {

	public static final String COLUMN_FK_SKILL = "mp02_id_ratedskill";

	@Id
	// using GenerationType.IDENTITY must along with liquibase AUTOINCREMENT .. no
	// sequence
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@Column(nullable = false, name = "created_date")
	private Date createdDate;

	@Column(nullable = true, name = "creator_name")
	private String creatorName;

	@Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "rating")
	private Rating rating;

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = COLUMN_FK_SKILL)
    private Skill ratedSkill;

	protected SkillRating() {
		// default constructor is protected, as it should only be used by JPA
	}

	public SkillRating(@NotNull Rating rating, @NotNull Date createdDate) {
		Preconditions.checkNotNull(rating);
		Preconditions.checkNotNull(createdDate);

		this.rating = rating;
		this.createdDate = createdDate;
	}

	public SkillRating(@NotNull Rating rating) {
		this(rating, Date.from(Instant.now()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}

	public Skill getRatedSkill() {
		return ratedSkill;
	}

	public void setRatedSkill(Skill ratedSkill) {
		this.ratedSkill = ratedSkill;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	@Override
	public boolean equals(Object obj) {

		// using the "business key" strategy of the ID dilemma
		// see
		// http://www.onjava.com/pub/a/onjava/2006/09/13/dont-let-hibernate-steal-your-identity.html

		if (this == obj) {
			return true;
		}
		if (!(obj instanceof SkillRating)) {
			return false;
		}
		SkillRating that = (SkillRating) obj;
		EqualsBuilder eb = new EqualsBuilder();
		eb.append(this.getRating(), that.getRating());
		eb.append(this.getRatedSkill(), that.getRatedSkill());

		return eb.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder();
		hcb.append(this.getRating());
		hcb.append(this.getRatedSkill());
		return hcb.toHashCode();
	}


	@Override
	public String toString() {
		return ReflectionToStringBuilder.toStringExclude(this, "ratedSkill");
	}

}
