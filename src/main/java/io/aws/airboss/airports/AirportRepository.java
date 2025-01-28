package io.aws.airboss.airports;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AirportRepository extends JpaRepository<Airport, String> {



}