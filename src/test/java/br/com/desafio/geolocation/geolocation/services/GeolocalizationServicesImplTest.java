package br.com.desafio.geolocation.geolocation.services;

import br.com.desafio.geolocation.geolocation.client.ApiGoogleLocalizationClient;
import br.com.desafio.geolocation.geolocation.domain.GeoLocalization;
import br.com.desafio.geolocation.geolocation.domain.InfoLocalizationResponse;
import br.com.desafio.geolocation.geolocation.domain.InfoLocalizationResquest;
import br.com.desafio.geolocation.geolocation.repository.GeoLocalizationRepository;
import br.com.desafio.geolocation.geolocation.services.impl.GeolocalizationServicesImpl;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeolocalizationServicesImplTest {

    @Mock
    private GeoLocalizationRepository geoLocalizationRepository;

    @Mock
    private ApiGoogleLocalizationClient apiGoogleLocalizationClient;

    @InjectMocks
    private GeolocalizationServicesImpl geolocalizationService;

    private InfoLocalizationResquest validRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validRequest = new InfoLocalizationResquest(-10.0, -20.0);
    }


    @Test
    void getHistoricoConsultas_ValidPage_Success() {
        // Mocking
        GeoLocalization mockLocalization = new GeoLocalization();
        mockLocalization.setLongitude(10.0);
        mockLocalization.setLatitude(20.0);
        mockLocalization.setEnderecoLocalizado("Mocked Address");
        mockLocalization.setDataConsulta(LocalDateTime.now());

        Page<GeoLocalization> mockPage = new PageImpl<>(List.of(mockLocalization));

        when(geoLocalizationRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        // Test
        Page<InfoLocalizationResponse> resultPage = geolocalizationService.getHistoricoConsultas(0, 10);

        assertFalse(resultPage.isEmpty());
        assertEquals(1, resultPage.getContent().size());

        InfoLocalizationResponse response = resultPage.getContent().get(0);
        assertEquals(10.0, response.getLongitude());
        assertEquals(20.0, response.getLatitude());
        assertEquals("Mocked Address", response.getResults());
    }



    @Test
    void saveLocalizacaoGoogle_NullRequest_ExceptionThrown() {
        // Test
        assertThrows(BadRequestException.class, () -> geolocalizationService.saveLocalizacaoGoogle(null));

        // Verificar se o método save não foi chamado no repository
        verify(geoLocalizationRepository, never()).save(any());
    }

    @Test
    void getHistoricoConsultas_Success() {
        // Mocking
        GeoLocalization mockLocalization = new GeoLocalization();
        mockLocalization.setLongitude(10.0);
        mockLocalization.setLatitude(20.0);
        mockLocalization.setEnderecoLocalizado("Mocked Address");
        mockLocalization.setDataConsulta(LocalDateTime.now());
        Page<GeoLocalization> mockPage = new PageImpl<>(List.of(mockLocalization));


        when(geoLocalizationRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        // Test
        Page<InfoLocalizationResponse> resultPage = geolocalizationService.getHistoricoConsultas(0, 10);

        // Verificar se a página retornada não está vazia
        assertFalse(resultPage.isEmpty());
    }
}
