package com.bookhaven.web;

import com.bookhaven.exception.ConflictException;
import com.bookhaven.exception.LibraryException;
import com.bookhaven.exception.NotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Map;

public abstract class ApiServlet extends HttpServlet {
    static final ObjectMapper JSON=new ObjectMapper().findAndRegisterModules();
    public static com.bookhaven.service.LibraryService library;
    JsonNode body(HttpServletRequest request)throws IOException{return JSON.readTree(request.getInputStream());}
    String text(JsonNode body,String field){return body.hasNonNull(field)?body.get(field).asText():null;}
    long number(JsonNode body,String field){return body.hasNonNull(field)?body.get(field).asLong():0;}
    void send(HttpServletResponse response,Object value)throws IOException{response.setContentType("application/json;charset=UTF-8");JSON.writeValue(response.getOutputStream(),value);}
    void fail(HttpServletResponse response,Exception error)throws IOException{
        int status=error instanceof NotFoundException?404:error instanceof ConflictException?409:error instanceof LibraryException?400:500;
        response.setStatus(status);send(response,Map.of("error",status==500?"Something went wrong. Please try again.":error.getMessage()));
    }
}
