package de.fxdiagram.core.model;

import de.fxdiagram.core.model.XModelProvider;

@SuppressWarnings("all")
public interface DomainObjectHandle extends XModelProvider {
  public abstract String getName();
  
  public abstract String getId();
  
  public abstract Object getDomainObject();
}