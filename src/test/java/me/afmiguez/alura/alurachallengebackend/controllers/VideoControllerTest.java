package me.afmiguez.alura.alurachallengebackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.afmiguez.alura.alurachallengebackend.models.Video;
import me.afmiguez.alura.alurachallengebackend.services.VideoServiceI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VideoController.class)
class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VideoServiceI videoService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void listAllVideos() throws Exception {
        assertNotNull(mockMvc);
        assertNotNull(videoService);

        mockMvc.perform(get("/videos"))
                .andExpect(status().isOk());

    }

    @Test
    public void getVideoById() throws Exception {
        Long id=1L;
        when(videoService.findVideoById(id)).thenReturn(Video.builder().build());

        mockMvc.perform(get("/videos/1"))
                .andExpect(status().isOk());

        String response=mockMvc.perform(get("/videos/10"))
                .andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();

        assertTrue(response.isEmpty());


    }

    @Test
    public void addNewVideo() throws Exception {

        Video validVideo=Video.builder().titulo("titulo").descricao("descrição").url("http://url.com").build();

        when(videoService.addNewVideo(validVideo)).thenReturn(validVideo);

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
        String response=mockMvc.perform(post("/videos").content(objectMapper.writeValueAsString(invalidVideo)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn().getResponse().getContentAsString();


        assertTrue(response.isEmpty());

    }

    @Test
    public void changeVideo() throws Exception {
        //Video originalVideo=Video.builder().titulo("titulo").descricao("descrição").url("http://url.com").id(1L).build();
        Video changedVideo=Video.builder().titulo("titulo").descricao("descrição").url("http://url.com").id(1L).build();

        when(videoService.updateVideo(changedVideo)).thenReturn(changedVideo);

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
        when(videoService.deleteVideoById(1L)).thenReturn(true);

        mockMvc.perform(delete("/videos/1"))
                .andExpect(status().isNoContent());

        String response=mockMvc.perform(delete("/videos/10"))
                .andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();

        assertTrue(response.isEmpty());

    }

}