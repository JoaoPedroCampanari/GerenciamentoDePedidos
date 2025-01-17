package br.com.xablau.pedidos.api.controller;

import br.com.xablau.dtos.ProdutoDto;
import br.com.xablau.pedidos.api.entity.Produto;
import br.com.xablau.pedidos.api.services.impl.ProdutoServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoServices produtoServices;

    public ProdutoController(ProdutoServices produtoServices) {
        this.produtoServices = produtoServices;
    }

    @GetMapping("/{id}")
    ResponseEntity<Produto> findById(@PathVariable(name = "id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(produtoServices.findById(id));
    }

    @GetMapping
    ResponseEntity<List<Produto>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(produtoServices.findAll());
    }

    @PutMapping("/{id}")
    ResponseEntity<Produto> update(@RequestBody ProdutoDto produtoDto, @PathVariable(name = "id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(produtoServices.update(id, produtoDto));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteById(@PathVariable(name = "id") UUID id){
        return ResponseEntity.status(HttpStatus.OK).body(produtoServices.deleteById(id));
    }

    @PostMapping
    ResponseEntity<Produto> save(@RequestBody ProdutoDto produtoDto){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(produtoServices.save(produtoDto));
    }
}
