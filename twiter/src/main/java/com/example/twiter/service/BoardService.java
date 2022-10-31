package com.example.twiter.service;

import com.example.twiter.dto.BoardDto;
import com.example.twiter.entity.Board;
import com.example.twiter.entity.Member;
import com.example.twiter.entity.util.S3Uploader;
import com.example.twiter.exceptionHandler.RestApiExceptionHandler;
import com.example.twiter.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    private final RestApiExceptionHandler exceptionHandler;

    private final S3Uploader s3Uploader;

    @Transactional
    public ResponseEntity<?> getBoards() {

        List<Board> boards = boardRepository.findAll();

        BoardDto boardDtos = new BoardDto();
        for (Board board : boards) {
            boardDtos.addBoard(new BoardDto(board));
        }




        return new ResponseEntity<>( boardDtos, HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<?> createBoard(BoardDto dto, Member member) throws IOException {

        if(dto.getImageFile()==null){

            Board saveBoard = new Board(dto, member);

            return new ResponseEntity<>(boardRepository.save(saveBoard), HttpStatus.OK);
        }

        Board board = Board.builder()
                .boardContent(dto.getBoardContent())
                .member(member)
                .retweet(dto.isRetweet())
                .imageFile(s3Uploader.upload(dto.getImageFile(), "member"))
                .build();

        return new ResponseEntity<>(boardRepository.save(board), HttpStatus.OK);

    }

    @Transactional
    public ResponseEntity<?> updateBoard(BoardDto dto, Long boardId, Member member) throws IOException {

        Board board = boardRepository.findById(boardId).orElse(null);

        if(board==null){
            return exceptionHandler.handleApiRequestException(new IllegalArgumentException("존재 하지 않는 게시글 입니다"));
        }
        else if(board.getMember().getMemberId()!=member.getMemberId()){
            return exceptionHandler.handleApiRequestException(new IllegalArgumentException("작성자가 다릅니다"));
        }

        if(dto.getImageFile()==null) {
            board.update(dto);
        }
        else{

            int sliceNum = board.getImageFile().lastIndexOf("/",board.getImageFile().lastIndexOf("/")-1);
            s3Uploader.deleteFile(board.getImageFile().substring(sliceNum+1));


            board.update(dto, s3Uploader.upload(dto.getImageFile(), "member"));

        }

        return new ResponseEntity<>(boardRepository.findById(boardId),HttpStatus.OK);

    }

    @Transactional
    public ResponseEntity<?> deleteBoard(Long boardId, Member member){

        Optional<Board> board = boardRepository.findById(boardId);

        if(board.isEmpty()){
            return exceptionHandler.handleApiRequestException(new IllegalArgumentException("존재 하지 않는 게시글 입니다"));
        }
        else if(board.get().getMember().getMemberId()!=member.getMemberId()){
            return exceptionHandler.handleApiRequestException(new IllegalArgumentException("작성자가 다릅니다"));
        }

        boardRepository.delete(board.orElse(null));

        return new ResponseEntity<>("삭제가 완료 되었습니다",HttpStatus.OK);

    }
}

