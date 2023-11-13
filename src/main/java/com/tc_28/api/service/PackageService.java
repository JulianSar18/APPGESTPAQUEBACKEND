package com.tc_28.api.service;


import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.tc_28.api.dto.PackageDto;
import com.tc_28.api.dto.PackageToDeliverDto;

import jakarta.transaction.Transactional;

public interface PackageService {
	@Transactional
	public PackageDto addInventory(PackageDto packageDto);
	public String updateStatusPackage(PackageToDeliverDto packageToDeliverDto, String id_driver);
	@Transactional
	public String saveMassivePackages(MultipartFile file) throws IOException;
}
