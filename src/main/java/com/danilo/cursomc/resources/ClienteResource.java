package com.danilo.cursomc.resources;

import com.danilo.cursomc.domain.Cliente;
import com.danilo.cursomc.services.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET) 
    public ResponseEntity<Cliente> find(@PathVariable final Integer id){

        final Cliente obj = service.find(id);
        //Retorna uma resposta de sucesso com o conteudo sendo o objeto encontrado
        return ResponseEntity.ok().body(obj);
    }
}