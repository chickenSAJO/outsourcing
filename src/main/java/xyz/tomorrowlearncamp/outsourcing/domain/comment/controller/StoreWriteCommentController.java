package xyz.tomorrowlearncamp.outsourcing.domain.comment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comments")
public class StoreWriteCommentController {

    @GetMapping("")
    public ResponseEntity<Void> swagger_test() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
