package io.aws.airboss.airports;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import io.aws.airboss.airports.exceptions.AirportNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional(readOnly = true)
public class AirportServiceImpl implements AirportService {
    
    @Autowired
    private AirportRepository airportRepo;
    
    @Override
    public Airport getAirportById(String airportId) {
        return airportRepo.findById(airportId).orElseThrow();
    }
    
    @Override
    public List<Airport> getAllAirports() {
        return airportRepo.findAll();
    }
    
    @Override
    @Transactional
    public Airport createAirport(Airport airport) {
        return airportRepo.save(airport);
    }
    
    @Override
    public Airport updateAirport(Long id, Airport airport) {
        return null;
    }
    
    @Override
    public void deleteAirport(Long id) {
    
    }
    
    
    @Override
    public void flush() {
    
    }
    
    @Override
    public <S extends Airport> S saveAndFlush(S entity) {
        return null;
    }
    
    @Override
    public <S extends Airport> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }
    
    @Override
    public void deleteAllInBatch(Iterable<Airport> entities) {
    
    }
    
    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {
    
    }
    
    @Override
    public void deleteAllInBatch() {
    
    }
    
    @Override
    public Airport getOne(Long aLong) {
        return null;
    }
    
    @Override
    public Airport getById(Long aLong) {
        return null;
    }
    
    @Override
    public Airport getReferenceById(Long aLong) {
        return null;
    }
    
    @Override
    public <S extends Airport> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }
    
    @Override
    public <S extends Airport> List<S> findAll(Example<S> example) {
        return List.of();
    }
    
    @Override
    public <S extends Airport> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }
    
    @Override
    public <S extends Airport> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }
    
    @Override
    public <S extends Airport> long count(Example<S> example) {
        return 0;
    }
    
    @Override
    public <S extends Airport> boolean exists(Example<S> example) {
        return false;
    }
    
    @Override
    public <S extends Airport, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
    
    @Override
    public <S extends Airport> S save(S entity) {
        return null;
    }
    
    @Override
    public <S extends Airport> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }
    
    @Override
    public Optional<Airport> findById(Long aLong) {
        return Optional.empty();
    }
    
    @Override
    public boolean existsById(Long aLong) {
        return false;
    }
    
    @Override
    public List<Airport> findAll() {
        return List.of();
    }
    
    @Override
    public List<Airport> findAllById(Iterable<Long> longs) {
        return List.of();
    }
    
    @Override
    public long count() {
        return 0;
    }
    
    @Override
    public void deleteById(Long aLong) {
    
    }
    
    @Override
    public void delete(Airport entity) {
    
    }
    
    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
    
    }
    
    @Override
    public void deleteAll(Iterable<? extends Airport> entities) {
    
    }
    
    @Override
    public void deleteAll() {
    
    }
    
    @Override
    public List<Airport> findAll(Sort sort) {
        return List.of();
    }
    
    @Override
    public Page<Airport> findAll(Pageable pageable) {
        return null;
    }
}