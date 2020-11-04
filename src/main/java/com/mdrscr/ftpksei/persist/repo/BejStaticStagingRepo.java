package com.mdrscr.ftpksei.persist.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mdrscr.ftpksei.persist.model.BejStaticStaging;

@Repository
public interface BejStaticStagingRepo extends JpaRepository<BejStaticStaging, String> {
	
//	List<BejStaticStaging> findByFlag (String fl);
	List<BejStaticStaging> findByActdate (String actdate);

}
