package com.mdrscr.ftpksei.persist.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mdrscr.ftpksei.persist.model.FileTransmision;

@Repository
public interface FileTransmisionRepo extends JpaRepository<FileTransmision, Long> {

	public List<FileTransmision> findBySubModulAndValDateOrderByDailyCounterDesc (
													String subModul, String valdate);
	
	public List<FileTransmision> findBySendStatusOrderBySendTime(String sendStatus);
}
