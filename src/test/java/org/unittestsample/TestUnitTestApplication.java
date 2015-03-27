package org.unittestsample;

import static org.mockito.Mockito.mock;

/**
 * Robolectric uses class with name Test<ApplicationClassName> as test variant of the application
 * class. We use test application for API class injection so we need test version of this class.
 */
public class TestUnitTestApplication extends UnitTestApplication {

  @Override
  protected RandomApi createGitHubApi() {
    return mock(RandomApi.class);
  }

}
