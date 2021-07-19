package me.afmiguez.alura.alurachallengebackend.repositories;

import me.afmiguez.alura.alurachallengebackend.models.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video,Long> {
}
