package me.afmiguez.alura.alurachallengebackend.services;

import me.afmiguez.alura.alurachallengebackend.models.Video;

import java.util.List;

public interface VideoServiceI {
    Video addNewVideo(Video video);
    Video findVideoById(Long id);
    List<Video> findAllVideos();
    Video updateVideo(Video video);
    boolean deleteVideoById(Long id);
}
