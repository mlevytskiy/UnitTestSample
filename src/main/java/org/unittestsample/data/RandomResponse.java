package org.unittestsample.data;

/**
 * Random generator response.
 *
 * Such nested structure is needed because of JSON-RPC protocol.
 */
public class RandomResponse {
  private Result result;

  public RandomResponse(final int[] randoms) {
    result = new Result();
    result.random = new Random();
    result.random.data = randoms;
  }

  public int[] getData() {
    if (result != null) {
      return result.random.data;
    } else {
      return null;
    }
  }

  public static class Result {
    Random random;
  }

  public static class Random {
    int[] data;
  }
}
