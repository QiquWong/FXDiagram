package de.fxdiagram.core.tools;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import de.fxdiagram.annotations.logging.Logging;
import de.fxdiagram.core.HeadsUpDisplay;
import de.fxdiagram.core.XRoot;
import de.fxdiagram.core.layout.LayoutType;
import de.fxdiagram.core.tools.XDiagramTool;
import de.fxdiagram.core.tools.actions.CenterAction;
import de.fxdiagram.core.tools.actions.CloseAction;
import de.fxdiagram.core.tools.actions.DiagramAction;
import de.fxdiagram.core.tools.actions.ExitAction;
import de.fxdiagram.core.tools.actions.ExportSvgAction;
import de.fxdiagram.core.tools.actions.LayoutAction;
import de.fxdiagram.core.tools.actions.NavigateNextAction;
import de.fxdiagram.core.tools.actions.NavigatePreviousAction;
import de.fxdiagram.core.tools.actions.OpenAction;
import de.fxdiagram.core.tools.actions.SelectAllAction;
import de.fxdiagram.core.tools.actions.ZoomToFitAction;
import eu.hansolo.enzo.radialmenu.MenuItem;
import eu.hansolo.enzo.radialmenu.Options;
import eu.hansolo.enzo.radialmenu.RadialMenu;
import eu.hansolo.enzo.radialmenu.Symbol;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.ListExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@Logging
@SuppressWarnings("all")
public class MenuTool implements XDiagramTool {
  private XRoot root;
  
  private EventHandler<KeyEvent> keyHandler;
  
  private EventHandler<MouseEvent> mouseHandler;
  
  private Group menuGroup;
  
  private RadialMenu menu;
  
  private MenuItem selection;
  
  public MenuTool(final XRoot root) {
    this.root = root;
    final EventHandler<KeyEvent> _function = new EventHandler<KeyEvent>() {
      public void handle(final KeyEvent it) {
        DiagramAction _switchResult = null;
        KeyCode _code = it.getCode();
        final KeyCode getCode = _code;
        boolean _matched = false;
        if (!_matched) {
          if (Objects.equal(getCode,KeyCode.A)) {
            _matched=true;
            SelectAllAction _xifexpression = null;
            boolean _isShortcutDown = it.isShortcutDown();
            if (_isShortcutDown) {
              SelectAllAction _xblockexpression = null;
              {
                it.consume();
                SelectAllAction _selectAllAction = new SelectAllAction();
                _xblockexpression = (_selectAllAction);
              }
              _xifexpression = _xblockexpression;
            }
            _switchResult = _xifexpression;
          }
        }
        if (!_matched) {
          if (Objects.equal(getCode,KeyCode.C)) {
            _matched=true;
            CenterAction _xifexpression_1 = null;
            boolean _isShortcutDown_1 = it.isShortcutDown();
            if (_isShortcutDown_1) {
              CenterAction _xblockexpression_1 = null;
              {
                it.consume();
                CenterAction _centerAction = new CenterAction();
                _xblockexpression_1 = (_centerAction);
              }
              _xifexpression_1 = _xblockexpression_1;
            }
            _switchResult = _xifexpression_1;
          }
        }
        if (!_matched) {
          if (Objects.equal(getCode,KeyCode.E)) {
            _matched=true;
            ExportSvgAction _xifexpression_2 = null;
            boolean _isShortcutDown_2 = it.isShortcutDown();
            if (_isShortcutDown_2) {
              ExportSvgAction _xblockexpression_2 = null;
              {
                it.consume();
                ExportSvgAction _exportSvgAction = new ExportSvgAction();
                _xblockexpression_2 = (_exportSvgAction);
              }
              _xifexpression_2 = _xblockexpression_2;
            }
            _switchResult = _xifexpression_2;
          }
        }
        if (!_matched) {
          if (Objects.equal(getCode,KeyCode.F)) {
            _matched=true;
            ZoomToFitAction _xifexpression_3 = null;
            boolean _isShortcutDown_3 = it.isShortcutDown();
            if (_isShortcutDown_3) {
              ZoomToFitAction _xblockexpression_3 = null;
              {
                it.consume();
                boolean _isShiftDown = it.isShiftDown();
                if (_isShiftDown) {
                  Scene _scene = root.getScene();
                  Window _window = _scene.getWindow();
                  final Window window = _window;
                  boolean _matched_1 = false;
                  if (!_matched_1) {
                    if (window instanceof Stage) {
                      _matched_1=true;
                      ((Stage)window).setFullScreen(true);
                      return;
                    }
                  }
                }
                ZoomToFitAction _zoomToFitAction = new ZoomToFitAction();
                _xblockexpression_3 = (_zoomToFitAction);
              }
              _xifexpression_3 = _xblockexpression_3;
            }
            _switchResult = _xifexpression_3;
          }
        }
        if (!_matched) {
          if (Objects.equal(getCode,KeyCode.L)) {
            _matched=true;
            LayoutAction _xifexpression_4 = null;
            boolean _isShortcutDown_4 = it.isShortcutDown();
            if (_isShortcutDown_4) {
              LayoutAction _xblockexpression_4 = null;
              {
                it.consume();
                LayoutType _xifexpression_5 = null;
                boolean _isShiftDown = it.isShiftDown();
                if (_isShiftDown) {
                  _xifexpression_5 = LayoutType.NEATO;
                } else {
                  _xifexpression_5 = LayoutType.DOT;
                }
                LayoutAction _layoutAction = new LayoutAction(_xifexpression_5);
                _xblockexpression_4 = (_layoutAction);
              }
              _xifexpression_4 = _xblockexpression_4;
            }
            _switchResult = _xifexpression_4;
          }
        }
        if (!_matched) {
          if (Objects.equal(getCode,KeyCode.Q)) {
            _matched=true;
            ExitAction _xifexpression_5 = null;
            boolean _isShortcutDown_5 = it.isShortcutDown();
            if (_isShortcutDown_5) {
              ExitAction _exitAction = new ExitAction();
              _xifexpression_5 = _exitAction;
            }
            _switchResult = _xifexpression_5;
          }
        }
        if (!_matched) {
          if (Objects.equal(getCode,KeyCode.RIGHT)) {
            _matched=true;
            NavigateNextAction _navigateNextAction = new NavigateNextAction();
            _switchResult = _navigateNextAction;
          }
        }
        if (!_matched) {
          if (Objects.equal(getCode,KeyCode.LEFT)) {
            _matched=true;
            NavigatePreviousAction _navigatePreviousAction = new NavigatePreviousAction();
            _switchResult = _navigatePreviousAction;
          }
        }
        if (!_matched) {
          if (Objects.equal(getCode,KeyCode.PAGE_DOWN)) {
            _matched=true;
            NavigateNextAction _navigateNextAction_1 = new NavigateNextAction();
            _switchResult = _navigateNextAction_1;
          }
        }
        if (!_matched) {
          if (Objects.equal(getCode,KeyCode.PAGE_UP)) {
            _matched=true;
            NavigatePreviousAction _navigatePreviousAction_1 = new NavigatePreviousAction();
            _switchResult = _navigatePreviousAction_1;
          }
        }
        if (!_matched) {
          if (Objects.equal(getCode,KeyCode.ENTER)) {
            _matched=true;
            OpenAction _openAction = new OpenAction();
            _switchResult = _openAction;
          }
        }
        if (!_matched) {
          if (Objects.equal(getCode,KeyCode.PERIOD)) {
            _matched=true;
            OpenAction _openAction_1 = new OpenAction();
            _switchResult = _openAction_1;
          }
        }
        if (!_matched) {
          if (Objects.equal(getCode,KeyCode.ESCAPE)) {
            _matched=true;
            CloseAction _xblockexpression_5 = null;
            {
              it.consume();
              CloseAction _xifexpression_6 = null;
              RadialMenu.State _state = MenuTool.this.menu.getState();
              boolean _equals = Objects.equal(_state, RadialMenu.State.OPENED);
              if (_equals) {
                MenuTool.this.closeMenu();
                return;
              } else {
                CloseAction _closeAction = new CloseAction();
                _xifexpression_6 = _closeAction;
              }
              _xblockexpression_5 = (_xifexpression_6);
            }
            _switchResult = _xblockexpression_5;
          }
        }
        if (!_matched) {
          _switchResult = null;
        }
        final DiagramAction action = _switchResult;
        if (action!=null) {
          action.perform(root);
        }
      }
    };
    this.keyHandler = _function;
    Options _options = new Options();
    final Procedure1<Options> _function_1 = new Procedure1<Options>() {
      public void apply(final Options it) {
        it.setDegrees(360);
        it.setOffset((-90));
        it.setRadius(200);
        it.setButtonSize(72);
        it.setButtonAlpha(1.0);
      }
    };
    Options _doubleArrow = ObjectExtensions.<Options>operator_doubleArrow(_options, _function_1);
    final Function1<Symbol.Type,MenuItem> _function_2 = new Function1<Symbol.Type,MenuItem>() {
      public MenuItem apply(final Symbol.Type s) {
        MenuItem _menuItem = new MenuItem();
        final Procedure1<MenuItem> _function = new Procedure1<MenuItem>() {
          public void apply(final MenuItem it) {
            it.setSymbol(s);
            it.setSize(64);
          }
        };
        MenuItem _doubleArrow = ObjectExtensions.<MenuItem>operator_doubleArrow(_menuItem, _function);
        return _doubleArrow;
      }
    };
    List<MenuItem> _map = ListExtensions.<Symbol.Type, MenuItem>map(Collections.<Symbol.Type>unmodifiableList(Lists.<Symbol.Type>newArrayList(Symbol.Type.EJECT, Symbol.Type.GRAPH, Symbol.Type.CAMERA, Symbol.Type.SELECTION1, Symbol.Type.SELECTION2, Symbol.Type.ZOOM_IN)), _function_2);
    RadialMenu _radialMenu = new RadialMenu(_doubleArrow, _map);
    this.menu = _radialMenu;
    final EventHandler<MouseEvent> _function_3 = new EventHandler<MouseEvent>() {
      public void handle(final MouseEvent it) {
        MouseButton _button = it.getButton();
        boolean _equals = Objects.equal(_button, MouseButton.SECONDARY);
        if (_equals) {
          RadialMenu.State _state = MenuTool.this.menu.getState();
          boolean _equals_1 = Objects.equal(_state, RadialMenu.State.OPENED);
          if (_equals_1) {
            MenuTool.this.closeMenu();
            it.consume();
          } else {
            boolean _and = false;
            EventTarget _target = it.getTarget();
            Pane _diagramCanvas = root.getDiagramCanvas();
            boolean _equals_2 = Objects.equal(_target, _diagramCanvas);
            if (!_equals_2) {
              _and = false;
            } else {
              RadialMenu.State _state_1 = MenuTool.this.menu.getState();
              boolean _notEquals = (!Objects.equal(_state_1, RadialMenu.State.OPENED));
              _and = (_equals_2 && _notEquals);
            }
            if (_and) {
              MenuTool.this.openMenu();
              it.consume();
            }
          }
        }
      }
    };
    this.mouseHandler = _function_3;
  }
  
  protected boolean openMenu() {
    boolean _xblockexpression = false;
    {
      Group _group = new Group();
      this.menuGroup = _group;
      HeadsUpDisplay _headsUpDisplay = this.root.getHeadsUpDisplay();
      ObservableList<Node> _children = _headsUpDisplay.getChildren();
      final Procedure1<Group> _function = new Procedure1<Group>() {
        public void apply(final Group it) {
          BorderPane.setAlignment(it, Pos.CENTER);
          Scene _scene = MenuTool.this.root.getScene();
          double _width = _scene.getWidth();
          double _multiply = (0.5 * _width);
          it.setTranslateX(_multiply);
          Scene _scene_1 = MenuTool.this.root.getScene();
          double _height = _scene_1.getHeight();
          double _multiply_1 = (0.5 * _height);
          it.setTranslateY(_multiply_1);
          ObservableList<Node> _children = it.getChildren();
          final Procedure1<RadialMenu> _function = new Procedure1<RadialMenu>() {
            public void apply(final RadialMenu it) {
              it.show();
              it.open();
              final EventHandler<RadialMenu.ItemEvent> _function = new EventHandler<RadialMenu.ItemEvent>() {
                public void handle(final RadialMenu.ItemEvent it) {
                  MenuItem _item = it.getItem();
                  MenuTool.this.selection = _item;
                }
              };
              it.setOnItemSelected(_function);
              final EventHandler<RadialMenu.MenuEvent> _function_1 = new EventHandler<RadialMenu.MenuEvent>() {
                public void handle(final RadialMenu.MenuEvent it) {
                  MenuTool.this.closeMenu();
                  boolean _notEquals = (!Objects.equal(MenuTool.this.selection, null));
                  if (_notEquals) {
                    DiagramAction _switchResult = null;
                    Symbol.Type _symbol = MenuTool.this.selection.getSymbol();
                    final Symbol.Type _switchValue = _symbol;
                    boolean _matched = false;
                    if (!_matched) {
                      if (Objects.equal(_switchValue,Symbol.Type.GRAPH)) {
                        _matched=true;
                        LayoutAction _layoutAction = new LayoutAction(LayoutType.DOT);
                        _switchResult = _layoutAction;
                      }
                    }
                    if (!_matched) {
                      if (Objects.equal(_switchValue,Symbol.Type.CAMERA)) {
                        _matched=true;
                        ExportSvgAction _exportSvgAction = new ExportSvgAction();
                        _switchResult = _exportSvgAction;
                      }
                    }
                    if (!_matched) {
                      if (Objects.equal(_switchValue,Symbol.Type.EJECT)) {
                        _matched=true;
                        ExitAction _exitAction = new ExitAction();
                        _switchResult = _exitAction;
                      }
                    }
                    if (!_matched) {
                      if (Objects.equal(_switchValue,Symbol.Type.SELECTION1)) {
                        _matched=true;
                        SelectAllAction _selectAllAction = new SelectAllAction();
                        _switchResult = _selectAllAction;
                      }
                    }
                    if (!_matched) {
                      if (Objects.equal(_switchValue,Symbol.Type.SELECTION2)) {
                        _matched=true;
                        CenterAction _centerAction = new CenterAction();
                        _switchResult = _centerAction;
                      }
                    }
                    if (!_matched) {
                      if (Objects.equal(_switchValue,Symbol.Type.ZOOM_IN)) {
                        _matched=true;
                        ZoomToFitAction _zoomToFitAction = new ZoomToFitAction();
                        _switchResult = _zoomToFitAction;
                      }
                    }
                    if (!_matched) {
                      DiagramAction _xblockexpression = null;
                      {
                        MenuTool.LOG.warning(("Unhandled menu item " + MenuTool.this.selection));
                        _xblockexpression = (null);
                      }
                      _switchResult = _xblockexpression;
                    }
                    final DiagramAction action = _switchResult;
                    if (action!=null) {
                      action.perform(MenuTool.this.root);
                    }
                  }
                  MenuTool.this.selection = null;
                }
              };
              it.setOnMenuCloseFinished(_function_1);
            }
          };
          RadialMenu _doubleArrow = ObjectExtensions.<RadialMenu>operator_doubleArrow(MenuTool.this.menu, _function);
          _children.add(_doubleArrow);
        }
      };
      Group _doubleArrow = ObjectExtensions.<Group>operator_doubleArrow(this.menuGroup, _function);
      boolean _add = _children.add(_doubleArrow);
      _xblockexpression = (_add);
    }
    return _xblockexpression;
  }
  
  protected boolean closeMenu() {
    boolean _xblockexpression = false;
    {
      this.menu.hide();
      boolean _xifexpression = false;
      boolean _and = false;
      boolean _notEquals = (!Objects.equal(this.menuGroup, null));
      if (!_notEquals) {
        _and = false;
      } else {
        Parent _parent = this.menuGroup.getParent();
        boolean _notEquals_1 = (!Objects.equal(_parent, null));
        _and = (_notEquals && _notEquals_1);
      }
      if (_and) {
        HeadsUpDisplay _headsUpDisplay = this.root.getHeadsUpDisplay();
        ObservableList<Node> _children = _headsUpDisplay.getChildren();
        boolean _remove = _children.remove(this.menuGroup);
        _xifexpression = _remove;
      }
      _xblockexpression = (_xifexpression);
    }
    return _xblockexpression;
  }
  
  public boolean activate() {
    boolean _xblockexpression = false;
    {
      Scene _scene = this.root.getScene();
      _scene.<KeyEvent>addEventHandler(KeyEvent.KEY_PRESSED, this.keyHandler);
      Pane _diagramCanvas = this.root.getDiagramCanvas();
      _diagramCanvas.<MouseEvent>addEventHandler(MouseEvent.MOUSE_PRESSED, this.mouseHandler);
      _xblockexpression = (true);
    }
    return _xblockexpression;
  }
  
  public boolean deactivate() {
    boolean _xblockexpression = false;
    {
      Pane _diagramCanvas = this.root.getDiagramCanvas();
      _diagramCanvas.<MouseEvent>removeEventHandler(MouseEvent.MOUSE_PRESSED, this.mouseHandler);
      Scene _scene = this.root.getScene();
      _scene.<KeyEvent>removeEventHandler(KeyEvent.KEY_PRESSED, this.keyHandler);
      _xblockexpression = (true);
    }
    return _xblockexpression;
  }
  
  private static Logger LOG = Logger.getLogger("de.fxdiagram.core.tools.MenuTool");
    ;
}
