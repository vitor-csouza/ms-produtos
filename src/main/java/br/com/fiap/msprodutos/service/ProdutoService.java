package br.com.fiap.msprodutos.service;

import br.com.fiap.msprodutos.dto.ProdutoDTO;
import br.com.fiap.msprodutos.model.Produto;
import br.com.fiap.msprodutos.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Transactional
    public List<ProdutoDTO> getAllProdutos(){
        return repository.findAll().stream().map(ProdutoDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public ProdutoDTO getProdutoById(Long id){
        Produto produto = repository.findById(id).orElseThrow();
        ProdutoDTO dto = new ProdutoDTO(produto);
        return dto;
    }

    private void copyDtoToEntity(ProdutoDTO dto, Produto entity){
        entity.setNome(dto.getDescricao());
        entity.setDescricao(dto.getDescricao());
        entity.setPreco(dto.getPreco());
        entity.setCaracteristica(dto.getCaracteristica());
    }

    @Transactional
    public ProdutoDTO createProduto(ProdutoDTO dto){
        Produto entity = new Produto();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProdutoDTO(entity);
    }

    @Transactional
    public ProdutoDTO updateById(ProdutoDTO dto, Long id){
        Produto entity = repository.getReferenceById(id);
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProdutoDTO(entity);
    }

    @Transactional
    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
