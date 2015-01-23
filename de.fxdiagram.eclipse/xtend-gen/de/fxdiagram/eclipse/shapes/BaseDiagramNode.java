package de.fxdiagram.eclipse.shapes;

import com.google.common.base.Objects;
import de.fxdiagram.annotations.properties.ModelNode;
import de.fxdiagram.core.XDiagram;
import de.fxdiagram.core.model.DomainObjectDescriptor;
import de.fxdiagram.core.model.ModelElementImpl;
import de.fxdiagram.eclipse.behavior.LazyConnectionMappingBehavior;
import de.fxdiagram.eclipse.behavior.OpenElementInEditorBehavior;
import de.fxdiagram.eclipse.mapping.AbstractConnectionMappingCall;
import de.fxdiagram.eclipse.mapping.AbstractMapping;
import de.fxdiagram.eclipse.mapping.AbstractXtextDescriptor;
import de.fxdiagram.eclipse.mapping.IMappedElementDescriptor;
import de.fxdiagram.eclipse.mapping.NodeMapping;
import de.fxdiagram.eclipse.mapping.XDiagramConfigInterpreter;
import de.fxdiagram.lib.nodes.RectangleBorderPane;
import de.fxdiagram.lib.simple.OpenableDiagramNode;
import java.util.Collections;
import java.util.List;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@ModelNode
@SuppressWarnings("all")
public class BaseDiagramNode<T extends Object> extends OpenableDiagramNode {
  public BaseDiagramNode() {
    ReadOnlyObjectProperty<DomainObjectDescriptor> _domainObjectProperty = this.domainObjectProperty();
    final ChangeListener<DomainObjectDescriptor> _function = new ChangeListener<DomainObjectDescriptor>() {
      public void changed(final ObservableValue<? extends DomainObjectDescriptor> prop, final DomainObjectDescriptor oldVal, final DomainObjectDescriptor newVal) {
        BaseDiagramNode.this.injectMembers();
      }
    };
    _domainObjectProperty.addListener(_function);
  }
  
  public BaseDiagramNode(final IMappedElementDescriptor<T> descriptor) {
    super(descriptor);
    this.injectMembers();
  }
  
  protected void injectMembers() {
    final DomainObjectDescriptor descriptor = this.getDomainObject();
    if ((descriptor instanceof AbstractXtextDescriptor<?>)) {
      ((AbstractXtextDescriptor<?>)descriptor).injectMembers(this);
    }
  }
  
  public DomainObjectDescriptor getDomainObject() {
    DomainObjectDescriptor _domainObject = super.getDomainObject();
    return ((IMappedElementDescriptor<T>) _domainObject);
  }
  
  public void initializeGraphics() {
    super.initializeGraphics();
    RectangleBorderPane _pane = this.getPane();
    Color _rgb = Color.rgb(242, 236, 181);
    Stop _stop = new Stop(0, _rgb);
    Color _rgb_1 = Color.rgb(255, 248, 202);
    Stop _stop_1 = new Stop(1, _rgb_1);
    LinearGradient _linearGradient = new LinearGradient(
      0, 0, 1, 1, 
      true, CycleMethod.NO_CYCLE, 
      Collections.<Stop>unmodifiableList(CollectionLiterals.<Stop>newArrayList(_stop, _stop_1)));
    _pane.setBackgroundPaint(_linearGradient);
  }
  
  public void doActivate() {
    super.doActivate();
    final DomainObjectDescriptor descriptor = this.getDomainObject();
    if ((descriptor instanceof IMappedElementDescriptor<?>)) {
      AbstractMapping<?> _mapping = ((IMappedElementDescriptor<?>)descriptor).getMapping();
      if ((_mapping instanceof NodeMapping<?>)) {
        AbstractMapping<?> _mapping_1 = ((IMappedElementDescriptor<?>)descriptor).getMapping();
        final NodeMapping<T> nodeMapping = ((NodeMapping<T>) _mapping_1);
        LazyConnectionMappingBehavior<T> lazyBehavior = null;
        List<AbstractConnectionMappingCall<?, T>> _outgoing = nodeMapping.getOutgoing();
        final Function1<AbstractConnectionMappingCall<?, T>, Boolean> _function = new Function1<AbstractConnectionMappingCall<?, T>, Boolean>() {
          public Boolean apply(final AbstractConnectionMappingCall<?, T> it) {
            return Boolean.valueOf(it.isLazy());
          }
        };
        final Iterable<AbstractConnectionMappingCall<?, T>> lazyOutgoing = IterableExtensions.<AbstractConnectionMappingCall<?, T>>filter(_outgoing, _function);
        boolean _isEmpty = IterableExtensions.isEmpty(lazyOutgoing);
        boolean _not = (!_isEmpty);
        if (_not) {
          LazyConnectionMappingBehavior<T> _elvis = null;
          if (lazyBehavior != null) {
            _elvis = lazyBehavior;
          } else {
            LazyConnectionMappingBehavior<T> _lazyConnectionMappingBehavior = new LazyConnectionMappingBehavior<T>(this);
            _elvis = _lazyConnectionMappingBehavior;
          }
          lazyBehavior = _elvis;
          for (final AbstractConnectionMappingCall<?, T> out : lazyOutgoing) {
            XDiagramConfigInterpreter _xDiagramConfigInterpreter = new XDiagramConfigInterpreter();
            lazyBehavior.addConnectionMappingCall(out, _xDiagramConfigInterpreter, true);
          }
        }
        List<AbstractConnectionMappingCall<?, T>> _incoming = nodeMapping.getIncoming();
        final Function1<AbstractConnectionMappingCall<?, T>, Boolean> _function_1 = new Function1<AbstractConnectionMappingCall<?, T>, Boolean>() {
          public Boolean apply(final AbstractConnectionMappingCall<?, T> it) {
            return Boolean.valueOf(it.isLazy());
          }
        };
        final Iterable<AbstractConnectionMappingCall<?, T>> lazyIncoming = IterableExtensions.<AbstractConnectionMappingCall<?, T>>filter(_incoming, _function_1);
        boolean _isEmpty_1 = IterableExtensions.isEmpty(lazyIncoming);
        boolean _not_1 = (!_isEmpty_1);
        if (_not_1) {
          LazyConnectionMappingBehavior<T> _elvis_1 = null;
          if (lazyBehavior != null) {
            _elvis_1 = lazyBehavior;
          } else {
            LazyConnectionMappingBehavior<T> _lazyConnectionMappingBehavior_1 = new LazyConnectionMappingBehavior<T>(this);
            _elvis_1 = _lazyConnectionMappingBehavior_1;
          }
          lazyBehavior = _elvis_1;
          for (final AbstractConnectionMappingCall<?, T> in : lazyIncoming) {
            XDiagramConfigInterpreter _xDiagramConfigInterpreter_1 = new XDiagramConfigInterpreter();
            lazyBehavior.addConnectionMappingCall(in, _xDiagramConfigInterpreter_1, false);
          }
        }
        boolean _notEquals = (!Objects.equal(lazyBehavior, null));
        if (_notEquals) {
          this.addBehavior(lazyBehavior);
        }
      }
    }
    OpenElementInEditorBehavior _openElementInEditorBehavior = new OpenElementInEditorBehavior(this);
    this.addBehavior(_openElementInEditorBehavior);
    XDiagram _innerDiagram = this.getInnerDiagram();
    _innerDiagram.setIsLayoutOnActivate(true);
  }
  
  public void populate(final ModelElementImpl modelElement) {
    super.populate(modelElement);
  }
}
