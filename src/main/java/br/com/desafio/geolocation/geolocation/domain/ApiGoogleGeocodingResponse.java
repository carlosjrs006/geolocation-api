package br.com.desafio.geolocation.geolocation.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiGoogleGeocodingResponse {

    private List<GeocodingResult> results;
}
