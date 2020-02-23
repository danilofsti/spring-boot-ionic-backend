package com.danilo.cursomc.services;

import java.util.List;
import java.util.Optional;

import com.danilo.cursomc.domain.Cliente;
import com.danilo.cursomc.dto.ClienteDTO;
import com.danilo.cursomc.repositories.ClienteRepository;
import com.danilo.cursomc.services.exceptions.DataIntegrityException;
import com.danilo.cursomc.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService{

    @Autowired
    private ClienteRepository repo;

    public Cliente find(Integer id){
        Optional<Cliente> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException(
            "Objeto não encontrado! Id: "+id+", Tipo: "+Cliente.class.getName()));
    }

    @Transactional
    public Cliente insert(Cliente obj){
        obj.setId(null);
        return repo.save(obj);
    }

    public Cliente update(Cliente obj){
        Cliente newObj = this.find(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    public void delete(Integer id){
        this.find(id);
        try{
            repo.deleteById(id);
        }
        catch(DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
        }
    }

    public List<Cliente> findAll(){
        return repo.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO objDto){
        return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
    }

    private void updateData(Cliente newObj, Cliente obj){
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }
}