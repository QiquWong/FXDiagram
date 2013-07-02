package de.fxdiagram.core;

import de.fxdiagram.core.XActivatable;
import de.fxdiagram.core.XConnection;
import de.fxdiagram.core.geometry.TransformExtensions;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;
import org.eclipse.xtext.xbase.lib.DoubleExtensions;

@SuppressWarnings("all")
public class XConnectionLabel extends Parent implements XActivatable {
  private Text text;
  
  public XConnectionLabel(final XConnection connection) {
    this.setConnection(connection);
    connection.setLabel(this);
    Text _text = new Text();
    this.text = _text;
    ObservableList<Node> _children = this.getChildren();
    _children.add(this.text);
  }
  
  public String getText() {
    String _text = this.text.getText();
    return _text;
  }
  
  public void setText(final String text) {
    this.text.setText(text);
  }
  
  public StringProperty textProperty() {
    StringProperty _textProperty = this.text.textProperty();
    return _textProperty;
  }
  
  public void activate() {
    boolean _isActive = this.getIsActive();
    boolean _not = (!_isActive);
    if (_not) {
      this.doActivate();
    }
    this.isActiveProperty.set(true);
  }
  
  public void doActivate() {
    XConnection _connection = this.getConnection();
    ObservableList<Double> _points = _connection.getPoints();
    this.place(_points);
    XConnection _connection_1 = this.getConnection();
    ObservableList<Double> _points_1 = _connection_1.getPoints();
    final ListChangeListener<Double> _function = new ListChangeListener<Double>() {
        public void onChanged(final Change<? extends Double> it) {
          ObservableList<? extends Double> _list = it.getList();
          XConnectionLabel.this.place(_list);
        }
      };
    _points_1.addListener(_function);
    ReadOnlyObjectProperty<Bounds> _boundsInLocalProperty = this.boundsInLocalProperty();
    final ChangeListener<Bounds> _function_1 = new ChangeListener<Bounds>() {
        public void changed(final ObservableValue<? extends Bounds> element, final Bounds oldVlaue, final Bounds newValue) {
          XConnection _connection = XConnectionLabel.this.getConnection();
          ObservableList<Double> _points = _connection.getPoints();
          XConnectionLabel.this.place(_points);
        }
      };
    _boundsInLocalProperty.addListener(_function_1);
  }
  
  protected Boolean place(final List<? extends Double> list) {
    Boolean _xifexpression = null;
    int _size = list.size();
    boolean _greaterEqualsThan = (_size >= 4);
    if (_greaterEqualsThan) {
      boolean _xblockexpression = false;
      {
        int _size_1 = list.size();
        int _minus = (_size_1 - 2);
        Double _get = list.get(_minus);
        Double _get_1 = list.get(0);
        final double dx = DoubleExtensions.operator_minus(_get, _get_1);
        int _size_2 = list.size();
        int _minus_1 = (_size_2 - 1);
        Double _get_2 = list.get(_minus_1);
        Double _get_3 = list.get(1);
        final double dy = DoubleExtensions.operator_minus(_get_2, _get_3);
        double angle = Math.atan2(dy, dx);
        Bounds _boundsInLocal = this.getBoundsInLocal();
        double _width = _boundsInLocal.getWidth();
        double _minus_2 = (-_width);
        final double labelDx = (_minus_2 / 2);
        int labelDy = (-4);
        double _abs = Math.abs(angle);
        double _divide = (Math.PI / 2);
        boolean _greaterThan = (_abs > _divide);
        if (_greaterThan) {
          boolean _lessThan = (angle < 0);
          if (_lessThan) {
            double _plus = (angle + Math.PI);
            angle = _plus;
          } else {
            boolean _greaterThan_1 = (angle > 0);
            if (_greaterThan_1) {
              double _minus_3 = (angle - Math.PI);
              angle = _minus_3;
            }
          }
          int _minus_4 = (-4);
          labelDy = _minus_4;
        }
        Affine _affine = new Affine();
        final Affine transform = _affine;
        TransformExtensions.translate(transform, labelDx, labelDy);
        double _multiply = (angle * 180);
        double _divide_1 = (_multiply / Math.PI);
        TransformExtensions.rotate(transform, _divide_1);
        Double _get_4 = list.get(0);
        int _size_3 = list.size();
        int _minus_5 = (_size_3 - 2);
        Double _get_5 = list.get(_minus_5);
        double _plus_1 = DoubleExtensions.operator_plus(_get_4, _get_5);
        final double xPos = (_plus_1 / 2);
        Double _get_6 = list.get(1);
        int _size_4 = list.size();
        int _minus_6 = (_size_4 - 1);
        Double _get_7 = list.get(_minus_6);
        double _plus_2 = DoubleExtensions.operator_plus(_get_6, _get_7);
        final double yPos = (_plus_2 / 2);
        TransformExtensions.translate(transform, xPos, yPos);
        ObservableList<Transform> _transforms = this.getTransforms();
        _transforms.clear();
        ObservableList<Transform> _transforms_1 = this.getTransforms();
        boolean _add = _transforms_1.add(transform);
        _xblockexpression = (_add);
      }
      _xifexpression = Boolean.valueOf(_xblockexpression);
    }
    return _xifexpression;
  }
  
  private SimpleObjectProperty<XConnection> connectionProperty = new SimpleObjectProperty<XConnection>(this, "connection");
  
  public XConnection getConnection() {
    return this.connectionProperty.get();
    
  }
  
  public void setConnection(final XConnection connection) {
    this.connectionProperty.set(connection);
    
  }
  
  public ObjectProperty<XConnection> connectionProperty() {
    return this.connectionProperty;
    
  }
  
  private ReadOnlyBooleanWrapper isActiveProperty = new ReadOnlyBooleanWrapper(this, "isActive");
  
  public boolean getIsActive() {
    return this.isActiveProperty.get();
    
  }
  
  public ReadOnlyBooleanProperty isActiveProperty() {
    return this.isActiveProperty.getReadOnlyProperty();
    
  }
}