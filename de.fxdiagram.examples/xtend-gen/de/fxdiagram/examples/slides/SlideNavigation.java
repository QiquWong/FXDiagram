package de.fxdiagram.examples.slides;

import de.fxdiagram.core.behavior.AbstractHostBehavior;
import de.fxdiagram.core.behavior.Behavior;
import de.fxdiagram.core.behavior.NavigationBehavior;
import de.fxdiagram.examples.slides.SlideDiagram;

@SuppressWarnings("all")
public class SlideNavigation extends AbstractHostBehavior<SlideDiagram> implements NavigationBehavior {
  public SlideNavigation(final SlideDiagram host) {
    super(host);
  }
  
  @Override
  protected void doActivate() {
  }
  
  @Override
  public Class<? extends Behavior> getBehaviorKey() {
    return NavigationBehavior.class;
  }
  
  @Override
  public boolean next() {
    SlideDiagram _host = this.getHost();
    return _host.next();
  }
  
  @Override
  public boolean previous() {
    SlideDiagram _host = this.getHost();
    return _host.previous();
  }
}
