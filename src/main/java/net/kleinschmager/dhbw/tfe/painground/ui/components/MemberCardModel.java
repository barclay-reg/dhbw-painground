package net.kleinschmager.dhbw.tfe.painground.ui.components;

import com.vaadin.flow.templatemodel.Include;
import com.vaadin.flow.templatemodel.TemplateModel;

import net.kleinschmager.dhbw.tfe.painground.persistence.model.ProjectMembership;
import net.kleinschmager.dhbw.tfe.painground.persistence.model.Skill;

import java.util.List;

public interface MemberCardModel extends TemplateModel {

    void setName(String name);

    String getName();

    void setSurName(String surName);

    String getSurName();

    @Include({"projectName", "startDateAsString", "endDateAsString"})
    void setProjects(List<ProjectMembership> projectMembership);

    List<ProjectMembership> getProjects();

    @Include({"name", "level.htmlColor"})
    void setRelevantSkills(List<Skill> skills);

    List<Skill> getRelevantSkills();

    //void setPicture(String picture);

    //String getPicture();

}
