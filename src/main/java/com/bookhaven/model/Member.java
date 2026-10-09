package com.bookhaven.model;

public final class Member extends LibraryEntity {
    private final String name, email, phone;
    public Member(long id, String name, String email, String phone) {
        super(id); this.name = name; this.email = email; this.phone = phone;
    }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    @Override public String getDisplayName() { return name; }
}
