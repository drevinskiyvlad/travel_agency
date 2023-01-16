package com.travel_agency.models.services;

import com.travel_agency.DB.DAO.OfferDAO;
import com.travel_agency.exceptions.DAOException;
import com.travel_agency.exceptions.ValidationException;
import com.travel_agency.models.DAO.Offer;
import com.travel_agency.models.DTO.OfferDTO;
import com.travel_agency.utils.ValidationMessageConstants;
import com.travel_agency.utils.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OfferService {
    private final OfferDAO dao;
    private static final Logger logger = LogManager.getLogger(OfferService.class);

    public OfferService(OfferDAO dao) {
        this.dao = dao;
    }

    public List<OfferDTO> getAllOffers() {
        List<Offer> offers;
        try {
            offers = dao.readAll();
        } catch (DAOException e) {
            logger.error("Unable to read offers: " + e.getMessage(), e);
            return new ArrayList<>();
        }
        return makeListOfDTOs(offers, false);
    }

    public List<OfferDTO> getAllHotOffers() {
        List<Offer> offers = null;
        try {
            offers = dao.readAll();
        } catch (DAOException e) {
            logger.error("Unable to read offers: " + e.getMessage(), e);
            return new ArrayList<>();
        }
        return makeListOfDTOs(offers, true);
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

    public boolean deleteOffer(OfferDTO offerDTO) throws DAOException {
        Offer offer = convertDTOToOffer(offerDTO);
        return dao.delete(offer);
    }

    public boolean updateOfferIsHot(OfferDTO offerDTO) throws DAOException {
        Offer offer = convertDTOToOffer(offerDTO);
        return dao.update(offer, true);
    }

    public boolean updateOffer(String code, String type, int vacancy, double discount) throws DAOException, ValidationException {
        validateDiscount(discount);

        Offer offer = dao.read(code);
        boolean resultDelete = dao.delete(offer);

        setVariablesToOffer(type, vacancy, discount, offer);

        boolean resultCreate = dao.create(offer);
        return resultCreate && resultDelete;
    }

    private static void setVariablesToOffer(String type, int vacancy, double discount, Offer offer) {
        offer.setType(type);
        offer.setVacancy(vacancy);
        offer.setDiscount(discount);
    }

    private void validateDiscount(double discount) throws ValidationException{
        if(!Validator.validateDiscount(discount))
            throw new ValidationException(ValidationMessageConstants.INVALID_DISCOUNT);
    }

    private Offer convertDTOToOffer(OfferDTO offerDTO) throws DAOException {
        return dao.read(offerDTO.getCode());
    }

    private List<OfferDTO> makeListOfDTOs(List<Offer> offers, boolean onlyHot) {
        List<OfferDTO> result = new CopyOnWriteArrayList<>();
        for (Offer o : offers) {
            if (o.isHot())
                result.add(convertOfferToDTO(o));
        }

        if (!onlyHot) {
            for (Offer o : offers) {
                if (!o.isHot())
                    result.add(convertOfferToDTO(o));
            }
        }
        return result;
    }

    protected OfferDTO convertOfferToDTO(Offer offer) {
        String code = offer.getCode();
        String type = offer.getType();
        String tc = offer.getTransportCompany().getName();
        String hotel = offer.getHotel().getName();
        String hotelType = offer.getHotel().getHotelType();
        String city = offer.getHotel().getCity();
        int vacancy = offer.getVacancy();
        double discount = offer.getDiscount();
        boolean isHot = offer.isHot();
        double price = offer.getPrice();
        return new OfferDTO(code, type, tc, hotel, hotelType, city, vacancy, discount, isHot, price);
    }
}
