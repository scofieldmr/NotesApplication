package com.ms.notesapplication.repository;

import com.ms.notesapplication.entity.Notes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepository extends JpaRepository<Notes, Long> {

    List<Notes> findByOwnerUsername(String username);

}
