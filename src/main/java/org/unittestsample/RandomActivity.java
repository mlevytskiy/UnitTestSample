package org.unittestsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.unittestsample.data.RandomRequest;
import org.unittestsample.data.RandomResponse;

import java.util.Arrays;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.view.View.*;

/**
 *  Activity with field with random numbers and refresh button.
 *
 *  It has to load 3 random numbers and show them. Loading starts on activity creation.
 */
public class RandomActivity extends Activity {

  private Callback<RandomResponse> randomResponseCallback = new Callback<RandomResponse>() {
    @Override
    public void success(RandomResponse randomResponse, Response response) {
      processSuccess(randomResponse);
    }

    @Override
    public void failure(RetrofitError error) {
      processError(error);
    }
  };

  private OnClickListener refreshBtnClickListener = new OnClickListener() {
    @Override
    public void onClick(View v) {
      startLoadRandomNumbers();
    }
  };
  private View refreshBtn;
  private View loadingView;
  private TextView randomView;

  void startLoadRandomNumbers() {
    loadingView.setVisibility(VISIBLE);
    randomView.setVisibility(GONE);
    refreshBtn.setVisibility(GONE);

    final RandomApi api = ((UnitTestApplication) getApplication()).getApi();
    api.getRandoms(new RandomRequest(3, 1, 100), randomResponseCallback);
  }


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_random);

    loadingView = findViewById(R.id.random_loading);
    randomView = (TextView) findViewById(R.id.random_text);

    refreshBtn = findViewById(R.id.random_refresh);
    refreshBtn.setOnClickListener(refreshBtnClickListener);

    // Load random numbers on start.
    startLoadRandomNumbers();
  }

  void makeMainViewsVisible(final String text) {
    loadingView.setVisibility(GONE);

    randomView.setText(text);
    randomView.setVisibility(VISIBLE);

    refreshBtn.setVisibility(VISIBLE);
  }

  void processSuccess(final RandomResponse response) {
    makeMainViewsVisible(Arrays.toString(response.getData()));
  }

  void processError(final RetrofitError error) {
    makeMainViewsVisible("Error");
  }

}
