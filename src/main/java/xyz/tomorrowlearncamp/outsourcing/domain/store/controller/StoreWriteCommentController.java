package xyz.tomorrowlearncamp.outsourcing.domain.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.tomorrowlearncamp.outsourcing.domain.store.service.StoreWriteCommentService;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class StoreWriteCommentController {

    private final StoreWriteCommentService storeWriteCommentService;
}
