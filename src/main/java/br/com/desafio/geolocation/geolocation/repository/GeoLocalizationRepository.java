package br.com.desafio.geolocation.geolocation.repository;

import br.com.desafio.geolocation.geolocation.domain.GeoLocalization;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoLocalizationRepository extends MongoRepository<GeoLocalization, String> {
}
