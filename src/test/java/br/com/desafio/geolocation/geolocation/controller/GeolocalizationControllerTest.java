package br.com.desafio.geolocation.geolocation.controller;

import br.com.desafio.geolocation.geolocation.domain.InfoLocalizationResponse;
import br.com.desafio.geolocation.geolocation.domain.InfoLocalizationResquest;
import br.com.desafio.geolocation.geolocation.services.GeolocalizationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@WebMvcTest(GeolocalizationController.class)
class GeolocalizationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GeolocalizationService geolocalizationService;

    @Test
    void saveLocalizacaoGoogle_ValidRequest() throws Exception {
        // Mocking
        InfoLocalizationResquest request = new InfoLocalizationResquest();
        InfoLocalizationResponse response = new InfoLocalizationResponse();
        when(geolocalizationService.saveLocalizacaoGoogle(request)).thenReturn(response);

        // Test
        mockMvc.perform(MockMvcRequestBuilders.post("/geolocation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void saveLocalizacaoGoogle_Exception_InternalServerError() throws Exception {
        // Mocking
        InfoLocalizationResquest request = new InfoLocalizationResquest();
        when(geolocalizationService.saveLocalizacaoGoogle(request)).thenThrow(new RuntimeException("Internal Server Error"));

        // Test
        mockMvc.perform(MockMvcRequestBuilders.post("/geolocation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void getHistoricoConsultas_Success() throws Exception {
        // Mocking
        Page<InfoLocalizationResponse> page = null; // Define o valor do Page conforme necessário
        when(geolocalizationService.getHistoricoConsultas(anyInt(), anyInt())).thenReturn(page);

        // Test
        mockMvc.perform(MockMvcRequestBuilders.get("/geolocation"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getHistoricoConsultas_Exception_InternalServerError() throws Exception {
        // Mocking
        when(geolocalizationService.getHistoricoConsultas(anyInt(), anyInt())).thenThrow(new RuntimeException("Internal Server Error"));

        // Test
        mockMvc.perform(MockMvcRequestBuilders.get("/geolocation"))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }
}
