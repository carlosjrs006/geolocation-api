package br.com.desafio.geolocation.geolocation.client;

import br.com.desafio.geolocation.geolocation.domain.ApiGoogleGeocodingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "apiGoogleLocalizationClient" , url = "${url.api.google}")
public interface ApiGoogleLocalizationClient {

    @RequestMapping(method = RequestMethod.GET, value = "")
    ApiGoogleGeocodingResponse findLocalizationByLongituteAndLatitude(
            @RequestParam("latlng") String latlng,
            @RequestParam("key") String apiKey);
}
