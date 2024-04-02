package com.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.UsuarioDto;
import com.demo.dto.UsuarioResponse;
import com.demo.entity.Usuario;
import com.demo.services.IUsuarioService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api")
public class UsuarioController {
	
	@Autowired
	private IUsuarioService ActividadService;
	

	
	@PostMapping("/guardar")
	public ResponseEntity<?> guardar(@Valid @RequestBody UsuarioDto usuarioDto, BindingResult result ) {
		Map<String, Object> response=new HashMap<>();
		
		if(result.hasErrors()) {
			
			
			
			List<String> errors=result.getFieldErrors()
					.stream()
					.map(err->"El campo '"+err.getField()+"' "+err.getDefaultMessage()
						).collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		try {
			
			UsuarioResponse n=ActividadService.save(usuarioDto);
			response.put("mensaje","El usuario ha sido creado con éxito!");
			response.put("usuario",n);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos.");
			response.put("error",e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.CREATED);

		
	
	}



}
