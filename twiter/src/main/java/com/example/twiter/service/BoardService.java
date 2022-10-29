package com.example.twiter.service;

import com.example.twiter.dto.BoardDto;
import com.example.twiter.entity.Board;
import com.example.twiter.exceptionHandler.RestApiExceptionHandler;
import com.example.twiter.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final RestApiExceptionHandler exceptionHandler;

    @Transactional
    public ResponseEntity<?> getBoards() {

        List<Board> boards = boardRepository.findAll();

        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> createBoard(BoardDto dto) {

        Board saveBoard = new Board(dto);

        return new ResponseEntity<>(boardRepository.save(saveBoard), HttpStatus.OK);

    }

    @Transactional
    public ResponseEntity<?> updateBoard(BoardDto dto, Long boardId) {

        Board board = boardRepository.findById(boardId).orElse(null);

        if(board==null){
            return exceptionHandler.handleApiRequestException(new IllegalArgumentException("존재 하지 않는 게시글 입니다"));
        }

        board.update(dto);

        return new ResponseEntity<>(boardRepository.findById(boardId),HttpStatus.OK);

    }

    @Transactional
    public ResponseEntity<?> deleteBoard(Long boardId){

        Optional<Board> board = boardRepository.findById(boardId);

        if(board.isEmpty()){
            return new ResponseEntity<>("보드가 존재하지 않습니다",HttpStatus.BAD_REQUEST);
        }

        boardRepository.delete(board.orElse(null));

        return new ResponseEntity<>("삭제가 완료 되었습니다",HttpStatus.OK);

    }
}
