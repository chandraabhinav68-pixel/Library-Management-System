package com.bookhaven.web;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Map;
@WebServlet("/api/books")
public final class BooksServlet extends ApiServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws IOException{try{send(resp,library.searchBooks(req.getParameter("q")));}catch(Exception e){fail(resp,e);}}
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws IOException{try{var b=body(req);var book=library.addBook(text(b,"title"),text(b,"author"),text(b,"isbn"),text(b,"category"),b.path("totalCopies").asInt(1));resp.setStatus(201);send(resp,book);}catch(Exception e){fail(resp,e);}}
}
