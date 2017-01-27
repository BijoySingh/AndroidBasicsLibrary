package com.github.bijoysingh.starter.async;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Simple Thread Usage
 * Created by bijoy on 1/27/17.
 */

public class SimpleThreadExecutor {
  private ExecutorService mThreadPool;
  private List<Runnable> mRunnable;

  /**
   * Single Thread Executor
   */
  public SimpleThreadExecutor() {
    mThreadPool = Executors.newSingleThreadExecutor();
    mRunnable = new ArrayList<>();
  }

  /**
   * Thread Pool with number of threads set
   * @param numberThreads number of threads in the pool
   */
  public SimpleThreadExecutor(int numberThreads) {
    mThreadPool = Executors.newFixedThreadPool(numberThreads);
    mRunnable = new ArrayList<>();
  }

  /**
   * Add Runnables to run
   * @param runnable the Runnable
   * @return this instance
   */
  public SimpleThreadExecutor addRunnable(Runnable runnable) {
    mRunnable.add(runnable);
    return this;
  }

  /**
   * Executes this runnable (immediately if possible)
   * @param runnable the runnable
   */
  public SimpleThreadExecutor executeNow(Runnable runnable) {
    if (mThreadPool != null) {
      mThreadPool.execute(runnable);
    }
    return this;
  }

  /**
   * Execute all the runnables added till now.
   */
  public void execute() {
    if (mThreadPool != null && mRunnable != null) {
      for (Runnable runnable : mRunnable) {
        mThreadPool.execute(runnable);
      }
    }
  }

  /**
   * Executes this runnable
   * @param runnable the runnable
   */
  public static void execute(Runnable runnable) {
    SimpleThreadExecutor executor = new SimpleThreadExecutor();
    executor.executeNow(runnable);
  }
}
