package com.bookhaven.dao;

import com.bookhaven.model.LibraryEntity;
import java.util.List;

/** Generic read contract shared by library repositories. */
public interface LibraryRepository<T extends LibraryEntity> {
    List<T> findAll();
    T findById(long id);
}
