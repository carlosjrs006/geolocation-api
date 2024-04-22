package br.com.desafio.geolocation.geolocation.services.impl;

import br.com.desafio.geolocation.geolocation.client.ApiGoogleLocalizationClient;
import br.com.desafio.geolocation.geolocation.domain.ApiGoogleGeocodingResponse;
import br.com.desafio.geolocation.geolocation.domain.GeoLocalization;
import br.com.desafio.geolocation.geolocation.domain.InfoLocalizationResponse;
import br.com.desafio.geolocation.geolocation.domain.InfoLocalizationResquest;
import br.com.desafio.geolocation.geolocation.repository.GeoLocalizationRepository;
import br.com.desafio.geolocation.geolocation.services.GeolocalizationService;
import org.apache.catalina.util.StringUtil;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
public class GeolocalizationServicesImpl implements GeolocalizationService {

    @Autowired
    private GeoLocalizationRepository geoLocalizationRepository;

    @Autowired
    private ApiGoogleLocalizationClient apiGoogleLocalizationClient;

    @Value("${secret.key.google}")
    private String apikey;


    @Override
    public InfoLocalizationResponse saveLocalizacaoGoogle(InfoLocalizationResquest infoLocalizationResquest) throws BadRequestException {

        if(Objects.isNull(infoLocalizationResquest)){
            throw new BadRequestException("Error ao passar Longitude e Latitude");
        }

        try {
            String longitude = String.valueOf(infoLocalizationResquest.getLongitude());
            String latitude = String.valueOf(infoLocalizationResquest.getLatitude());
            String latLng =  latitude + "," + longitude;
            ApiGoogleGeocodingResponse localizationByLongituteAndLatitude = apiGoogleLocalizationClient.findLocalizationByLongituteAndLatitude(latLng, apikey);
            System.out.println("localizationByLongituteAndLatitude: " + localizationByLongituteAndLatitude);

            salvarConsultaRepository(localizationByLongituteAndLatitude,infoLocalizationResquest);

            return montarViewReponse(localizationByLongituteAndLatitude,infoLocalizationResquest);
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao chamar a API do Google Geocoding", ex);
        }
    }

    @Override
    public Page<InfoLocalizationResponse> getHistoricoConsultas(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("dataConsulta").descending());
        Page<GeoLocalization> allHistoric = geoLocalizationRepository.findAll(pageable);
        return allHistoric.map(this::montarViewReponseList);
    }

    private void salvarConsultaRepository(ApiGoogleGeocodingResponse localizationByLongituteAndLatitude, InfoLocalizationResquest infoLocalizationResquest){

        GeoLocalization geoLocalization = new GeoLocalization();
        geoLocalization.setLongitude(infoLocalizationResquest.getLongitude());
        geoLocalization.setLatitude(infoLocalizationResquest.getLatitude());
        geoLocalization.setEnderecoLocalizado(localizationByLongituteAndLatitude.getResults().get(0).getFormatted_address());
        geoLocalization.setDataConsulta(LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toLocalDateTime());
        try{
            geoLocalizationRepository.save(geoLocalization);

        }catch (Exception e){
            throw new RuntimeException("Error ao salvar repository");
        }

    }

   private InfoLocalizationResponse montarViewReponseList(GeoLocalization geoLocalizations){

        return InfoLocalizationResponse.builder()
                .longitude(geoLocalizations.getLongitude())
                .latitude(geoLocalizations.getLatitude())
                .results(geoLocalizations.getEnderecoLocalizado())
                .dateTime(formatDate(geoLocalizations.getDataConsulta()))
                .build();
    }


    private String formatDate(LocalDateTime dataconsulta){
        ZoneId zonaBrasil = ZoneId.of("America/Sao_Paulo");
        ZonedDateTime zonedDateTime = dataconsulta.atZone(zonaBrasil);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return zonedDateTime.format(formatter);
    }

    private InfoLocalizationResponse montarViewReponse(ApiGoogleGeocodingResponse localizationByLongituteAndLatitude,InfoLocalizationResquest infoLocalizationResquest){
        return InfoLocalizationResponse.builder()
                .longitude(infoLocalizationResquest.getLongitude())
                .latitude(infoLocalizationResquest.getLatitude())
                .results(localizationByLongituteAndLatitude.getResults().get(0).getFormatted_address())
                .build();
    }
}
