package me.afmiguez.alura.alurachallengebackend.controllers;

import lombok.RequiredArgsConstructor;
import me.afmiguez.alura.alurachallengebackend.services.VideoServiceI;
import me.afmiguez.alura.alurachallengebackend.models.Video;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoServiceI videoService;

    @GetMapping
    public ResponseEntity<List<Video>> listAllVideos(){
        return ResponseEntity.ok(videoService.findAllVideos());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Video> findVideoById(@PathVariable Long id){
        Video video=videoService.findVideoById(id);
        if(video!=null){
            return ResponseEntity.ok(video);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Video> addNewVideo(@Valid @RequestBody Video video){
            return ResponseEntity.ok(videoService.addNewVideo(video));
    }

    @PutMapping
    public ResponseEntity<Video> updateVideo(@Valid @RequestBody Video video){
        return ResponseEntity.ok(videoService.updateVideo(video));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Video> removeVideo(@PathVariable Long id){
        if(videoService.deleteVideoById(id)){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
