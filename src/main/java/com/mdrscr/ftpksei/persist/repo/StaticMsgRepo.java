package com.mdrscr.ftpksei.persist.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mdrscr.ftpksei.persist.model.StaticMsg;


@Repository
public interface StaticMsgRepo extends JpaRepository<StaticMsg, String> {

	List<StaticMsg> findAllByActivitydateAndAckStatus (String activitydate, String ackStatus);
	List<StaticMsg> findAllByActivitydateAndAckActivity (String activitydate, String ackActivity);
}
