package com.mdrscr.ftpksei.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mdrscr.ftpksei.persist.model.AnggotaBursa;
import com.mdrscr.ftpksei.persist.repo.AnggotaBursaRepo;

@RestController
@RequestMapping("/rest")
public class DataRestController {

	@Autowired
	private AnggotaBursaRepo anggotaBursaRepo;
	
	@RequestMapping("/anggotabursa")
	public List<AnggotaBursa> getAllAnggotaBursa() {
		return anggotaBursaRepo.findAll();
	}
	
	@RequestMapping("/anggotabursa/{id}")
	public AnggotaBursa getAnggotaBursaById (@PathVariable("id") String id) {
		return anggotaBursaRepo.findById(id).orElse(new AnggotaBursa());
	}

}
