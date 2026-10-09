package com.bookhaven.model;

import java.time.LocalDate;

public final class Loan extends LibraryEntity {
    private final long bookId, memberId;
    private final String bookTitle, memberName;
    private final LocalDate borrowedOn, dueOn, returnedOn;
    public Loan(long id, long bookId, long memberId, String bookTitle, String memberName,
                LocalDate borrowedOn, LocalDate dueOn, LocalDate returnedOn) {
        super(id); this.bookId = bookId; this.memberId = memberId; this.bookTitle = bookTitle;
        this.memberName = memberName; this.borrowedOn = borrowedOn; this.dueOn = dueOn; this.returnedOn = returnedOn;
    }
    public long getBookId() { return bookId; }
    public long getMemberId() { return memberId; }
    public String getBookTitle() { return bookTitle; }
    public String getMemberName() { return memberName; }
    public LocalDate getBorrowedOn() { return borrowedOn; }
    public LocalDate getDueOn() { return dueOn; }
    public LocalDate getReturnedOn() { return returnedOn; }
    public boolean isOverdue() { return returnedOn == null && dueOn.isBefore(LocalDate.now()); }
    @Override public String getDisplayName() { return bookTitle + " borrowed by " + memberName; }
}
