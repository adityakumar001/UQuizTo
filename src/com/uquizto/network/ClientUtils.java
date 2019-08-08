package com.uquizto.network;

import com.google.gson.GsonBuilder;
import com.uquizto.models.Question;
import com.uquizto.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Client {

    private static final String BASE_URL = "http://127.0.0.1:8000/user/";
    private static Retrofit mRetrofit;

    public static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create())).build();
        }
        return mRetrofit;
    }
}

public class ClientUtils {

    public static String signUpCall(User user) {
        User user_ = new User();
        try {
            Response<User> response = Client.getRetrofit().create(ClientInterface.class).signUp(user).execute();
            user_.setName(response.body().getName());
            user_.setHigh_score(1);
        } catch (Exception ie) {
            user_.setName(ie.getMessage());
            user_.setHigh_score(-1);
        }
        return user_.getName();
    }

    public static List<Question> getQuestions() {
        final List<Question> questions = new ArrayList<>();
        try {
            Response<List<Question>> response = Client.getRetrofit().create(ClientInterface.class).getQuestions().execute();
            if (response.isSuccessful()) {
                if (response.body() != null)
                    questions.addAll(response.body());
            } else {
                Question error = new Question();
                error.setQuestion(response.message());
                questions.add(error);
            }
        } catch (IOException ie) {
            Question error = new Question();
            error.setQuestion(ie.getMessage());
            questions.add(error);
        }
        return questions;
    }

    public static void syncResults(User user) {
        Client.getRetrofit().create(ClientInterface.class).syncScore(user.getHigh_score(), user.getUname())
                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable throwable) {

                    }
                });
    }

    public static User login(String username, String password) {
        User user = new User();
        Call<User> loginCall = Client.getRetrofit().create(ClientInterface.class).login(username, password);
        try {
            Response<User> response = loginCall.execute();
            if (response.isSuccessful()) {
                user = response.body();

            } else {
                user.setName(response.message());
                user.setHigh_score(-1);
            }
        } catch (IOException ie) {
            user.setName(ie.getMessage());
            user.setHigh_score(-1);
        }
        return user;
    }
}
