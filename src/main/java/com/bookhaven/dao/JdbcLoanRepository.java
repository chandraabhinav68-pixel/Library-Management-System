package com.bookhaven.dao;

import com.bookhaven.config.Database;
import com.bookhaven.exception.ConflictException;
import com.bookhaven.exception.DataAccessException;
import com.bookhaven.exception.NotFoundException;
import com.bookhaven.model.Loan;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public final class JdbcLoanRepository implements LoanRepository {
    private static final String SELECT="SELECT l.*,b.title book_title,m.name member_name FROM loans l JOIN books b ON b.id=l.book_id JOIN members m ON m.id=l.member_id";
    @Override public List<Loan> findAll(){return findRecent();}
    @Override public List<Loan> findRecent(){return query(SELECT+" ORDER BY l.borrowed_on DESC,l.id DESC LIMIT 40");}
    @Override public List<Loan> findOverdue(){return query(SELECT+" WHERE l.returned_on IS NULL AND l.due_on<CURRENT_DATE ORDER BY l.due_on");}
    private List<Loan> query(String sql){try(Connection c=Database.connect();Statement s=c.createStatement();ResultSet r=s.executeQuery(sql)){List<Loan> rows=new ArrayList<>();while(r.next())rows.add(map(r));return rows;}catch(SQLException e){throw new DataAccessException("Could not load loans",e);}}
    @Override public Loan findById(long id){try(Connection c=Database.connect();PreparedStatement p=c.prepareStatement(SELECT+" WHERE l.id=?")){p.setLong(1,id);try(ResultSet r=p.executeQuery()){if(r.next())return map(r);}throw new NotFoundException("Loan was not found");}catch(SQLException e){throw new DataAccessException("Could not load loan",e);}}
    @Override public Loan issue(long bookId,long memberId){
        try(Connection c=Database.connect()){
            c.setAutoCommit(false);
            try(PreparedStatement book=c.prepareStatement("SELECT total_copies-(SELECT COUNT(*) FROM loans WHERE book_id=? AND returned_on IS NULL) available FROM books WHERE id=? FOR UPDATE")){
                book.setLong(1,bookId);book.setLong(2,bookId);try(ResultSet r=book.executeQuery()){if(!r.next())throw new NotFoundException("Book was not found");if(r.getInt(1)<1)throw new ConflictException("No copies of this book are currently available");}
            }
            try(PreparedStatement member=c.prepareStatement("SELECT id FROM members WHERE id=?")){member.setLong(1,memberId);try(ResultSet r=member.executeQuery()){if(!r.next())throw new NotFoundException("Member was not found");}}
            long id;
            try(PreparedStatement p=c.prepareStatement("INSERT INTO loans(book_id,member_id,borrowed_on,due_on) VALUES(?,?,?,?)",Statement.RETURN_GENERATED_KEYS)){
                LocalDate today=LocalDate.now();p.setLong(1,bookId);p.setLong(2,memberId);p.setDate(3,Date.valueOf(today));p.setDate(4,Date.valueOf(today.plusDays(14)));p.executeUpdate();try(ResultSet k=p.getGeneratedKeys()){k.next();id=k.getLong(1);}
            }
            c.commit();return findById(id);
        }catch(SQLException e){throw new DataAccessException("Could not issue book",e);}
    }
    @Override public Loan returnLoan(long id){
        try(Connection c=Database.connect();PreparedStatement p=c.prepareStatement("UPDATE loans SET returned_on=CURRENT_DATE WHERE id=? AND returned_on IS NULL")){
            p.setLong(1,id);if(p.executeUpdate()==0){Loan existing=findById(id);if(existing.getReturnedOn()!=null)throw new ConflictException("This book has already been returned");throw new NotFoundException("Loan was not found");}return findById(id);
        }catch(SQLException e){throw new DataAccessException("Could not return book",e);}
    }
    @Override public long countActive(){return scalar("SELECT COUNT(*) FROM loans WHERE returned_on IS NULL");}
    @Override public long countOverdue(){return scalar("SELECT COUNT(*) FROM loans WHERE returned_on IS NULL AND due_on<CURRENT_DATE");}
    private long scalar(String sql){try(Connection c=Database.connect();Statement s=c.createStatement();ResultSet r=s.executeQuery(sql)){r.next();return r.getLong(1);}catch(SQLException e){throw new DataAccessException("Could not count loans",e);}}
    private Loan map(ResultSet r)throws SQLException{Date returned=r.getDate("returned_on");return new Loan(r.getLong("id"),r.getLong("book_id"),r.getLong("member_id"),r.getString("book_title"),r.getString("member_name"),r.getDate("borrowed_on").toLocalDate(),r.getDate("due_on").toLocalDate(),returned==null?null:returned.toLocalDate());}
}
