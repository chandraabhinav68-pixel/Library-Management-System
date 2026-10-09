package com.bookhaven.model;

public final class Book extends LibraryEntity {
    private final String title, author, isbn, category;
    private final int totalCopies, availableCopies;

    public Book(long id, String title, String author, String isbn, String category, int totalCopies, int availableCopies) {
        super(id); this.title = title; this.author = author; this.isbn = isbn; this.category = category;
        this.totalCopies = totalCopies; this.availableCopies = availableCopies;
    }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public String getCategory() { return category; }
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }
    @Override public String getDisplayName() { return title + " — " + author; }
}
