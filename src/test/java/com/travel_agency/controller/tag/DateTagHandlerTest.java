package com.travel_agency.controller.tag;

import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DateTagHandlerTest {

    private final DateTagHandler dateTagHandler = new DateTagHandler();
    private final PageContext pageContext = mock(PageContext.class);
    private final JspWriter jspWriter = mock(JspWriter.class);

    @Test
    void testDoStartTag() throws Exception {
        when(pageContext.getOut()).thenReturn(jspWriter);
        dateTagHandler.setPageContext(pageContext);

        int result = dateTagHandler.doStartTag();

        verify(jspWriter).print(DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDateTime.now()));
        assertEquals(Tag.SKIP_BODY, result);
    }

    @Test
    void testDoStartTagException() throws Exception {
        when(pageContext.getOut()).thenReturn(jspWriter);
        doThrow(new IOException()).when(jspWriter).print(anyString());
        dateTagHandler.setPageContext(pageContext);

        int result = dateTagHandler.doStartTag();

        assertEquals(Tag.SKIP_BODY, result);
    }

    @Test
    void testDoEndTag() {
        int result = dateTagHandler.doEndTag();

        assertEquals(Tag.EVAL_PAGE, result);
    }
}