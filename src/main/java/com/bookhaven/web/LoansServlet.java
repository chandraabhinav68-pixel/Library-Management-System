package com.bookhaven.web;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Map;
@WebServlet("/api/loans")
public final class LoansServlet extends ApiServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws IOException{try{send(resp,library.getLoans());}catch(Exception e){fail(resp,e);}}
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws IOException{try{var b=body(req);var result=library.issue(number(b,"bookId"),number(b,"memberId"));resp.setStatus(201);send(resp,result);}catch(Exception e){fail(resp,e);}}
    @Override protected void service(HttpServletRequest req,HttpServletResponse resp)throws IOException {
        String path=req.getPathInfo();
        if(path!=null&&path.matches("/\\d+/return")&&"POST".equalsIgnoreCase(req.getMethod())){
            try{long id=Long.parseLong(path.split("/")[1]);send(resp,library.returnBook(id));}catch(Exception e){fail(resp,e);}return;
        }
        try{if("GET".equalsIgnoreCase(req.getMethod()))doGet(req,resp);else if("POST".equalsIgnoreCase(req.getMethod()))doPost(req,resp);else{resp.setStatus(405);send(resp,Map.of("error","Method not allowed"));}}catch(IOException e){throw e;}
    }
}
