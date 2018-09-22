package net.kleinschmager.dhbw.tfe16.painground.persistence.model;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "mp02_companyproject")
public class CompanyProject {

    @Id
    // using GenerationType.IDENTITY must along with liquibase AUTOINCREMENT .. no
    // sequence
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @OneToMany(
        mappedBy = "companyProject",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER)
    private Set<ProjectMembership> memberOfProject = new HashSet<>();

    public CompanyProject(@NotNull String name) {
        Preconditions.checkNotNull(name);
        this.name = name;
    }

    protected CompanyProject() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addMember(ProjectMembership projectMembership) {
        this.memberOfProject.add(projectMembership);
    }

    public void removeMember(ProjectMembership projectMembership) {
        this.memberOfProject.remove(projectMembership);
    }

    public List<ProjectMembership> getMembershipOfProject() {
        return ImmutableList.copyOf(this.memberOfProject);
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder();
        hcb.append(this.name);
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
        CompanyProject that = (CompanyProject) obj;
        EqualsBuilder eb = new EqualsBuilder();
        eb.append(this.getName().toLowerCase(), that.getName().toLowerCase());

        return eb.isEquals();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toStringExclude(this, "CompanyProject");
    }


}
