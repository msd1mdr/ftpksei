package com.mdrscr.ftpksei.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mdrscr.ftpksei.persist.model.StaticKsei;

@Repository
public interface StaticKseiRepo extends JpaRepository<StaticKsei, String> {

	public StaticKsei findByExtrefAndFileName (String extref, String fileName);
}
