package com.demo.services;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.demo.dto.UsuarioDto;
import com.demo.dto.UsuarioResponse;
import com.demo.entity.Usuario;

public interface IUsuarioService {
	


	public UsuarioResponse save(UsuarioDto usuarioDto);


}
