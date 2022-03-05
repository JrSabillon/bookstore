package com.test.bookstore.controllers;

import com.test.bookstore.models.LikeModel;
import com.test.bookstore.models.POJO.LikePojo;
import com.test.bookstore.services.LikeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
public class LikeController {
    @Autowired
    LikeService likeService;

    @PostMapping()
    public ResponseEntity<LikeModel> saveLike(@RequestBody LikePojo like){
        LikeModel savedLike = likeService.saveLike(like);

        if(savedLike == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(savedLike, HttpStatus.CREATED);
    }
}
