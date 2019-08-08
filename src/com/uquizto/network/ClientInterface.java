package com.uquizto.network;

import com.uquizto.models.Question;
import com.uquizto.models.User;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ClientInterface {

    @POST("sign_up/")
    @Headers("Content-Type: application/json")
    Call<User> signUp(@Body User user);

    @POST("login/")
    @FormUrlEncoded
    Call<User> login(@Field("uname") String uname, @Field("password") String password);

    @POST("sync_score/")
    @FormUrlEncoded
    Call<User> syncScore(@Field("high_score") int high_score, @Field("uname") String uname);

    @GET("get_questions/")
    @Headers("Content-Type: application/json")
    Call<List<Question>> getQuestions();

}
