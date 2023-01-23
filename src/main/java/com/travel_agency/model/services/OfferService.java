package com.travel_agency.model.services;

import com.travel_agency.model.DB.DAO.OfferDAO;
import com.travel_agency.model.DB.DAO.impl.MySQL.MySQLOfferDAO;
import com.travel_agency.exceptions.DAOException;
import com.travel_agency.exceptions.ValidationException;
import com.travel_agency.model.entity.Offer;
import com.travel_agency.model.DTO.OfferDTO;
import com.travel_agency.utils.Constants.SORTING_BY;
import com.travel_agency.utils.Constants.ValidationMessageConstants;
import com.travel_agency.utils.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OfferService {
    private final OfferDAO<Offer,String> dao;
    private static final Logger logger = LogManager.getLogger(OfferService.class);

    public OfferService(MySQLOfferDAO dao) {
        this.dao = dao;
    }

    public List<OfferDTO> getAllOffers(int offset, int numOfRecords, boolean onlyHot, SORTING_BY sortingBy) {
        List<Offer> offers;
        try {
            if(sortingBy == SORTING_BY.NONE) {
                offers = dao.readAll(offset, numOfRecords, onlyHot);
            } else{
                offers = dao.readAllSorted(offset, numOfRecords, sortingBy);
            }
        } catch (DAOException e) {
            logger.error("Unable to read offers: " + e.getMessage(), e);
            return new ArrayList<>();
        }
        return makeListOfDTOs(offers);
    }

    public OfferDTO getOffer(String code) {
        Offer offer;
        try {
            offer = dao.read(code);
        } catch (DAOException e) {
            logger.error("Unable to read offer: " + e.getMessage(), e);
            return new OfferDTO();
        }
        return convertOfferToDTO(offer);
    }

    public boolean updateOfferIsHot(OfferDTO offerDTO) throws DAOException {
        Offer offer = convertDTOToOffer(offerDTO);
        return dao.update(offer, true);
    }

    public boolean updateOffer(OfferDTO offerDTO) throws DAOException, ValidationException {
        validateDiscount(offerDTO.getDiscount());

        Offer offer = convertDTOToOffer(offerDTO);
        boolean resultDelete = dao.delete(offerDTO.getCode());

        boolean resultCreate = dao.create(offer);

        return resultCreate && resultDelete;
    }

    public boolean createOffer(OfferDTO offerDTO) throws DAOException, ValidationException {
        validateDiscount(offerDTO.getDiscount());

        Offer offer = convertDTOToOffer(offerDTO);

        return dao.create(offer);
    }

    private void validateDiscount(double discount) throws ValidationException {
        if (!Validator.validateDiscount(discount))
            throw new ValidationException(ValidationMessageConstants.INVALID_DISCOUNT);
    }

    private Offer convertDTOToOffer(OfferDTO offerDTO) {
        String code = offerDTO.getCode();
        String city = offerDTO.getCity();
        String offerType = offerDTO.getOfferType();
        String hotelType = offerDTO.getHotelType();
        String hotelName = offerDTO.getHotel();
        int places = offerDTO.getPlaces();
        double discount = offerDTO.getDiscount();
        boolean isHot = offerDTO.isHot();
        double price = offerDTO.getPrice();
        return new Offer(0, code, city, offerType, hotelType, hotelName, places, discount, isHot, price);
    }

    private List<OfferDTO> makeListOfDTOs(List<Offer> offers) {
        List<OfferDTO> result = new CopyOnWriteArrayList<>();
        for (Offer o : offers) {
                result.add(convertOfferToDTO(o));
        }
        return result;
}

    protected OfferDTO convertOfferToDTO(Offer offer) {
        String code = offer.getCode();
        String type = offer.getOfferType();
        String hotel = offer.getHotelName();
        String hotelType = offer.getHotelType();
        String city = offer.getCity();
        int vacancy = offer.getPlaces();
        double discount = offer.getDiscount();
        boolean isHot = offer.isHot();
        double price = offer.getPrice();
        return new OfferDTO(code, type, hotel, hotelType, city, vacancy, discount, isHot, price);
    }


}
