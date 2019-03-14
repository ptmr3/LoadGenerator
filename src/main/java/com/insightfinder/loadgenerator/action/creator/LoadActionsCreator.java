package com.insightfinder.loadgenerator.action.creator;

import com.insightfinder.loadgenerator.action.LoadActions;
import com.ptmr3.fluxx.FluxxActionCreator;

public class LoadActionsCreator extends FluxxActionCreator implements LoadActions {
  private static LoadActionsCreator instance;

  public static LoadActionsCreator getInstance() {
    return instance == null ? (instance = new LoadActionsCreator()) : instance;
  }

  @Override
  public void generateLoad() {
    publishAction(GENERATE_LOAD);
  }
}
