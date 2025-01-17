package br.com.xablau.pedidos.api.services;

import br.com.xablau.dtos.ProdutoDto;
import br.com.xablau.pedidos.api.entity.Produto;
import br.com.xablau.pedidos.api.exception.produtoException.ProdutoNotFoundException;
import br.com.xablau.pedidos.api.repository.ProdutoRepository;
import br.com.xablau.pedidos.api.services.impl.ProdutoServices;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProdutoServicesImpl implements ProdutoServices{

    private final ProdutoRepository produtoRepository;


    public ProdutoServicesImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public Produto findById(UUID id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado!", HttpStatus.NOT_FOUND, "NOT FOUND"));
    }

    @Override
    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    @Override
    public Produto update(UUID id, ProdutoDto produtoDto) {
        Produto produto= produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado!", HttpStatus.NOT_FOUND, "NOT FOUND"));
        BeanUtils.copyProperties(produtoDto, produto);
        return produtoRepository.save(produto);
    }

    @Override
    public String deleteById(UUID id) {
        if (!produtoRepository.existsById(id)){
            throw new ProdutoNotFoundException("Produto não encontrado!", HttpStatus.NOT_FOUND, "NOT FOUND");
        }
        return "Produto deletado com sucesso!";
    }

    @Override
    public Produto save(ProdutoDto produtoDto) {
        if (produtoRepository.existsByNome(produtoDto.getName())){
            throw new ProdutoNotFoundException("Produto Já encontrado na base de dados!", HttpStatus.CONFLICT, "CONFLICT");
        }

        Produto produto = new Produto();
        BeanUtils.copyProperties(produtoDto, produto);
        return produtoRepository.save(produto);
    }
}
