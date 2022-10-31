package com.example.twiter.controller;

import com.example.twiter.dto.BoardDto;
import com.example.twiter.security.MemberDetailsImpl;
import com.example.twiter.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth/boards/")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    @GetMapping
    public ResponseEntity<?> getBoards(){

        return boardService.getBoards();

    }

    @GetMapping("infinities")
    public ResponseEntity<?> getBoardInfinite(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc) {
        int pageTemp = page - 1;
        return boardService.getBoardInfiniteScroll(pageTemp, size, sortBy, isAsc);
    }

    @PostMapping("create")
    public ResponseEntity<?> createBoard(@ModelAttribute BoardDto dto , @AuthenticationPrincipal MemberDetailsImpl memberDetails) throws IOException {

        return boardService.createBoard(dto,memberDetails.getMember());

    }

    @PutMapping("{boardId}/update")
    public ResponseEntity<?> updateBoard(@ModelAttribute BoardDto dto, @PathVariable Long boardId , @AuthenticationPrincipal MemberDetailsImpl memberDetails) throws IOException {

        return boardService.updateBoard(dto,boardId, memberDetails.getMember());

    }

    @DeleteMapping("{boardId}/delete")
    public ResponseEntity<?> deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal MemberDetailsImpl memberDetails){

        return boardService.deleteBoard(boardId,memberDetails.getMember());

    }
}
