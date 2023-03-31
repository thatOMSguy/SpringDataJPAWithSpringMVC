package com.springrestmvcproject.spring6restmvc.controller;

import com.springrestmvcproject.spring6restmvc.entities.Beer;
import com.springrestmvcproject.spring6restmvc.mappers.BeerMapper;
import com.springrestmvcproject.spring6restmvc.model.BeerDTO;
import com.springrestmvcproject.spring6restmvc.repositories.BeerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BeerControllerIntegTest {

    @Autowired
    BeerController beerController;
    @Autowired
    BeerRepository beerRepository;
    @Autowired
    BeerMapper beerMapper;

    @Test
    void testListBeers() {
        List<BeerDTO> beerDTOList = beerController.listBeers();
        assertThat(beerDTOList.size()).isEqualTo(3);
    }


    //the below test actually alter the in mem data base by deleting all entries in dtolist, and since sprint boot test
    //runs alphabetically so the testEmptyList runs but the testListBeer fails since it now does not have any fata in dtoList
    //what we need to do in this case is mark that operation on db done here is transactional and rollback the DB operation once
    //test run is completed
    //Alsp by default SB does rollback for operation that happens as part of transaction, but since we are dealing with controller layer here
    //it does not do so.
    @Rollback
    @Transactional
    @Test
    void testEmptyList() {

        beerRepository.deleteAll();

        List<BeerDTO> beerDTOList = beerController.listBeers();
        assertThat(beerDTOList.size()).isEqualTo(0);

    }

    @Test
    void testGetBeerById() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerController.getBeerById(beer.getId());

        assertThat(beerDTO).isNotNull();
    }

    @Test
    void testBeerIdNotFound() {

        //using lambda
        assertThrows(NotFoundException.class, () -> {
            beerController.getBeerById(UUID.randomUUID());
        });

/**
 //without using lambda
 */
        /*assertThrows(NotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                beerController.getBeerById(UUID.randomUUID());
            }
        });*/


    }


    @Transactional
    @Rollback
    @Test
    void saveNewBeerTest() {
        BeerDTO beerDTO = BeerDTO.builder().beerName("New Beer").build();

        ResponseEntity responseEntity = beerController.handlePost(beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");

        UUID savedUUID = UUID.fromString(locationUUID[4]);
        Beer beer = beerRepository.findById(savedUUID).get();
        assertThat(beer).isNotNull();
    }


    @Rollback
    @Transactional
    @Test
    void updateBeerByIdTest() {

        Beer beer = beerRepository.findAll().get(0);

        BeerDTO beerDTO = beerMapper.beerTobeerDTO(beer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);

        final String beerName = beer.getBeerName() + " NEW";
        beerDTO.setBeerName(beerName);

        ResponseEntity responseEntity = beerController.updateById(beer.getId(), beerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer updateBeer = beerRepository.findById(beer.getId()).get();

        assertThat(updateBeer.getBeerName()).isEqualTo(beerName);

    }

    @Test
    void testUpdateBeerNotFound() {
        assertThrows(NotFoundException.class, () ->
        {
            beerController.updateById(UUID.randomUUID(), BeerDTO.builder().build());
        });
    }

    @Rollback
    @Transactional
    @Test
    void deleteBeerById() {

        Beer beer = beerRepository.findAll().get(0);
        ResponseEntity responseEntity = beerController.deleteById(beer.getId());

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(beerRepository.findById(beer.getId())).isEmpty();


    }

    @Test
    void testDeleteByIdNotFound(){
        assertThrows(NotFoundException.class,()->{
            beerController.deleteById(UUID.randomUUID());
        });
    }


}