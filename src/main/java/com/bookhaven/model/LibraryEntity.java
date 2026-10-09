package com.bookhaven.model;

/** Shared identity for persisted library records. */
public abstract class LibraryEntity {
    private final long id;

    protected LibraryEntity(long id) { this.id = id; }

    public long getId() { return id; }

    /** Each concrete entity supplies a useful human-readable label. */
    public abstract String getDisplayName();
}
