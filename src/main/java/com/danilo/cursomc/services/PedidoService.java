package com.danilo.cursomc.services;

import java.util.Optional;

import com.danilo.cursomc.domain.Pedido;
import com.danilo.cursomc.repositories.PedidoRepository;
import com.danilo.cursomc.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService{

    @Autowired
    private PedidoRepository repo;

    public Pedido find(Integer id){
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException(
            "Objeto não encontrado! Id: "+id+", Tipo: "+Pedido.class.getName()));
    }

}