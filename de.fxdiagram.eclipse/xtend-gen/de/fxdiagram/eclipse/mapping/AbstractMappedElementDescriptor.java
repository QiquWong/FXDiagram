package de.fxdiagram.eclipse.mapping;

import com.google.common.base.Objects;
import de.fxdiagram.annotations.properties.ModelNode;
import de.fxdiagram.core.model.DomainObjectDescriptorImpl;
import de.fxdiagram.core.model.ModelElementImpl;
import de.fxdiagram.eclipse.mapping.AbstractMapping;
import de.fxdiagram.eclipse.mapping.IMappedElementDescriptor;
import de.fxdiagram.eclipse.mapping.IMappedElementDescriptorProvider;
import de.fxdiagram.eclipse.mapping.XDiagramConfig;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

@ModelNode({ "mappingConfigID", "mappingID" })
@SuppressWarnings("all")
public abstract class AbstractMappedElementDescriptor<T extends Object> extends DomainObjectDescriptorImpl<T> implements IMappedElementDescriptor<T> {
  private AbstractMapping<T> mapping;
  
  public AbstractMappedElementDescriptor(final String id, final String name, final String mappingConfigID, final String mappingID, final IMappedElementDescriptorProvider provider) {
    super(id, name, provider);
    this.mappingConfigIDProperty.set(mappingConfigID);
    this.mappingIDProperty.set(mappingID);
  }
  
  public AbstractMapping<T> getMapping() {
    AbstractMapping<T> _xblockexpression = null;
    {
      boolean _equals = Objects.equal(this.mapping, null);
      if (_equals) {
        XDiagramConfig.Registry _instance = XDiagramConfig.Registry.getInstance();
        String _mappingConfigID = this.getMappingConfigID();
        final XDiagramConfig config = _instance.getConfigByID(_mappingConfigID);
        String _mappingID = this.getMappingID();
        AbstractMapping<?> _mappingByID = config.getMappingByID(_mappingID);
        this.mapping = ((AbstractMapping<T>) _mappingByID);
      }
      _xblockexpression = this.mapping;
    }
    return _xblockexpression;
  }
  
  /**
   * Automatically generated by @ModelNode. Needed for deserialization.
   */
  public AbstractMappedElementDescriptor() {
  }
  
  public void populate(final ModelElementImpl modelElement) {
    super.populate(modelElement);
    modelElement.addProperty(mappingConfigIDProperty, String.class);
    modelElement.addProperty(mappingIDProperty, String.class);
  }
  
  private ReadOnlyStringWrapper mappingConfigIDProperty = new ReadOnlyStringWrapper(this, "mappingConfigID");
  
  public String getMappingConfigID() {
    return this.mappingConfigIDProperty.get();
  }
  
  public ReadOnlyStringProperty mappingConfigIDProperty() {
    return this.mappingConfigIDProperty.getReadOnlyProperty();
  }
  
  private ReadOnlyStringWrapper mappingIDProperty = new ReadOnlyStringWrapper(this, "mappingID");
  
  public String getMappingID() {
    return this.mappingIDProperty.get();
  }
  
  public ReadOnlyStringProperty mappingIDProperty() {
    return this.mappingIDProperty.getReadOnlyProperty();
  }
}
