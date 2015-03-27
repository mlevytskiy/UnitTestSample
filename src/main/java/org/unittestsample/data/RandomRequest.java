package org.unittestsample.data;

import java.util.HashMap;
import java.util.Map;

/**
 *  Random generator request.
 *
 *  Such nested structure is needed because of JSON-RPC protocol.
 */
public class RandomRequest {
  private static final String API_KEY = "33b1f5c9-1244-4db9-8b2a-d2ae0ec3f034";

  final String jsonrpc = "2.0";
  final String method = "generateIntegers";
  final int id = 42;

  final Map<String, Object> params;

  public RandomRequest(final int n, final int min, final int max) {
    params = new HashMap<>();
    params.put("apiKey", API_KEY);
    params.put("n", n);
    params.put("min", min);
    params.put("max", max);
    params.put("replacement", true);
  }
}
