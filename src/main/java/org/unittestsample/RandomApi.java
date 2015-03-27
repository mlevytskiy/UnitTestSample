package org.unittestsample;

import org.unittestsample.data.RandomRequest;
import org.unittestsample.data.RandomResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 *  Retrofit interface for random.org API. Random.org uses JSON-RPC protocol but just for one
 *  query we can do it by "hands" :)
 */
public interface RandomApi {
  @POST("/json-rpc/1/invoke")
  void getRandoms(@Body RandomRequest request, Callback<RandomResponse> callback);
}
