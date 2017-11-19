package com.github.bijoysingh.starter.async;

import android.app.Activity;

public final class MultiAsyncTask {

  private static final int MAX_NUMBER_OF_THREADS = 6;
  private static SimpleThreadExecutor executor;

  private static SimpleThreadExecutor getExecutor() {
    executor = executor == null ? new SimpleThreadExecutor(MAX_NUMBER_OF_THREADS) : executor;
    return executor;
  }

  public static <T> void execute(final Activity activity, final Task<T> task) {
    getExecutor().executeNow(new Runnable() {
      @Override
      public void run() {
        final T result = task.run();
        activity.runOnUiThread(new Runnable() {
          @Override
          public void run() {
            task.handle(result);
          }
        });
      }
    });
  }

  public interface Task<T> {
    T run();

    void handle(T result);
  }

}
