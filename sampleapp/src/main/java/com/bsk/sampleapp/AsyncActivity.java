package com.bsk.sampleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.bijoysingh.starter.async.MultiAsyncTask;
import com.github.bijoysingh.starter.async.SimpleAsyncTask;

public class AsyncActivity extends AppCompatActivity {

  LinearLayout layout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_thread_executor);

    layout = findViewById(R.id.executor_results);
  }

  @Override
  protected void onResume() {
    super.onResume();

    testAsyncTask();
    testMultiAsyncTask();
  }

  private void testAsyncTask() {
    for (int index = 1; index <= 10; index++) {
      final int staticIndex = index;
      SimpleAsyncTask<Integer> task = new SimpleAsyncTask<Integer>() {
        @Override
        protected Integer run() {
          return heavyOperation(staticIndex);
        }

        @Override
        protected void handle(Integer integer) {
          addTextView( "[DONE]... " + "testAsyncTask(" + integer + ")");
        }
      };
      task.execute();
    }
  }

  private void testMultiAsyncTask() {
    for (int index = 1; index <= 10; index++) {
      final int staticIndex = index;
      MultiAsyncTask.execute(this, new MultiAsyncTask.Task<Integer>() {
        @Override
        public Integer run() {
          return heavyOperation(staticIndex);
        }

        @Override
        public void handle(Integer integer) {
          addTextView( "[DONE]... " + "testMultiAsyncTask(" + integer + ")");
        }
      });
    }
  }

  private Integer heavyOperation(int value) {
    try {
      Thread.sleep(2000);
    } catch (Exception exception) {
      // Ignore
    }
    return value;
  }

  private void addTextView(String text) {
    TextView tv = new TextView(this);
    tv.setLayoutParams(new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
    ));
    tv.setText(text);
    layout.addView(tv);
  }
}
