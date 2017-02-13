package com.github.bijoysingh.starter.async;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * Class to handle parallel activities
 * Created by bijoy on 2/12/17.
 *
 * @param <I> Input type
 * @param <O> Output type
 */
public class Parallel<I, O> {

  private int numberOfThreads;
  private ParallelExecutionListener<I, O> listener;

  public Parallel() {
    setDefaultNumberOfThreads();
  }

  public Parallel(int numberOfThreads) {
    this.numberOfThreads = numberOfThreads;
  }

  public Parallel(ParallelExecutionListener<I, O> listener) {
    setDefaultNumberOfThreads();
    this.listener = listener;
  }

  public Parallel(
      int numberOfThreads,
      ParallelExecutionListener<I, O> listener) {
    this.numberOfThreads = numberOfThreads;
    this.listener = listener;
  }

  /**
   * Set the listener for execution
   *
   * @param listener the listener
   */
  public void setListener(ParallelExecutionListener<I, O> listener) {
    this.listener = listener;
  }

  /**
   * Execute for in parallel
   *
   * @param inputs the list of inputs for the for
   * @return the list of outputs
   * @throws Exception execution
   */
  public List<O> For(List<I> inputs) throws Exception {
    ExecutorService service = Executors.newFixedThreadPool((int) (numberOfThreads * 1.25));
    List<Future<O>> futures = new ArrayList<>();
    for (final I input : inputs) {
      Callable<O> callable = new Callable<O>() {
        public O call() throws Exception {
          return listener == null ? null : listener.onExecution(input);
        }
      };
      futures.add(service.submit(callable));
    }
    service.shutdown();

    List<O> outputs = new ArrayList<>();
    for (Future<O> future : futures) {
      outputs.add(future.get());
    }
    return outputs;
  }

  /**
   * Interface for the Parallel execution listener. This is executed when the input is about to
   * be executed
   *
   * @param <I> input type
   * @param <O> output type
   */
  public interface ParallelExecutionListener<I, O> {
    O onExecution(I input);
  }

  /**
   * Sets the default number of threads
   */
  private void setDefaultNumberOfThreads() {
    numberOfThreads = Runtime.getRuntime().availableProcessors();
    numberOfThreads = numberOfThreads <= 0 ? 1 : numberOfThreads;
  }
}
