package com.example.twiter.dto.Request;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class BoardRequestDto {
    private String boardContent;
    private MultipartFile imageFile;
    private boolean retweet;
}
