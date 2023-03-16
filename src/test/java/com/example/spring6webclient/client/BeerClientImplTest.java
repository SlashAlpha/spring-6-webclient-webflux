package com.example.spring6webclient.client;

import com.example.spring6webclient.model.BeerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;

@SpringBootTest
class BeerClientImplTest {
    @Autowired
    BeerClient beerClient;

    @Test
    void listBeers() {
        AtomicBoolean atomicBoolean=new AtomicBoolean(false);

        beerClient.listBeers().subscribe(response->{
            System.out.println(response);
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void listBeer() {
        AtomicBoolean atomicBoolean=new AtomicBoolean(false);
        beerClient.listBeer().subscribe(response->{
            System.out.println(response);
            atomicBoolean.set(true);

        });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void listBeerJsonNode() {

        AtomicBoolean atomicBoolean=new AtomicBoolean(false);
        beerClient.listBeerJsonNode().subscribe(response->{
            System.out.println(response);
            atomicBoolean.set(true);

        });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void listBeerPOJO() {
        AtomicBoolean atomicBoolean=new AtomicBoolean(false);
        beerClient.listBeerPOJO().subscribe(response->{
            System.out.println(response);
            atomicBoolean.set(true);

        });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void getBeerByIdTest() {
        AtomicBoolean atomicBoolean=new AtomicBoolean(false);
        beerClient.listBeerPOJO()
                .flatMap(
                beerDTO -> beerClient.getBeerById(beerDTO.getId()))
                .subscribe(dto->{
            System.out.println(dto.getBeerName());
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }
    @Test
    void getBeerByIBeerStyleTest() {
        AtomicBoolean atomicBoolean=new AtomicBoolean(false);
         beerClient.getBeerByBeerStyle("Pale Ale")
                .subscribe(dto->{
                    System.out.println(dto.getBeerName());
                    atomicBoolean.set(true);
                });
        await().untilTrue(atomicBoolean);
    }
    @Test
    void testCreateBeer() {

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        BeerDTO newDto = BeerDTO.builder()
                .price(new BigDecimal("10.99"))
                .beerName("Mango Bobs")
                .beerStyle("IPA")
                .quantityOnHand(500)
                .upc("123245")
                .build();

        beerClient.createBeer(newDto)
                .subscribe(dto -> {
                    System.out.println(dto.toString());
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);
    }
    @Test
    void testUpdate() {

        final String NAME = "New Name";

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.listBeerPOJO()
                .next()
                .doOnNext(beerDTO -> beerDTO.setBeerName(NAME))
                .flatMap(dto -> beerClient.updateBeer(dto))
                .subscribe(byIdDto -> {
                    System.out.println(byIdDto.toString());
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);
    }
    @Test
    void testPatch() {

        final String NAME = "New Name";

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.listBeerPOJO()
                .next()
                .doOnNext(beerDTO -> beerDTO.setBeerName(NAME))
                .flatMap(dto -> beerClient.patchBeer(dto))
                .subscribe(byIdDto -> {
                    System.out.println(byIdDto.toString());
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);
    }


    @Test
    void testDelete() {

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerClient.listBeerPOJO()
                .next()
                .flatMap(dto -> beerClient.deleteBeer(dto))
                .doOnSuccess(mt -> atomicBoolean.set(true))
                .subscribe();

        await().untilTrue(atomicBoolean);
    }
}