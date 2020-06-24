package com.mdrscr.ftpksei.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mdrscr.ftpksei.persist.model.StatementKsei;

@Repository
public interface StatementKseiRepo extends JpaRepository<StatementKsei, Long> {

}
