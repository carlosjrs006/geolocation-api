package br.com.desafio.geolocation.geolocation.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InfoLocalizationResponse {

    private Double longitude;
    private Double latitude;
    private String results;
    private String dateTime;
}
