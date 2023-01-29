package com.travel_agency.controller.tag;

import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTagHandler extends TagSupport {
    private static final Logger logger = LogManager.getLogger(DateTagHandler.class);
    @Override
    public int doStartTag() {
        JspWriter out=pageContext.getOut();//returns the instance of JspWriter
        try{
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            out.print(dtf.format(LocalDateTime.now()));//printing date and time using JspWriter
        }catch(Exception e){
            logger.error("Error while handle tag",e);
        }
        return SKIP_BODY;//will not evaluate the body content of the tag
    }
    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }

}
