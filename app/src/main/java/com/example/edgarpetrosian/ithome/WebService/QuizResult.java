package com.example.edgarpetrosian.ithome.WebService;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonPropertyOrder({
        "code",
        "quizresult"
})
public class QuizResult {

    @JsonProperty("code")
    private String code;
    @JsonProperty("quizresult")
    private String quizresult;
    @JsonProperty("message")
    private String message;

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("quizresult")
    public String getQuizresult() {
        return quizresult;
    }

    @JsonProperty("quizresult")
    public void setQuizresult(String quizresult) {
        this.quizresult = quizresult;
    }

}