package com.travel_agency.model.services;

import com.travel_agency.model.entity.Hotel;
import com.travel_agency.utils.exceptions.DAOException;
import com.travel_agency.utils.exceptions.ValidationException;
import com.travel_agency.model.DB.DAO.OfferDAO;
import com.travel_agency.model.DTO.OfferDTO;
import com.travel_agency.model.entity.Offer;
import com.travel_agency.utils.Constants.SORTING_BY;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OfferServiceTest {
    @Mock
    private OfferDAO<Offer> dao;

    @InjectMocks
    private OfferService service;

    private OfferDTO offerDTO;
    Hotel hotel;
    private Offer offer;

    @BeforeEach
    void setUp() {
        hotel = new Hotel(0,"hotel","hotelType","city");
        offerDTO = new OfferDTO("code", "type", "hotel", "hotelType", "city", 100, 0.15, false, true, 150);
        offer = new Offer(0,"code", hotel,"type", 100,0.15,false,true,150);
    }

    @Test
    void testGetAllOffers() throws DAOException {
        List<Offer> offers = new ArrayList<>();
        offers.add(offer);
        offers.add(new Offer(0,"1234", hotel,"type", 100,0.15,false,true,150));

        when(dao.readAll(0, 10,false)).thenReturn(offers);

        List<OfferDTO> result = service.getAllOffers(0, 10,false, SORTING_BY.NONE);

        verify(dao).readAll(0, 10,false);
        assertEquals(2, result.size());
        assertEquals(offerDTO, result.get(0));
    }

    @Test
    void testGetAllHotOffers() throws DAOException {
        List<Offer> offers = new ArrayList<>();
        offers.add(new Offer(0,"1234", hotel,"type", 100,0.15,false,true,150));

        when(dao.readAll(0, 10,true)).thenReturn(offers);

        List<OfferDTO> result = service.getAllOffers(0, 10,true, SORTING_BY.NONE);

        verify(dao).readAll(0, 10,true);

        assertEquals(1, result.size());
        assertEquals(offers.get(0).getCode(), result.get(0).getCode());
    }

    @Test
    void testGetAllOffersSorted() throws DAOException {
        List<Offer> offers = new ArrayList<>();
        offers.add(offer);
        offers.add(new Offer(0,"1234", hotel,"type", 100,0.15,false,true,150));

        when(dao.readAllSorted(0, 10,SORTING_BY.OFFER_TYPE)).thenReturn(offers);

        List<OfferDTO> result = service.getAllOffers(0, 10,false, SORTING_BY.OFFER_TYPE);

        verify(dao).readAllSorted(0, 10,SORTING_BY.OFFER_TYPE);
        assertEquals(2, result.size());
        assertEquals(offerDTO, result.get(0));
    }

    @Test
    void testGetAllOffers_DAOException() throws DAOException {
        when(dao.readAll(0, 10,false)).thenThrow(DAOException.class);

        List<OfferDTO> result = service.getAllOffers(0, 10,false, SORTING_BY.NONE);

        verify(dao).readAll(0, 10,false);
        assertEquals(0, result.size());
    }

    @Test
    void testGetOffer() throws DAOException {
        when(dao.read("code")).thenReturn(offer);

        OfferDTO result = service.getOffer("code");

        assertEquals(offerDTO, result);
    }

    @Test
    void testGetOffer_DAOException() throws DAOException {
        when(dao.read("code")).thenThrow(DAOException.class);

        OfferDTO result = service.getOffer("code");

        assertEquals(new OfferDTO(), result);
    }

    @Test
    void changeOfferIsHot() throws DAOException {
        offer.setHot(true);
        service.updateOfferIsHot(offerDTO);
        verify(dao).update(offer, true);
    }

    @Test
    void updateOffer() throws DAOException {
        when(dao.delete(offerDTO.getCode())).thenReturn(true);
        when(dao.create(offer)).thenReturn(true);

        boolean result = service.updateOffer(offerDTO);

        verify(dao).delete(offerDTO.getCode());
        verify(dao).create(offer);
        assertTrue(result);
    }

    @Test
    void updateOffer_ValidationException() {
        offerDTO.setDiscount(0.5);

        assertThrows(ValidationException.class, () ->
                service.updateOffer(offerDTO));
    }

    @Test
    void createOffer() throws DAOException {
        when(dao.create(offer)).thenReturn(true);

        boolean result = service.createOffer(offerDTO);

        verify(dao).create(offer);
        assertTrue(result);
    }


    @Test
    void createOffer_ValidationException() {
        offerDTO.setDiscount(0.5);

        assertThrows(ValidationException.class, () ->
                service.createOffer(offerDTO));
    }


}