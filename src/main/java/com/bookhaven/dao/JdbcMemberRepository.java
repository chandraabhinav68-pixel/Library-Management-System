package com.bookhaven.dao;

import com.bookhaven.config.Database;
import com.bookhaven.exception.ConflictException;
import com.bookhaven.exception.DataAccessException;
import com.bookhaven.exception.NotFoundException;
import com.bookhaven.model.Member;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class JdbcMemberRepository implements MemberRepository {
    @Override public List<Member> findAll() { return query("SELECT * FROM members ORDER BY name"); }
    @Override public List<Member> findRecent() { return query("SELECT * FROM members ORDER BY joined_at DESC LIMIT 8"); }
    private List<Member> query(String sql) {
        try(Connection c=Database.connect(); Statement s=c.createStatement(); ResultSet r=s.executeQuery(sql)){List<Member> rows=new ArrayList<>();while(r.next())rows.add(map(r));return rows;}
        catch(SQLException e){throw new DataAccessException("Could not load members",e);}
    }
    @Override public Member findById(long id) {
        try(Connection c=Database.connect();PreparedStatement p=c.prepareStatement("SELECT * FROM members WHERE id=?")){p.setLong(1,id);try(ResultSet r=p.executeQuery()){if(r.next())return map(r);}throw new NotFoundException("Member was not found");}
        catch(SQLException e){throw new DataAccessException("Could not load member",e);}
    }
    @Override public Member create(String name,String email,String phone) {
        try(Connection c=Database.connect();PreparedStatement p=c.prepareStatement("INSERT INTO members(name,email,phone) VALUES(?,?,?)",Statement.RETURN_GENERATED_KEYS)){
            p.setString(1,name);p.setString(2,email);p.setString(3,phone);p.executeUpdate();try(ResultSet k=p.getGeneratedKeys()){k.next();return findById(k.getLong(1));}
        }catch(SQLIntegrityConstraintViolationException e){throw new ConflictException("A member with that email already exists");}catch(SQLException e){throw new DataAccessException("Could not register member",e);}
    }
    @Override public long count(){try(Connection c=Database.connect();Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT COUNT(*) FROM members")){r.next();return r.getLong(1);}catch(SQLException e){throw new DataAccessException("Could not count members",e);}}
    private Member map(ResultSet r)throws SQLException{return new Member(r.getLong("id"),r.getString("name"),r.getString("email"),r.getString("phone"));}
}
