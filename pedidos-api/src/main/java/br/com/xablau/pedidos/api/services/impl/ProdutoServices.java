package br.com.xablau.pedidos.api.services.impl;

import br.com.xablau.pedidos.api.entity.Produto;
import br.com.xablau.pedidos.api.entity.dtos.ProdutoDto;

import java.util.List;
import java.util.UUID;

public interface ProdutoServices {

    Produto findById(UUID id);

    List<Produto> findAll();

    Produto update(UUID id, ProdutoDto produtoDto);

    String deleteById(UUID id);

    Produto save(ProdutoDto produtoDto);
}
