package br.com.desafio.geolocation.geolocation.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoLocalizationResquest {

    @NotNull(message = "longitude obrigatorio, por favor preencha!")
    private Double longitude;

    @NotNull(message = "latitude obrigatorio, por favor preencha!")
    private Double latitude;
}
