package net.kleinschmager.dhbw.tfe16.painground.persistence.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "mp03_projectmembership")
public class ProjectMembership implements Comparable {

    public static final String COLUMN_FK_PROJECT = "mp02_id_companyproject";

    public static final String COLUMN_FK_MEMBER = "mp01_id_projectmember";

    @Id
    // using GenerationType.IDENTITY must along with liquibase AUTOINCREMENT .. no
    // sequence
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = COLUMN_FK_PROJECT)
    private CompanyProject companyProject;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = COLUMN_FK_MEMBER)
    private MemberProfile projectProfile;

    public ProjectMembership(@NotNull CompanyProject companyProject, @NotNull Date startDate, Date endDate,
                             @NotNull MemberProfile projectProfile) {
        this.companyProject = companyProject;
        this.companyProject.addMember(this);

        this.startDate = startDate;
        this.endDate = endDate;

        this.projectProfile = projectProfile;
        this.projectProfile.addProjectMembership(this);
    }

    public ProjectMembership(@NotNull CompanyProject companyProject, @NotNull Date startDate,
                             @NotNull MemberProfile projectProfile) {
        this.companyProject = companyProject;
        this.companyProject.addMember(this);

        this.startDate = startDate;
        this.projectProfile = projectProfile;

        this.projectProfile.addProjectMembership(this);
    }

    public ProjectMembership(@NotNull CompanyProject companyProject, @NotNull MemberProfile projectProfile) {
        this(companyProject, Date.from(Instant.now()), projectProfile);
    }

    protected ProjectMembership() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompanyProject getCompanyProject() {
        return companyProject;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStartDateAsString() {
        return dateAsString(startDate);
    }

    public String getEndDateAsString() {
        return dateAsString(endDate);
    }

    private String dateAsString(Date date) {
        String simpleDateAsString = "";
        if (date != null) {
            simpleDateAsString = new SimpleDateFormat("MM.yyyy").format(date);
        }
        return simpleDateAsString;
    }

    public void setProjectMember(MemberProfile projectProfile) {
        this.projectProfile = projectProfile;
    }

    public MemberProfile getProjectMember() {
        return this.projectProfile;
    }

    public String getProjectName() {
        return companyProject.getName();
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(this.companyProject);
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CompanyProject)) {
            return false;
        }

        ProjectMembership that = (ProjectMembership) obj;
        EqualsBuilder eb = new EqualsBuilder();

        eb.append(this.getCompanyProject(), that.getCompanyProject());
        eb.append(this.getStartDate(), that.getStartDate());
        eb.append(this.getEndDate(), that.getEndDate());

        return eb.isEquals();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toStringExclude(this, "projectProfile");
    }

    @Override
    public int compareTo(Object o) {
        ProjectMembership other = (ProjectMembership) o;
        return this.startDate.compareTo(other.startDate);
    }

}
