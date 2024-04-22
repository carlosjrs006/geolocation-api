package br.com.desafio.geolocation.geolocation.controller;

import br.com.desafio.geolocation.geolocation.domain.InfoLocalizationResponse;
import br.com.desafio.geolocation.geolocation.domain.InfoLocalizationResquest;
import br.com.desafio.geolocation.geolocation.services.GeolocalizationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/geolocation")
public class GeolocalizationController {

    @Autowired
    private GeolocalizationService geolocalizationService;

    @PostMapping
    private ResponseEntity<?> saveLocalizacaoGoogle(@RequestBody @Valid InfoLocalizationResquest infoLocalizationResquest){
        try{
           return ResponseEntity.ok().body(geolocalizationService.saveLocalizacaoGoogle(infoLocalizationResquest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @GetMapping("")
    public ResponseEntity<Page<?>> getHistoricoConsultas(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        try {
            return ResponseEntity.ok().body( geolocalizationService.getHistoricoConsultas(pageNumber, pageSize));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
