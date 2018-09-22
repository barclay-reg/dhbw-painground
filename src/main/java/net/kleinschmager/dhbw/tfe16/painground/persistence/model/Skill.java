package net.kleinschmager.dhbw.tfe16.painground.persistence.model;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "mp02_skill")
public class Skill implements Comparable{

	public static final String COLUMN_FK_MEMBER = "mp01_id_skilledprofile";

	@Id
	// using GenerationType.IDENTITY must along with liquibase AUTOINCREMENT .. no
	// sequence
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, name = "name")
	private String name;

	@Column(nullable = false, name = "created_date")
	private Date createdDate;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, name = "level")
	private Level level;

	@OneToMany (
			mappedBy = "ratedSkill" ,
			cascade = CascadeType.ALL,
	        orphanRemoval = true,
	        fetch = FetchType.EAGER)
	private List<SkillRating> ratings = new ArrayList<>();

	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = COLUMN_FK_MEMBER)
	private MemberProfile skilledProfile;

	public Skill(@NotNull String name, @NotNull Date createdDate) {
		Preconditions.checkNotNull(name);
		Preconditions.checkNotNull(createdDate);

		this.name = name;
		this.createdDate = createdDate;
		this.level = Level.NOVICE;
	}

	public Skill(@NotNull String name) {
		this(name, Date.from(Instant.now()));
	}

	public Skill() {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MemberProfile getSkilledProfile() {
		return skilledProfile;
	}

	public void setSkilledProfile(MemberProfile skilledProfile) {
		this.skilledProfile = skilledProfile;
	}

	public List<SkillRating> getRatings() {
		return ratings;
	}

	public void addRating(SkillRating rating) {
		this.ratings.add(rating);
		rating.setRatedSkill(this);
	}

	public void removeRating(SkillRating rating) {
		this.ratings.remove(rating);
		rating.setRatedSkill(null);
	}

	@Override
	public boolean equals(Object obj) {

		// using the "business key" strategy of the ID dilemma
		// see
		// http://www.onjava.com/pub/a/onjava/2006/09/13/dont-let-hibernate-steal-your-identity.html

		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Skill)) {
			return false;
		}
		Skill that = (Skill) obj;
		EqualsBuilder eb = new EqualsBuilder();
		eb.append(this.getName(), that.getName());
		eb.append(this.getSkilledProfile(), that.getSkilledProfile());

		return eb.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder();
		hcb.append(this.name);
		hcb.append(this.getSkilledProfile());
		return hcb.toHashCode();
	}

    @Override
    public int compareTo(Object o) {
        Skill other = (Skill) o;
        return this.level.compareTo(other.level);
    }

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toStringExclude(this, "skilledProfile");
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

}
