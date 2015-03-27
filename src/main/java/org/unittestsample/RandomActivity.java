package org.unittestsample;

import android.app.Activity;
import android.os.Bundle;

/**
 *  Activity with field with random numbers and refresh button.
 *
 *  It has to load 3 random numbers and show them. Loading starts on activity creation.
 */
public class RandomActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_random);
  }

}
