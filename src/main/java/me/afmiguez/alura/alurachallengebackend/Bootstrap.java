package me.afmiguez.alura.alurachallengebackend;

import lombok.RequiredArgsConstructor;
import me.afmiguez.alura.alurachallengebackend.models.Video;
import me.afmiguez.alura.alurachallengebackend.repositories.VideoRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Profile({"dev","prod"})
@RequiredArgsConstructor
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final VideoRepository videoRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        videoRepository.save(Video.builder().titulo("titulo").url("http://url.com").descricao("descrição").build());
    }
}
