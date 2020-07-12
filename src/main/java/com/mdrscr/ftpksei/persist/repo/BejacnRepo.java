package com.mdrscr.ftpksei.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mdrscr.ftpksei.persist.model.Bejacn;

@Repository
public interface BejacnRepo extends JpaRepository<Bejacn, String> {

}
