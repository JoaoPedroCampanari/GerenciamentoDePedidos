package br.com.xablau.pedidos.api.controller;


import br.com.xablau.dtos.ClienteDto;
import br.com.xablau.pedidos.api.entity.Cliente;
import br.com.xablau.pedidos.api.entity.Pedido;
import br.com.xablau.pedidos.api.services.impl.ClienteServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteServices clienteServices;

    public ClienteController(ClienteServices clienteServices) {
        this.clienteServices = clienteServices;
    }

    @GetMapping("/{id}")
    ResponseEntity<Cliente> findById(@PathVariable(name = "id") UUID id){
        Cliente cliente = clienteServices.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
    }

    @GetMapping
    ResponseEntity<List<Cliente>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(clienteServices.findAll());
    }

    @PostMapping
    ResponseEntity<String> save(@RequestBody ClienteDto clienteDto){
        clienteServices.enfileirarCliente(clienteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Um email com mais detalhes sobre o cadastrado sera enviado");
    }

    @PutMapping("/{id}")
    ResponseEntity<Cliente> update(@PathVariable(name = "id") UUID id, @RequestBody ClienteDto clienteDto){
        return ResponseEntity.status(HttpStatus.OK).body(clienteServices.update(id,clienteDto));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteById(@PathVariable(name = "id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(clienteServices.deleteById(id));
    }

    @GetMapping("/pedidos/{id}")
    ResponseEntity<List<Pedido>> pedidosClienteById(@PathVariable(name = "id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(clienteServices.pedidosClienteById(id));
    }

}
