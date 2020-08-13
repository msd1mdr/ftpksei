package com.mdrscr.ftpksei.persist.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mdrscr.ftpksei.persist.model.StatementKsei;

@Repository
public interface StatementKseiRepo extends JpaRepository<StatementKsei, Long> {

	public StatementKsei findByExtrefAndFileName (String extref, String fileName);
	public List<StatementKsei> findByFileName (String filename);
}
