package com.demo.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.demo.dao.IPhonesDao;
import com.demo.dao.IUsuarioDao;
import com.demo.dto.PhonesDto;
import com.demo.dto.UsuarioDto;
import com.demo.dto.UsuarioResponse;
import com.demo.entity.Phones;
import com.demo.entity.Usuario;

import jakarta.transaction.Transactional;

@Service	
public class UsuarioServiceImp implements IUsuarioService {
	
	@Autowired
	private IUsuarioDao iUsuarDao;
	
	@Autowired
	private IPhonesDao iPhonesDao;

	
	@Override
	public UsuarioResponse save(UsuarioDto usuarioDto) {

		Usuario usuarioNew=null;
	
		
			
			Usuario usuario=new Usuario();
			Phones phones=new Phones();
			
			usuario.setName(usuarioDto.getName());
			usuario.setEmail(usuarioDto.getEmail());
			usuario.setPassword(usuarioDto.getPassword());
			
			usuarioNew= iUsuarDao.save(usuario);
			
			 Phones insertPhones=new Phones();
			
			 for (PhonesDto p:usuarioDto.getPhones()) {
				 
				 insertPhones.setNumber(p.getNumber());
				 insertPhones.setCitycode(p.getCitycode());
				 insertPhones.setContrycode(p.getContrycode());
				 insertPhones.setIdUsuario(usuarioNew.getId());
				
				  iPhonesDao.save(insertPhones);
			 }
		
			 Usuario usuarioRespuesta=iUsuarDao.findById(usuarioNew.getId()).orElse(null);
			 UsuarioResponse respuesta=new UsuarioResponse();
			 
			 respuesta.setId(usuarioRespuesta.getId());
			 respuesta.setCreated(usuarioRespuesta.getCreated());
			 respuesta.setModified(usuarioRespuesta.getModified());
			 respuesta.setIsactive(usuarioRespuesta.isIsactive());
			 
			 
			 return respuesta;
			
	}



}
