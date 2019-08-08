package com.uquizto.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@ToString
@NoArgsConstructor
@RequiredArgsConstructor()
public class Question {
    @SerializedName("question")
    @Expose
    private @Getter
    @NonNull
    @Setter
    String question;

    @SerializedName("answer")
    @Expose
    private @Getter
    @NonNull
    String answer;

    @SerializedName("options")
    @Expose
    private @Getter
    @NonNull String[] options;
}