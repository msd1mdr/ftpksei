package com.mdrscr.ftpksei.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mdrscr.ftpksei.persist.model.Parameter;

@Repository
public interface ParameterRepo extends JpaRepository<Parameter, String> {

	Parameter findByName(String name);
}
