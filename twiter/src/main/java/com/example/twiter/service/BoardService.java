package com.example.twiter.service;

import com.example.twiter.dto.Request.BoardRequestDto;
import com.example.twiter.dto.Request.CommentRequestDto;
import com.example.twiter.dto.Response.BoardResponseDto;
import com.example.twiter.dto.Response.MyPageResponseDto;
import com.example.twiter.entity.Board;
import com.example.twiter.entity.Comment;
import com.example.twiter.entity.Member;
import com.example.twiter.entity.util.S3Uploader;
import com.example.twiter.exceptionHandler.RestApiExceptionHandler;
import com.example.twiter.repository.BoardRepository;
import com.example.twiter.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
        List<BoardResponseDto> boardList = new ArrayList<>();

        for (Board board : boards) {
            boardList.add(new BoardResponseDto(board));
        }

        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<?> createBoard(String dto, MultipartFile imageFile, Member member) throws IOException {

        if (imageFile == null) {
            System.out.println("no image board creation");
            Board saveWithoutFileBoard = new Board(dto, member);
            boardRepository.save(saveWithoutFileBoard);

            return new ResponseEntity<>("성공적으로 생성 되었습니다(보드 추가시)", HttpStatus.OK);
        }

        Board board = Board.builder()
                .boardContent(dto)
                .member(member)
                .imageFile(s3Uploader.upload(imageFile, "member"))
                .build();
        boardRepository.save(board);

        return new ResponseEntity<>("성공적으로 게시 되었습니다", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> deleteBoard(Long boardId, Member member) {

        Optional<Board> board = boardRepository.findById(boardId);
        System.out.println("in board service board = " + board);

        if (board.isEmpty()) {
            return exceptionHandler.handleApiRequestException(new IllegalArgumentException("존재 하지 않는 게시글 입니다"));
        } else if (board.get().getMember().getMemberId() != member.getMemberId()) {
            return exceptionHandler.handleApiRequestException(new IllegalArgumentException("작성자가 다릅니다"));
        }

        if (board.get().getImageFile() == null) {

        } else {
            int sliceNum = board.get().getImageFile().lastIndexOf("/", board.get().getImageFile().lastIndexOf("/") - 1);

            s3Uploader.deleteFile(board.get().getImageFile().substring(sliceNum + 1));
        }

        boardRepository.deleteById(boardId);

        return new ResponseEntity<>("삭제가 완료 되었습니다", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> getBoard(Long boardId) {

        Board board = boardRepository.findById(boardId).orElse(null);

        if (board == null) {
            return exceptionHandler.handleApiRequestException(new IllegalArgumentException("게시글이 존재하지 않습니다"));
        }

        List<CommentRequestDto> commentList = new ArrayList<>();
        List<Comment> comments = commentRepository.findCommentByBoard_BoardId(boardId);

        for (Comment comment : comments) {
            CommentRequestDto dto = new CommentRequestDto(comment);
            commentList.add(dto);
        }

        BoardResponseDto dto = new BoardResponseDto(board, commentList);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> getBoardInfiniteScroll(int page, int size, String sortBy, boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        boardRepository.findAllByOrderByBoardId(pageable);

        return new ResponseEntity<>("스크롤 완료", HttpStatus.OK);
    }

}

//    수정 기능 사용 안함.

//    @Transactional
//    public ResponseEntity<?> updateBoard(BoardRequestDto dto, Long boardId, Member member) throws IOException {
//
//        Board board = boardRepository.findById(boardId).orElse(null);
//
//        if (board == null) {
//            return exceptionHandler.handleApiRequestException(new IllegalArgumentException("존재 하지 않는 게시글 입니다"));
//        } else if (board.getMember().getMemberId() != member.getMemberId()) {
//            return exceptionHandler.handleApiRequestException(new IllegalArgumentException("작성자가 다릅니다"));
//        }
//
//        if (dto.getImageFile() == null) {
//            board.update(dto);
//            return new ResponseEntity<>("수정 굳", HttpStatus.OK);
//        } else {
//
//            int sliceNum = board.getImageFile().lastIndexOf("/", board.getImageFile().lastIndexOf("/") - 1);
//            s3Uploader.deleteFile(board.getImageFile().substring(sliceNum + 1));
//
//
//            board.update(dto, s3Uploader.upload(dto.getImageFile(), "member"));
//
//        }
//
//        return new ResponseEntity<>("수정이 완료 되었습니다", HttpStatus.OK);
//
//    }
