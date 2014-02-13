package de.fxdiagram.examples.ecore;

import de.fxdiagram.annotations.properties.ModelNode;
import de.fxdiagram.core.model.DomainObjectDescriptorImpl;
import de.fxdiagram.core.model.DomainObjectProvider;
import de.fxdiagram.core.model.ModelElementImpl;
import de.fxdiagram.examples.ecore.ESuperTypeHandle;
import de.fxdiagram.examples.ecore.EcoreDomainObjectProvider;
import org.eclipse.xtext.xbase.lib.Extension;

@ModelNode({ "id", "name", "provider" })
@SuppressWarnings("all")
public class ESuperTypeDescriptor extends DomainObjectDescriptorImpl<ESuperTypeHandle> {
  public ESuperTypeDescriptor(final ESuperTypeHandle it, @Extension final EcoreDomainObjectProvider provider) {
    super(((provider.getId(it.getSubType()) + "=") + Integer.valueOf(it.getSubType().getEAllSuperTypes().indexOf(it.getSuperType()))), 
      ((provider.getId(it.getSubType()) + "=") + Integer.valueOf(it.getSubType().getEAllSuperTypes().indexOf(it.getSuperType()))), provider);
  }
  
  /**
   * Automatically generated by @ModelNode. Needed for deserialization.
   */
  public ESuperTypeDescriptor() {
  }
  
  public void populate(final ModelElementImpl modelElement) {
    modelElement.addProperty(idProperty(), String.class);
    modelElement.addProperty(nameProperty(), String.class);
    modelElement.addProperty(providerProperty(), DomainObjectProvider.class);
  }
}
