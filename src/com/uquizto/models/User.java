package com.uquizto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@ToString
@RequiredArgsConstructor
@NoArgsConstructor
public class User {

    @SerializedName("name")
    @Expose
    private @Getter @Setter
    @NonNull
    String name;

    @SerializedName("uname")
    @Expose
    private @Getter
    @NonNull String uname;

    @SerializedName("age")
    @Expose
    private @Getter
    @NonNull int age;

    @SerializedName("gender")
    @Expose
    private @Getter
    @NonNull String gender;

    @SerializedName("country")
    @Expose
    private @Getter
    @NonNull String country;

    @SerializedName("password")
    @Expose
    private @Getter
    @Setter
    String password;

    private @Setter
    @Getter
    @NonNull int high_score;


    private @Getter
    @Setter
    User user;

}