package me.afmiguez.alura.alurachallengebackend.controllers;

import lombok.RequiredArgsConstructor;
import me.afmiguez.alura.alurachallengebackend.models.Video;
import me.afmiguez.alura.alurachallengebackend.repositories.VideoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoRepository videoRepository;

    @GetMapping
    public ResponseEntity<List<Video>> listAllVideos(){
        return ResponseEntity.ok(videoRepository.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Video> listAllVideos(@PathVariable Long id){
        if(videoRepository.existsById(id)){
            return ResponseEntity.ok(videoRepository.getById(id));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Video> addNewVideo(@Valid @RequestBody Video video){

            return ResponseEntity.ok(videoRepository.save(video));

    }

    @PutMapping
    public ResponseEntity<Video> updateVideo(@Valid @RequestBody Video video){
        Video videoFromdb=videoRepository.getById(video.getId());
        videoFromdb.setDescricao(video.getDescricao());
        videoFromdb.setTitulo(video.getTitulo());
        videoFromdb.setUrl(video.getUrl());
        return ResponseEntity.ok(videoRepository.save(videoFromdb));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Video> removeVideo(@PathVariable Long id){
        if(videoRepository.existsById(id)){
            videoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
