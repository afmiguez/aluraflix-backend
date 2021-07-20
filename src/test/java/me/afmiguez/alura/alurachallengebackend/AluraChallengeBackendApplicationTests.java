package me.afmiguez.alura.alurachallengebackend;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.afmiguez.alura.alurachallengebackend.models.Video;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AluraChallengeBackendApplicationTests {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void contextLoads() throws Exception {

        //garante que está vazio
        String videosResponse= mockMvc.perform(get("/videos")).andReturn().getResponse().getContentAsString();
        List<Video> videos=objectMapper.readValue(videosResponse,List.class);
        assertTrue(videos.isEmpty());

        //garante que não há video com id=1000
        assertTrue(mockMvc.perform(get("/videos/1000")).andReturn().getResponse().getContentAsString().isEmpty());


        Video video= Video.builder().titulo("titulo").descricao("descrição").url("http://url.com").build();

        //cria 1 video
        String validVideoResponseJson=mockMvc.perform(post("/videos").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(video)))
                .andReturn().getResponse().getContentAsString();

        Video createdVideo=objectMapper.readValue(validVideoResponseJson,Video.class);

        assertNotNull(createdVideo);

        Long createdVideoId=createdVideo.getId();

        //verifica que consegue buscar o video criado
        assertFalse(mockMvc.perform(get("/videos/"+createdVideoId)).andReturn().getResponse().getContentAsString().isEmpty());

        createdVideo.setDescricao("");

        //garante que não pode modificar video com campo invalido
        assertTrue(mockMvc.perform(put("/videos").content(objectMapper.writeValueAsString(createdVideo)).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString().isEmpty());


        String novaDescricao="nova descrição";
        createdVideo.setDescricao(novaDescricao);

        //garante que consegue modificar video com campo validado
        String videoModificadoJson=mockMvc.perform(put("/videos").content(objectMapper.writeValueAsString(createdVideo)).contentType(MediaType.APPLICATION_JSON)).andReturn().getResponse().getContentAsString();
        assertFalse(videoModificadoJson.isEmpty());
        Video videoModificado=objectMapper.readValue(videoModificadoJson,Video.class);
        assertEquals(videoModificado.getDescricao(),novaDescricao);


        //remove o video criado
        assertTrue(mockMvc.perform(delete("/videos/"+createdVideoId)).andExpect(status().isNoContent()).andReturn().getResponse().getContentAsString().isEmpty());

        //tenta remover video que não existe
        assertTrue(mockMvc.perform(delete("/videos/"+createdVideoId)).andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString().isEmpty());

        //garante que o video não existe mais
        assertTrue(mockMvc.perform(get("/videos/"+createdVideoId)).andReturn().getResponse().getContentAsString().isEmpty());


    }

}
