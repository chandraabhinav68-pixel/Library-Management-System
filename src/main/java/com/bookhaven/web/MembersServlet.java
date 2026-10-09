package com.bookhaven.web;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
@WebServlet("/api/members")
public final class MembersServlet extends ApiServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws IOException{try{send(resp,library.getMembers());}catch(Exception e){fail(resp,e);}}
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws IOException{try{var b=body(req);var result=library.addMember(text(b,"name"),text(b,"email"),text(b,"phone"));resp.setStatus(201);send(resp,result);}catch(Exception e){fail(resp,e);}}
}
