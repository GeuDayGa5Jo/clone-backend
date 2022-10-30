package com.example.twiter.dto;

import java.util.LinkedList;
import java.util.List;

public class CommentListDto {

    List<CommentDto> listDto = new LinkedList<>();

    public void addBoard(CommentDto commentDto){
        listDto.add(commentDto);
    }

}
