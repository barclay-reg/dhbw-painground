package net.kleinschmager.dhbw.tfe16.painground.ui.components;

import com.vaadin.flow.templatemodel.TemplateModel;

public interface DashboardCounterBindingModel extends TemplateModel {
    void setCountDigit(Integer countDigit);
    Integer getCountDigit();
    
    void setTitle(String title);
    String getTitle();
    
    void setSubTitle(String countDigit);
    String getSubTitle();
}
