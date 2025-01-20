package br.com.xablau.pedidos.api.controller;

import br.com.xablau.dtos.PedidoDto;
import br.com.xablau.pedidos.api.entity.ItemPedido;
import br.com.xablau.pedidos.api.entity.Pedido;
import br.com.xablau.pedidos.api.services.impl.PedidoServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidoServices pedidoServices;

    public PedidoController(PedidoServices pedidoServices) {
        this.pedidoServices = pedidoServices;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> findById(@PathVariable(name = "id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(pedidoServices.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(pedidoServices.findAll());
    }

    @GetMapping("/{id}/itens")
    public ResponseEntity<List<ItemPedido>> itemPedidoDoPedido(@PathVariable(name = "id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(pedidoServices.itensDoPedidoById(id));
    }

    @PostMapping
    public ResponseEntity<Pedido> save(@RequestBody PedidoDto pedidoDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoServices.save(pedidoDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pedido> update(@RequestBody PedidoDto pedidoDto, @PathVariable(name = "id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(pedidoServices.update(id, pedidoDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(name = "id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(pedidoServices.deleteById(id));
    }
}
