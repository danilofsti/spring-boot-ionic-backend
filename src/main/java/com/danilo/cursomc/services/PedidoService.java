package com.danilo.cursomc.services;

import java.util.Date;
import java.util.Optional;

import com.danilo.cursomc.domain.ItemPedido;
import com.danilo.cursomc.domain.PagamentoComBoleto;
import com.danilo.cursomc.domain.Pedido;
import com.danilo.cursomc.domain.enums.EstadoPagamento;
import com.danilo.cursomc.repositories.ItemPedidoRepository;
import com.danilo.cursomc.repositories.PagamentoRepository;
import com.danilo.cursomc.repositories.PedidoRepository;
import com.danilo.cursomc.services.exceptions.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService{

    @Autowired
    private PedidoRepository repo;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;
    
	@Autowired
    private ProdutoService produtoService;
    
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Pedido find(Integer id){
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException(
            "Objeto não encontrado! Id: "+id+", Tipo: "+Pedido.class.getName()));
    }

    public Pedido insert(Pedido obj){
        obj.setId(null);
        obj.setInstante(new Date());
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if(obj.getPagamento() instanceof PagamentoComBoleto){
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
        }

        obj = repo.save(obj);
        pagamentoRepository.save(obj.getPagamento());

        for(ItemPedido item : obj.getItens()){
            item.setDesconto(0.0);
            item.setPreco(produtoService.find(item.getProduto().getId()).getPreco());
            item.setPedido(obj);
        }
        itemPedidoRepository.saveAll(obj.getItens());
        return obj;
    }

}