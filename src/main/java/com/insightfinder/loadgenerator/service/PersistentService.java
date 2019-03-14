package com.insightfinder.loadgenerator.service;

import static com.insightfinder.loadgenerator.action.LoadActions.GENERATE_LOAD;

import com.insightfinder.loadgenerator.action.creator.LoadActionsCreator;
import com.ptmr3.fluxx.Fluxx;
import com.ptmr3.fluxx.annotation.Reaction;
import io.reactivex.Completable;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class PersistentService {
  private Logger logger = Logger.getLogger(PersistentService.class.getSimpleName());
  private LoadActionsCreator loadActionsCreator;
  private static PersistentService instance;

  public static PersistentService getInstance(LoadActionsCreator loadActionsCreator) {
    return instance == null ? (instance = new PersistentService(loadActionsCreator)) : instance;
  }

  private PersistentService(LoadActionsCreator loadActionsCreator) {
    Fluxx.Companion.getInstance().registerReactionSubscriber(this);
    this.loadActionsCreator = loadActionsCreator;
    loadActionsCreator.generateLoad();
  }

  @Reaction(reactionType = GENERATE_LOAD)
  public void ingestRawDataReaction() {
    logger.info("Reaction received, starting timer");
    Completable.timer(5000, TimeUnit.MILLISECONDS).blockingAwait();
    logger.info( "5 Seconds have passed, run again");
    loadActionsCreator.generateLoad();
  }
}
