package org.unittestsample;

import android.view.View;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.unittestsample.data.RandomRequest;
import org.unittestsample.data.RandomResponse;

import retrofit.Callback;
import retrofit.RetrofitError;

import static org.assertj.android.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

// Robolectric supports only SDK 18- so we need emulate it.
// Also now gradle and Android Studio JUnit test runner run tests from different working
// directories so we need to specify Android manifest location implicitly.
// WARNING: if your application uses flavors then implicit manifest location could break tests.
@Config(emulateSdk = 18, manifest = "src/main/AndroidManifest.xml")

// We have to use custom Robolectric test runner because with it Robolectric can control
// class loading.
@RunWith(RobolectricTestRunner.class)
public class RandomActivityTest {

  private RandomActivity activity;
  private View loadbar;
  private View refreshBtn;
  private TextView randomView;
  
  @Before
  public void setUp() throws Exception {
    activity = Robolectric.setupActivity(RandomActivity.class);

    loadbar = activity.findViewById(R.id.random_loading);
    refreshBtn = activity.findViewById(R.id.random_refresh);
    randomView = (TextView) activity.findViewById(R.id.random_text);
  }

  @Test
  public void shouldShowOnlyLoadbarInitially() throws Exception {
    assertThat(loadbar).isVisible();
    assertThat(refreshBtn).isGone();
    assertThat(randomView).isGone();
  }

  @Test
  @SuppressWarnings("unchecked")
  public void shouldInitRundomNumbersLoadingOnStart() throws Exception {
    final RandomApi api = ((UnitTestApplication) Robolectric.application).getApi();
    verify(api).getRandoms(any(RandomRequest.class), any(Callback.class));
  }

  @Test
  public void shouldShowLoadingResult() throws Exception {
    activity.processSuccess(new RandomResponse(new int[]{1, 2, 3}));
    assertThat(loadbar).isGone();

    assertThat(randomView).isVisible();
    assertThat(randomView).containsText("[1, 2, 3]");

    assertThat(refreshBtn).isVisible();
  }

  @Test
  public void shouldProcessError() throws Exception {
    activity.processError(mock(RetrofitError.class));
    assertThat(loadbar).isGone();

    assertThat(randomView).isVisible();
    assertThat(randomView).containsText("Error");

    assertThat(refreshBtn).isVisible();
  }

  @Test
  public void shouldShowLoadingOnRefresh() throws Exception {
    activity.processSuccess(new RandomResponse(new int[]{1, 2, 3}));
    Robolectric.clickOn(refreshBtn);

    assertThat(loadbar).isVisible();
    assertThat(randomView).isGone();
    assertThat(refreshBtn).isGone();
  }

  @Test
  @SuppressWarnings("unchecked")
  public void shouldLoadNewDataOnRefresh() throws Exception {
    activity.processSuccess(new RandomResponse(new int[] {1, 2, 3}));
    Robolectric.clickOn(refreshBtn);

    final RandomApi api = ((UnitTestApplication) Robolectric.application).getApi();
    //This method should be called twice: on onCreate and on click on "Refresh" button
    verify(api, times(2)).getRandoms(any(RandomRequest.class), any(Callback.class));
  }
}
