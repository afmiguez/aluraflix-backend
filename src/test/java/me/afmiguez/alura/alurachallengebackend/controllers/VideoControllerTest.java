package me.afmiguez.alura.alurachallengebackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.afmiguez.alura.alurachallengebackend.models.Video;
import me.afmiguez.alura.alurachallengebackend.repositories.VideoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VideoController.class)
class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VideoRepository videoRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void listAllVideos() throws Exception {
        assertNotNull(mockMvc);
        assertNotNull(videoRepository);

        mockMvc.perform(get("/videos"))
                .andExpect(status().isOk());

    }

    @Test
    public void getVideoById() throws Exception {
        Long id=1L;

        when(videoRepository.existsById(id)).thenReturn(true);
        when(videoRepository.getById(id)).thenReturn(Video.builder().build());

        mockMvc.perform(get("/videos/1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/videos/10"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void addNewVideo() throws Exception {

        Video validVideo=Video.builder().titulo("titulo").descricao("descrição").url("http://url.com").build();

        when(videoRepository.save(validVideo)).thenReturn(validVideo);

        mockMvc.perform(post("/videos").content(objectMapper.writeValueAsString(validVideo)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Video invalidVideo= Video.builder().titulo("titulo").descricao("descrição").url("url").build();
        mockMvc.perform(post("/videos").content(objectMapper.writeValueAsString(invalidVideo)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        invalidVideo= Video.builder().titulo("titulo").descricao("descrição").url("").build();
        mockMvc.perform(post("/videos").content(objectMapper.writeValueAsString(invalidVideo)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        invalidVideo= Video.builder().titulo("titulo").descricao("descrição").build();
        mockMvc.perform(post("/videos").content(objectMapper.writeValueAsString(invalidVideo)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        invalidVideo= Video.builder().titulo("titulo").descricao("").url("http://url.com").build();
        mockMvc.perform(post("/videos").content(objectMapper.writeValueAsString(invalidVideo)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        invalidVideo= Video.builder().titulo("titulo").url("http://url.com").build();
        mockMvc.perform(post("/videos").content(objectMapper.writeValueAsString(invalidVideo)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        invalidVideo= Video.builder().titulo("").descricao("descricao").url("http://url.com").build();
        mockMvc.perform(post("/videos").content(objectMapper.writeValueAsString(invalidVideo)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        invalidVideo= Video.builder().descricao("descricao").url("http://url.com").build();
        mockMvc.perform(post("/videos").content(objectMapper.writeValueAsString(invalidVideo)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void changeVideo() throws Exception {
        Video originalVideo=Video.builder().titulo("titulo").descricao("descrição").url("http://url.com").id(1L).build();
        Video changedVideo=Video.builder().titulo("titulo").descricao("descrição").url("http://url.com").id(1L).build();

        when(videoRepository.getById(1L)).thenReturn(originalVideo);
        when(videoRepository.save(changedVideo)).thenReturn(changedVideo);

        String validResponseJson=mockMvc.perform(put("/videos").content(objectMapper.writeValueAsString(changedVideo)).contentType(MediaType.APPLICATION_JSON).characterEncoding("utf8"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertNotNull(validResponseJson);
        assertFalse(validResponseJson.isEmpty());
        Video returnedFromApi=objectMapper.readValue(validResponseJson,Video.class);
        assertEquals(changedVideo,returnedFromApi);

    }

    @Test
    public void removeVideo() throws Exception {
        when(videoRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/videos/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(delete("/videos/10"))
                .andExpect(status().isNotFound());

    }

}