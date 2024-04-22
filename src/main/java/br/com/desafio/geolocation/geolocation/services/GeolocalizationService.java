package br.com.desafio.geolocation.geolocation.services;

import br.com.desafio.geolocation.geolocation.domain.InfoLocalizationResponse;
import br.com.desafio.geolocation.geolocation.domain.InfoLocalizationResquest;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GeolocalizationService {

    InfoLocalizationResponse saveLocalizacaoGoogle(InfoLocalizationResquest infoLocalizationResquest) throws BadRequestException;

    Page<InfoLocalizationResponse> getHistoricoConsultas(int pageNumber, int pageSize);
}
