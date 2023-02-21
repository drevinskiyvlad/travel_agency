package com.travel_agency.model.services;

import com.travel_agency.model.DB.DAO.OfferDAO;
import com.travel_agency.model.DTO.OfferDTO;
import com.travel_agency.model.entity.Hotel;
import com.travel_agency.model.entity.Offer;
import com.travel_agency.utils.Constants.SORTING_BY;
import com.travel_agency.utils.Constants.ValidationMessageConstants;
import com.travel_agency.utils.Validator;
import com.travel_agency.utils.exceptions.DAOException;
import com.travel_agency.utils.exceptions.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OfferService {
    private final OfferDAO<Offer> dao;
    private static final Logger logger = LogManager.getLogger(OfferService.class);

    /**
     * Constructor
     */
    public OfferService(OfferDAO<Offer> dao) {
        this.dao = dao;
    }

    /**
     * Get all offer from database with defined parameters and in sorted queue
     * @param offset index of first offer from database
     * @param numOfRecords number of records that given from database
     * @param onlyHot if true, then return only hot offers, if false, then all offers
     * @param sortingBy parameter of sorting
     * @return List of offers in sorted queue
     */
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

    /**
     * @return OfferDTO with this code
     */
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

    /**
     * Change offer is hot status
     * @return result of adding
     */
    public boolean updateOfferIsHot(OfferDTO offerDTO) throws DAOException {
        Offer offer = convertDTOToOffer(offerDTO);
        return dao.update(offer, true);
    }

    /**
     * Replace previous offer with this code to offer with new params
     * @throws ValidationException If discount is not valid
     */
    public boolean updateOffer(OfferDTO offerDTO) throws DAOException, ValidationException {
        validateDiscount(offerDTO.getDiscount());
        validatePlaces(offerDTO.getPlaces());

        Offer offer = convertDTOToOffer(offerDTO);

        boolean resultDelete = dao.delete(offerDTO.getCode());
        boolean resultCreate = dao.create(offer);

        return resultCreate && resultDelete;
    }

    /**
     * Added offer to database
     * @return result of creating
     */
    public boolean createOffer(OfferDTO offerDTO) throws DAOException, ValidationException {
        validateDiscount(offerDTO.getDiscount());
        validatePlaces(offerDTO.getPlaces());

        Offer offer = convertDTOToOffer(offerDTO);
        return dao.create(offer);
    }

    private void validateDiscount(double discount) throws ValidationException {
        if (!Validator.validateDiscount(discount))
            throw new ValidationException(ValidationMessageConstants.INVALID_DISCOUNT);
    }

    private void validatePlaces(int places) throws ValidationException {
        if (!Validator.validatePlaces(places))
            throw new ValidationException(ValidationMessageConstants.INVALID_PLACES);
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
        boolean active = offerDTO.isActive();
        double price = offerDTO.getPrice();

        Hotel hotel = new Hotel(0, hotelName, hotelType, city);

        return new Offer(0, code, hotel, offerType, places, discount, isHot, active, price);
    }

    private List<OfferDTO> makeListOfDTOs(List<Offer> offers) {
        List<OfferDTO> result = new CopyOnWriteArrayList<>();
        for (Offer o : offers) {
                result.add(convertOfferToDTO(o));
        }
        return result;
}

    protected OfferDTO convertOfferToDTO(Offer offer) {
        Hotel hotel = offer.getHotel();

        String code = offer.getCode();
        String type = offer.getOfferType();
        String hotelName = hotel.getName();
        String hotelType = hotel.getType();
        String city = hotel.getCity();
        int vacancy = offer.getPlaces();
        double discount = offer.getDiscount();
        boolean isHot = offer.isHot();
        boolean active = offer.isActive();
        double price = offer.getPrice();

        return new OfferDTO(code, type, hotelName, hotelType, city, vacancy, discount, isHot, active, price);
    }


}
