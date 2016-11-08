package com.github.bijoysingh.starter.async;

import android.os.AsyncTask;

/**
 * A simple wrapper for the asynchronous running
 * Created by bijoy on 11/8/16.
 */

public abstract class SimpleAsyncTask<T> extends AsyncTask<Void, Void, T> {
  @Override
  protected T doInBackground(Void... params) {
    return run();
  }

  @Override
  protected void onPostExecute(T t) {
    super.onPostExecute(t);
    handle(t);
  }

  protected abstract T run();

  protected abstract void handle(T t);
}
