package com.example.twiter.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class BoardDto {

    private String boardContent;
    private boolean retweet;

}
