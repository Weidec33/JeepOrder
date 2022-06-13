package com.promineotech.jeep.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "Jeep Sales Service"), servers = { @Server(url = "http://localhost:8080",
 description = "Local server.")})

@RequestMapping("/jeeps")

public interface JeepSalesController {
	
	
	
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	 List<Jeep> fetchJeeps(JeepModel model, @RequestParam String trim);
}
