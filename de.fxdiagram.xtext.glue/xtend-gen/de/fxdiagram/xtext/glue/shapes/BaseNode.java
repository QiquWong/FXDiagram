package de.fxdiagram.xtext.glue.shapes;

import de.fxdiagram.annotations.properties.ModelNode;
import de.fxdiagram.core.model.DomainObjectDescriptor;
import de.fxdiagram.core.model.ModelElementImpl;
import de.fxdiagram.lib.nodes.RectangleBorderPane;
import de.fxdiagram.lib.simple.SimpleNode;
import de.fxdiagram.xtext.glue.behavior.LazyConnectionMappingBehavior;
import de.fxdiagram.xtext.glue.behavior.OpenElementInEditorBehavior;
import de.fxdiagram.xtext.glue.mapping.AbstractXtextDescriptor;
import java.util.Collections;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

@ModelNode
@SuppressWarnings("all")
public class BaseNode<T extends Object> extends SimpleNode {
  public BaseNode() {
    ReadOnlyObjectProperty<DomainObjectDescriptor> _domainObjectProperty = this.domainObjectProperty();
    final ChangeListener<DomainObjectDescriptor> _function = new ChangeListener<DomainObjectDescriptor>() {
      public void changed(final ObservableValue<? extends DomainObjectDescriptor> prop, final DomainObjectDescriptor oldVal, final DomainObjectDescriptor newVal) {
        if ((newVal instanceof AbstractXtextDescriptor<?>)) {
          ((AbstractXtextDescriptor<?>)newVal).injectMembers(BaseNode.this);
        }
      }
    };
    _domainObjectProperty.addListener(_function);
  }
  
  public BaseNode(final AbstractXtextDescriptor<T> descriptor) {
    super(descriptor);
    descriptor.injectMembers(this);
  }
  
  protected Node createNode() {
    RectangleBorderPane _xblockexpression = null;
    {
      Node _createNode = super.createNode();
      final RectangleBorderPane pane = ((RectangleBorderPane) _createNode);
      Color _rgb = Color.rgb(158, 188, 227);
      Stop _stop = new Stop(0, _rgb);
      Color _rgb_1 = Color.rgb(220, 230, 255);
      Stop _stop_1 = new Stop(1, _rgb_1);
      LinearGradient _linearGradient = new LinearGradient(
        0, 0, 1, 1, 
        true, CycleMethod.NO_CYCLE, 
        Collections.<Stop>unmodifiableList(CollectionLiterals.<Stop>newArrayList(_stop, _stop_1)));
      pane.setBackgroundPaint(_linearGradient);
      _xblockexpression = pane;
    }
    return _xblockexpression;
  }
  
  protected AbstractXtextDescriptor<T> getDescriptor() {
    DomainObjectDescriptor _domainObject = this.getDomainObject();
    return ((AbstractXtextDescriptor<T>) _domainObject);
  }
  
  public void doActivate() {
    super.doActivate();
    AbstractXtextDescriptor<T> _descriptor = this.getDescriptor();
    LazyConnectionMappingBehavior.<T>addLazyBehavior(this, _descriptor);
    OpenElementInEditorBehavior _openElementInEditorBehavior = new OpenElementInEditorBehavior(this);
    this.addBehavior(_openElementInEditorBehavior);
  }
  
  public void populate(final ModelElementImpl modelElement) {
    super.populate(modelElement);
  }
}
