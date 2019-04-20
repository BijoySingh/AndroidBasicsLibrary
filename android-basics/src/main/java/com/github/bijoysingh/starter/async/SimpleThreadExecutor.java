package com.github.bijoysingh.starter.async;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

  public <T> Future<T> submit(Callable<T> callable) {
    if (mThreadPool != null) {
      return mThreadPool.submit(callable);
    }
    return null;
  }

  /**
   * Get the thread pool for specific operations
   * @return the threadpool
   */
  public ExecutorService getThreadPool() {
    return mThreadPool;
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
