package com.bookhaven.service;

import com.bookhaven.dao.*;
import com.bookhaven.exception.LibraryException;
import com.bookhaven.model.*;
import java.util.List;
import java.util.Map;

/** Application rules, kept independent from HTTP and SQL implementation details. */
public final class LibraryService {
    private final BookRepository books;
    private final MemberRepository members;
    private final LoanRepository loans;

    public LibraryService(BookRepository books, MemberRepository members, LoanRepository loans) {
        this.books=books; this.members=members; this.loans=loans;
    }
    public List<Book> searchBooks(String q) { return books.search(q == null ? "" : q); }
    public List<Member> getMembers() { return members.findAll(); }
    public List<Loan> getLoans() { return loans.findRecent(); }
    public Book addBook(String title,String author,String isbn,String category,int copies) {
        required(title,"Title");required(author,"Author");required(isbn,"ISBN");required(category,"Category");
        if(copies<1 || copies>500)throw new LibraryException("Copies must be between 1 and 500");
        return books.create(title.trim(),author.trim(),isbn.trim(),category.trim(),copies);
    }
    public Member addMember(String name,String email,String phone) {
        required(name,"Name");required(email,"Email");required(phone,"Phone");
        if(!email.trim().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"))throw new LibraryException("Enter a valid email address");
        return members.create(name.trim(),email.trim().toLowerCase(),phone.trim());
    }
    public Loan issue(long bookId,long memberId) { if(bookId<1||memberId<1)throw new LibraryException("Choose a book and a member");return loans.issue(bookId,memberId); }
    public Loan returnBook(long loanId) { return loans.returnLoan(loanId); }
    public Map<String,Long> dashboard() { return Map.of("titles",books.count(),"availableCopies",books.countAvailable(),"members",members.count(),"activeLoans",loans.countActive(),"overdue",loans.countOverdue()); }
    public List<Loan> overdueLoans() { return loans.findOverdue(); }
    private void required(String value,String label) { if(value==null||value.isBlank())throw new LibraryException(label+" is required"); }
}
