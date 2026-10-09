package com.bookhaven.dao;

import com.bookhaven.config.Database;
import com.bookhaven.exception.ConflictException;
import com.bookhaven.exception.DataAccessException;
import com.bookhaven.exception.NotFoundException;
import com.bookhaven.model.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class JdbcBookRepository implements BookRepository {
    private static final String SELECT = "SELECT b.*, b.total_copies - (SELECT COUNT(*) FROM loans l WHERE l.book_id=b.id AND l.returned_on IS NULL) AS available FROM books b";
    @Override public List<Book> findAll() { return search(""); }
    @Override public List<Book> search(String query) {
        String sql = SELECT + " WHERE LOWER(b.title) LIKE ? OR LOWER(b.author) LIKE ? OR LOWER(b.category) LIKE ? ORDER BY b.title";
        String term = "%" + query.toLowerCase().trim() + "%";
        try (Connection c = Database.connect(); PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, term); p.setString(2, term); p.setString(3, term);
            try (ResultSet r = p.executeQuery()) { List<Book> rows = new ArrayList<>(); while (r.next()) rows.add(map(r)); return rows; }
        } catch (SQLException e) { throw new DataAccessException("Could not search books", e); }
    }
    @Override public Book findById(long id) {
        try (Connection c = Database.connect(); PreparedStatement p = c.prepareStatement(SELECT + " WHERE b.id=?")) {
            p.setLong(1, id); try (ResultSet r = p.executeQuery()) { if (r.next()) return map(r); }
            throw new NotFoundException("Book was not found");
        } catch (SQLException e) { throw new DataAccessException("Could not load book", e); }
    }
    @Override public Book create(String title, String author, String isbn, String category, int copies) {
        try (Connection c = Database.connect(); PreparedStatement p = c.prepareStatement("INSERT INTO books(title,author,isbn,category,total_copies) VALUES(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            p.setString(1,title); p.setString(2,author); p.setString(3,isbn); p.setString(4,category); p.setInt(5,copies); p.executeUpdate();
            try (ResultSet k=p.getGeneratedKeys()) { k.next(); return findById(k.getLong(1)); }
        } catch (SQLIntegrityConstraintViolationException e) { throw new ConflictException("That ISBN already exists in the catalog"); }
        catch (SQLException e) { throw new DataAccessException("Could not add book",e); }
    }
    @Override public long count() { return scalar("SELECT COUNT(*) FROM books"); }
    @Override public long countAvailable() { return scalar("SELECT COALESCE(SUM(b.total_copies-(SELECT COUNT(*) FROM loans l WHERE l.book_id=b.id AND l.returned_on IS NULL)),0) FROM books b"); }
    private long scalar(String sql) { try(Connection c=Database.connect(); Statement s=c.createStatement(); ResultSet r=s.executeQuery(sql)){r.next();return r.getLong(1);}catch(SQLException e){throw new DataAccessException("Could not count books",e);} }
    private Book map(ResultSet r) throws SQLException { return new Book(r.getLong("id"),r.getString("title"),r.getString("author"),r.getString("isbn"),r.getString("category"),r.getInt("total_copies"),r.getInt("available")); }
}
