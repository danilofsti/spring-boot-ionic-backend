package com.danilo.cursomc.resources;

import com.danilo.cursomc.domain.Categoria;
import com.danilo.cursomc.services.CategoriaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET) 
    public ResponseEntity<?> find(@PathVariable final Integer id){

        final Categoria obj = service.buscar(id);
        //Retorna uma resposta de sucesso com o conteudo sendo o objeto encontrado
        return ResponseEntity.ok().body(obj);
    }
}