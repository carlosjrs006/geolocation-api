package br.com.desafio.geolocation.geolocation.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "localizacao")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeoLocalization {

    @Id
    private String id;
    private Double longitude;
    private Double latitude;
    private String enderecoLocalizado;
    private LocalDateTime dataConsulta;
}
