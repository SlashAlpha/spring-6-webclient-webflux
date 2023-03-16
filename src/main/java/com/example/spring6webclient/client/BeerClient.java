package com.example.spring6webclient.client;

import com.example.spring6webclient.model.BeerDTO;
import com.fasterxml.jackson.databind.JsonNode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface BeerClient {

    Flux<String> listBeers();

    Flux<Map> listBeer();

    Flux<JsonNode> listBeerJsonNode();

    Flux<BeerDTO> listBeerPOJO();

    Mono<BeerDTO> getBeerById(String id);

    Flux<BeerDTO> getBeerByBeerStyle(String beerStyle);

    Mono<BeerDTO> createBeer(BeerDTO beerDTO);

    Mono<BeerDTO> updateBeer(BeerDTO beerDTO);

    Mono<BeerDTO> patchBeer(BeerDTO dto);


    Mono<Void> deleteBeer(BeerDTO dto);
}
