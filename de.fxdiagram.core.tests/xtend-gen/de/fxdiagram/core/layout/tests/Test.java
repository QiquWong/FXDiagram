package de.fxdiagram.core.layout.tests;

import de.fxdiagram.core.HeadsUpDisplay;
import de.fxdiagram.core.XRoot;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class Test extends Application {
  public static void main(final String[] args) {
    Application.launch(args);
  }
  
  private XRoot root;
  
  private Node r;
  
  public void start(final Stage primaryStage) throws Exception {
    XRoot _xRoot = new XRoot();
    final Procedure1<XRoot> _function = new Procedure1<XRoot>() {
      public void apply(final XRoot it) {
        HeadsUpDisplay _headsUpDisplay = it.getHeadsUpDisplay();
        ObservableList<Node> _children = _headsUpDisplay.getChildren();
        Rectangle _rectangle = new Rectangle(10, 10, 10, 10);
        _children.add((Test.this.r = _rectangle));
      }
    };
    XRoot _doubleArrow = ObjectExtensions.<XRoot>operator_doubleArrow(_xRoot, _function);
    Scene _scene = new Scene(
      this.root = _doubleArrow, 320, 240);
    primaryStage.setScene(_scene);
    primaryStage.show();
    new Thread() {
      public void run() {
        try {
          Thread.sleep(1000);
          final Runnable _function = new Runnable() {
            public void run() {
              HeadsUpDisplay _headsUpDisplay = Test.this.root.getHeadsUpDisplay();
              ObservableList<Node> _children = _headsUpDisplay.getChildren();
              _children.remove(Test.this.r);
              InputOutput.<String>println("click");
            }
          };
          Platform.runLater(_function);
        } catch (Throwable _e) {
          throw Exceptions.sneakyThrow(_e);
        }
      }
    }.start();
  }
}