package me.afmiguez.alura.alurachallengebackend.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class VideoTest {

    @Test
    void testEquals() {

        Video video1= Video.builder().build();
        Video video2=video1;

        assertEquals(video1,video2);

        video1.setTitulo("titulo");
        video2= Video.builder().titulo("titulo").build();

        assertEquals(video1,video2);

        video2.setTitulo("titulo2");

        assertNotEquals(video1,video2);

        assertNotEquals(video1,"video2");
        assertNotEquals(video1,null);

        assertFalse(Video.builder().titulo("titulo").toString().isEmpty());

    }

    @Test
    void testHashCode() {
        assertNotEquals(0,Video.builder().titulo("titulo").build().hashCode());
    }
}