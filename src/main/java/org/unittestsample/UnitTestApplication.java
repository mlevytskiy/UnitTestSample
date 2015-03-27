package org.unittestsample;

import android.app.Application;

import retrofit.RestAdapter;

/**
 * Unit test application.
 *
 * Is used for API injection (activity will get API object from this class). This will give
 * us ability to mock API object in tests.
 */
public class UnitTestApplication extends Application {

  private RandomApi gitHubApi;

  @Override
  public void onCreate() {
    super.onCreate();
    this.gitHubApi = createGitHubApi();
  }

  // This class has to be package visible so test version of application will be able to inherit
  // this class and overload this method.
  RandomApi createGitHubApi() {
    return new RestAdapter.Builder()
        .setEndpoint("https://api.random.org/")
        .setLogLevel(RestAdapter.LogLevel.FULL)
        .build()
        .create(RandomApi.class);
  }

  public RandomApi getApi() {
    return gitHubApi;
  }
}
