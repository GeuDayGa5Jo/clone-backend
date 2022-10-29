package com.example.twiter.controller;

import com.example.twiter.dto.BoardDto;
import com.example.twiter.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/boards/")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    @GetMapping
    public ResponseEntity<?> getBoards(){

        return boardService.getBoards();

    }

    @PostMapping("create")
    public ResponseEntity<?> createBoard(@RequestBody BoardDto dto){

        return boardService.createBoard(dto);

    }

    @PutMapping("{boardId}/update")
    public ResponseEntity<?> updateBoard(@RequestBody BoardDto dto, @PathVariable Long boardId){

        return boardService.updateBoard(dto,boardId);

    }

    @DeleteMapping("{boardId}/delete")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId){

        return boardService.deleteBoard(boardId);

    }
}
