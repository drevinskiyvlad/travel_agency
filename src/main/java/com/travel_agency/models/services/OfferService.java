package com.travel_agency.models.services;

import com.travel_agency.DB.DAO.OfferDAO;
import com.travel_agency.models.DAO.Offer;
import com.travel_agency.models.DTO.OfferDTO;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class OfferService {
    private final OfferDAO dao;

    public OfferService(OfferDAO dao) {
        this.dao = dao;
    }

    public List<OfferDTO> getAllOffers(){
        List<Offer> offers = dao.readAll();
        return makeListOfDTOs(offers, false);
    }

    public List<OfferDTO> getAllHotOffers(){
        List<Offer> offers = dao.readAll();
        return makeListOfDTOs(offers, true);
    }

    public OfferDTO getOffer(String code){
        Offer offer = dao.read(code);
        return convertOfferToDTO(offer);
    }

    private List<OfferDTO> makeListOfDTOs(List<Offer> offers, boolean onlyHot) {
        List<OfferDTO> result = new CopyOnWriteArrayList<>();
        for(Offer o: offers){
            if(o.isHot())
                result.add(convertOfferToDTO(o));
        }

        if(!onlyHot) {
            for (Offer o : offers) {
                if (!o.isHot())
                    result.add(convertOfferToDTO(o));
            }
        }
        return result;
    }

    private OfferDTO convertOfferToDTO(Offer offer){
        String code = offer.getCode();
        String type = offer.getType();
        String tc = offer.getTransportCompany().getName();
        String hotel = offer.getHotel().getName();
        String city = offer.getHotel().getCity();
        int vacancy = offer.getVacancy();
        double discount = offer.getDiscount();
        boolean isHot = offer.isHot();
        double price = offer.getPrice();
        return new OfferDTO(code,type,tc,hotel,city,vacancy,discount,isHot,price);
    }
}
