package com.bookhaven.web;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
@WebServlet("/api/dashboard")
public final class DashboardServlet extends ApiServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws IOException{try{send(resp,library.dashboard());}catch(Exception e){fail(resp,e);}}
}
