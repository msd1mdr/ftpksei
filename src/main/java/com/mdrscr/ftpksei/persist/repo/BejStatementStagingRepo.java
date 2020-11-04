package com.mdrscr.ftpksei.persist.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mdrscr.ftpksei.persist.model.BejStatementStaging;

@Repository
public interface BejStatementStagingRepo extends JpaRepository<BejStatementStaging, String> {

//	List<BejStatementStaging> findByFlag (String fl);
	List<BejStatementStaging> findByValdat (String valdat);
	
}
