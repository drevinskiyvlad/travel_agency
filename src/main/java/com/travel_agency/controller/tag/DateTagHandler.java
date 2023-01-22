package com.travel_agency.controller.tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateTagHandler extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        JspWriter out=pageContext.getOut();//returns the instance of JspWriter
        try{
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            out.print(dtf.format(LocalDateTime.now()));//printing date and time using JspWriter
        }catch(Exception e){System.out.println(e);}
        return SKIP_BODY;//will not evaluate the body content of the tag
    }
    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

}
