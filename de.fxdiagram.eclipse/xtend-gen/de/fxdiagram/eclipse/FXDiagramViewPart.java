package de.fxdiagram.eclipse;

import de.fxdiagram.examples.Demo;
import de.fxdiagram.swtfx.SwtToFXGestureConverter;
import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

@SuppressWarnings("all")
public class FXDiagramViewPart extends ViewPart {
  private FXCanvas canvas;
  
  public void createPartControl(final Composite parent) {
    FXCanvas _fXCanvas = new FXCanvas(parent, SWT.NONE);
    this.canvas = _fXCanvas;
    SwtToFXGestureConverter.register(this.canvas);
    Scene _createFxScene = this.createFxScene();
    this.canvas.setScene(_createFxScene);
  }
  
  protected Scene createFxScene() {
    Demo _demo = new Demo();
    Scene _createScene = _demo.createScene();
    return _createScene;
  }
  
  public void setFocus() {
    this.canvas.setFocus();
    this.setFxFocus();
  }
  
  protected void setFxFocus() {
  }
}
