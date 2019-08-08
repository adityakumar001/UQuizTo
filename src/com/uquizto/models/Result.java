package com.uquizto.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class Result {
    public int correct, incorrect, nonAttempts;
    private @Setter
    @Getter
    long startTime, endTime;
}
