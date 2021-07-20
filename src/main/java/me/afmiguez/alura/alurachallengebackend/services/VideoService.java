package me.afmiguez.alura.alurachallengebackend.services;

import lombok.RequiredArgsConstructor;
import me.afmiguez.alura.alurachallengebackend.models.Video;
import me.afmiguez.alura.alurachallengebackend.repositories.VideoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoService implements VideoServiceI {

    private final VideoRepository videoRepository;

    @Override
    public Video addNewVideo(Video video) {
        return videoRepository.save(video);
    }

    @Override
    public Video findVideoById(Long id) {
        return videoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Video> findAllVideos() {
        return videoRepository.findAll();
    }

    @Override
    public Video updateVideo(Video video) {
        Video videoFromdb= videoRepository.getById(video.getId());
        videoFromdb.setDescricao(video.getDescricao());
        videoFromdb.setTitulo(video.getTitulo());
        videoFromdb.setUrl(video.getUrl());
        return videoRepository.save(videoFromdb);
    }

    @Override
    public boolean deleteVideoById(Long id) {
        if(videoRepository.existsById(id)){
            videoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
