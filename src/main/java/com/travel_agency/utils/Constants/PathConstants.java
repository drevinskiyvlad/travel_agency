package com.travel_agency.utils.Constants;

/**
 * All path constants for commands and jsp pages
 */
public class PathConstants {
    private PathConstants() {}

    //path to jsp pages
    public static final String INDEX = "index.jsp";
    public static final String AUTHORIZATION = "authorization.jsp";
    public static final String REGISTRATION = "registration.jsp";
    public static final String OUR_OFFER = "our-offer.jsp";
    public static final String USER_CABINET = "user-cabinet.jsp";
    public static final String OFFER = "offer.jsp";
    public static final String ADD_OFFER = "add-offer.jsp";
    public static final String UPDATE_OFFER = "update-offer.jsp";
    public static final String ERROR = "error.jsp";

    //commands
    public static final String COMMAND_SIGNIN = "controller?action=signIn";
    public static final String COMMAND_SIGNUP = "controller?action=signUp";
    public static final String COMMAND_LOGOUT = "controller?action=logout";
    public static final String COMMAND_BLOCK_USER = "controller?action=blockUser";
    public static final String COMMAND_ADD_OFFER = "controller?action=addOffer";
    public static final String COMMAND_ADD_ORDER = "controller?action=addOrder";
    public static final String COMMAND_DELETE_OFFER = "controller?action=deleteOffer";
    public static final String COMMAND_DELETE_ORDER = "controller?action=deleteOrder";
    public static final String COMMAND_UPDATE_OFFER_HOT = "controller?action=updateOfferHot";
    public static final String COMMAND_UPDATE_OFFER = "controller?action=updateOffer";
    public static final String COMMAND_UPDATE_ORDER_STATUS = "controller?action=updateOrderStatus";
    public static final String COMMAND_UPDATE_USER_ROLE = "controller?action=updateUserRole";
    public static final String COMMAND_OFFERS_PAGINATION = "controller?action=offersPagination";
    public static final String COMMAND_ORDER_PAGINATION = "controller?action=orderPagination";
    public static final String COMMAND_USER_PAGINATION = "controller?action=userPagination";

    public static final String COMMAND_REDIRECT = "redirect";
}
