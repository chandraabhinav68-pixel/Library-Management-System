package com.bookhaven.dao;

import com.bookhaven.model.Loan;
import java.util.List;

public interface LoanRepository extends LibraryRepository<Loan> {
    List<Loan> findRecent();
    Loan issue(long bookId, long memberId);
    Loan returnLoan(long id);
    long countActive();
    long countOverdue();
    List<Loan> findOverdue();
}
