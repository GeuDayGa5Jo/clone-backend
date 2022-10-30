package com.example.twiter.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class ListResponseDto {
    List<BoardDto> listDto = new LinkedList<>();

    public void addBoard(BoardDto boardDto){
        listDto.add(boardDto);
    }

}
