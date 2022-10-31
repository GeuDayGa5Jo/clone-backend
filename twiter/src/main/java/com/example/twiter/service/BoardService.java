package com.example.twiter.service;

import com.example.twiter.dto.BoardDto;
import com.example.twiter.dto.CommentDto;
import com.example.twiter.dto.CommentListDto;
import com.example.twiter.dto.ListResponseDto;
import com.example.twiter.entity.Board;
import com.example.twiter.entity.Comment;
import com.example.twiter.entity.Member;
import com.example.twiter.entity.util.S3Uploader;
import com.example.twiter.exceptionHandler.RestApiExceptionHandler;
import com.example.twiter.repository.BoardRepository;
import com.example.twiter.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final RestApiExceptionHandler exceptionHandler;

    private final S3Uploader s3Uploader;

    @Transactional
    public ResponseEntity<?> getBoards() {

        List<Board> boards = boardRepository.findAll();
        List<BoardDto> boardList = new ArrayList<>();
        List<Comment> comments = commentRepository.findAll();
        List<CommentDto> commentDto = new ArrayList<>();

        for (Board board : boards) {
            boardList.add(new BoardDto(board));
        }


        return new ResponseEntity<>( boardList, HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<?> createBoard(BoardDto dto, Member member) throws IOException {


//            return new ResponseEntity<>("성공적으로 생성 되었습니다(보드 추가시)", HttpStatus.OK);
        if(dto.getImageFile()==null){
            System.out.println("넌 나오면 안되겠지?");
            Board saveWithoutFileBoard = new Board(dto, member);
            boardRepository.save(saveWithoutFileBoard);

            return new ResponseEntity<>("성공적으로 생성 되었습니다(보드 추가시)", HttpStatus.OK);
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

        return new ResponseEntity<>("수정이 완료 되었습니다",HttpStatus.OK);

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

    @Transactional
    public ResponseEntity<?> getBoard(Long boardId) {

        Board board = boardRepository.findById(boardId).orElse(null);
        if(board==null){
            return exceptionHandler.handleApiRequestException(new IllegalArgumentException("게시글이 존재하지 않습니다"));
        }
        List <CommentDto> commentList = new ArrayList<>();

        List<Comment> comments = commentRepository.findCommentByBoard_BoardId(boardId);
        for (Comment comment : comments) {
            CommentDto dto = new CommentDto(comment);
            commentList.add(dto);
        }

        BoardDto dto = new BoardDto(board,commentList);


        return new ResponseEntity<>(dto,HttpStatus.OK);

    }
}

