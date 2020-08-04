package com.mdrscr.ftpksei.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mdrscr.ftpksei.persist.model.BalanceKsei;

public interface BalanceKseiRepo extends JpaRepository<BalanceKsei, String> {

	public BalanceKsei findByExtrefAndFileName (String extref, String fileName);
}
