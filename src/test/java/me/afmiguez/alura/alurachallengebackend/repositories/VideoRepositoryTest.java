package me.afmiguez.alura.alurachallengebackend.repositories;

import me.afmiguez.alura.alurachallengebackend.models.Video;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VideoRepositoryTest {

    @Autowired
    private VideoRepository videoRepository;


    @Test
    public void saveVideo(){

        assertNotNull(videoRepository);

        assertTrue(videoRepository.findAll().isEmpty());

        videoRepository.saveAndFlush(Video.builder().titulo("titulo").descricao("descrição").url("http://url.com").build());

        assertFalse(videoRepository.findAll().isEmpty());

    }

    @Test
    public void errorSaveVideo(){

        try {
            videoRepository.save(Video.builder().titulo("titulo").descricao("descrição").url("url").build());
            assertTrue(false);
        }catch (Exception exception){
            assertTrue(true);
        }

        try {
            videoRepository.save(Video.builder().titulo("titulo").descricao("descrição").url("").build());
            assertTrue(false);
        }catch (Exception exception){
            assertTrue(true);
        }

        try {
            videoRepository.save(Video.builder().titulo("titulo").descricao("descrição").build());
            assertTrue(false);
        }catch (Exception exception){
            assertTrue(true);
        }

        try {
            videoRepository.save(Video.builder().titulo("titulo").url("http://url.com").build());
            assertTrue(false);
        }catch (Exception exception){
            assertTrue(true);
        }
        try {
            videoRepository.save(Video.builder().titulo("titulo").descricao("").url("http://url.com").build());
            assertTrue(false);
        }catch (Exception exception){
            assertTrue(true);
        }
        try {
            videoRepository.save(Video.builder().titulo("").descricao("").url("http://url.com").build());
            assertTrue(false);
        }catch (Exception exception){
            assertTrue(true);
        }
        try {
            videoRepository.save(Video.builder().descricao("").url("http://url.com").build());
            assertTrue(false);
        }catch (Exception exception){
            assertTrue(true);
        }
    }

}