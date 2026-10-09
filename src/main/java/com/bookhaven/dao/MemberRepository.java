package com.bookhaven.dao;

import com.bookhaven.model.Member;
import java.util.List;

public interface MemberRepository extends LibraryRepository<Member> {
    List<Member> findRecent();
    Member create(String name, String email, String phone);
    long count();
}
