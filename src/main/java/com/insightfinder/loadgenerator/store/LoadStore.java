package com.insightfinder.loadgenerator.store;

import static com.insightfinder.loadgenerator.action.LoadActions.GENERATE_LOAD;
import static java.lang.Math.cbrt;
import static java.lang.Math.tan;
import static java.lang.StrictMath.atan;

import com.ptmr3.fluxx.FluxxAction;
import com.ptmr3.fluxx.FluxxStore;
import com.ptmr3.fluxx.annotation.Action;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class LoadStore extends FluxxStore {

  private static final String THREAD = "Thread: ";
  private static final String MAX_PRIORITY = "-MAX_PRIORITY";
  private static LoadStore instance;
  private Logger logger = Logger.getLogger(LoadStore.class.getSimpleName());

  public static LoadStore getInstance() {
    return instance == null ? (instance = new LoadStore()) : instance;
  }

  @Action(actionType = GENERATE_LOAD)
  public void ingestRawData(FluxxAction fluxxAction) {
    class MyRunnable implements Runnable {

      @Override
      public void run() {
        for (int i = 0; i < 1_000_000; i++) {
          double tangent = tan(
              atan(tan(atan(tan(atan(tan(atan(tan(atan(123456789.123456789))))))))));
          cbrt(tangent);
        }
        logger.info("Thread has finished -> " + Thread.currentThread().getName());
      }
    }
    final int NUMBER_OF_THREADS = 32;
    List<Thread> threadList = new ArrayList<>(NUMBER_OF_THREADS);
    for (int i = 1; i <= NUMBER_OF_THREADS; i++) {
      Thread thread = new Thread(new MyRunnable());
      if (i == NUMBER_OF_THREADS) {
        // Last thread gets MAX_PRIORITY
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.setName(THREAD + i + MAX_PRIORITY);
      } else {
        // All other threads get MIN_PRIORITY
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.setName(THREAD + i);
      }
      threadList.add(thread);
    }

    threadList.forEach(Thread::start);
    for (Thread thread : threadList) {
      try {
        thread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    publishReaction(GENERATE_LOAD);
  }
}
