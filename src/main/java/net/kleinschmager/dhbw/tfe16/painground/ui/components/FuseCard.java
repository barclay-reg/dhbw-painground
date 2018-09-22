/**
 * 
 */
package net.kleinschmager.dhbw.tfe16.painground.ui.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.templatemodel.TemplateModel;

/**
 * @author robertkleinschmager
 *
 */
@Tag("fuse-card")
@HtmlImport("src/fuse-card.html")
public class FuseCard extends PolymerTemplate<TemplateModel> {

    public FuseCard(Element wrappedElement) {

        getElement().appendChild(wrappedElement);
    }
    
    public FuseCard(Component wrappedComponent) {
    		
    		getElement().appendChild(wrappedComponent.getElement());
    	
    }
}
