package br.com.caminholivre.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.caminholivre.dto.request.EstabelecimentoRequestDTO;
import br.com.caminholivre.dto.response.EstabelecimentoResponseDTO;
import br.com.caminholivre.entity.Estabelecimento;
import br.com.caminholivre.exception.ResourceNotFoundException;
import br.com.caminholivre.repository.EstabelecimentoRepository;

@Service
public class EstabelecimentoService {

    private final EstabelecimentoRepository repository;

    public EstabelecimentoService(EstabelecimentoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public EstabelecimentoResponseDTO criar(EstabelecimentoRequestDTO dto) {
        Estabelecimento estabelecimento = new Estabelecimento();
        atualizarDados(estabelecimento, dto);

        return converter(repository.save(estabelecimento));
    }

    @Transactional(readOnly = true)
    public Page<EstabelecimentoResponseDTO> listar(Pageable pageable) {
        return repository.findAll(pageable).map(this::converter);
    }

    @Transactional(readOnly = true)
    public EstabelecimentoResponseDTO buscarPorId(Long id) {
        return converter(buscarEntidade(id));
    }

    @Transactional
    public EstabelecimentoResponseDTO atualizar(
            Long id, EstabelecimentoRequestDTO dto) {

        Estabelecimento estabelecimento = buscarEntidade(id);
        atualizarDados(estabelecimento, dto);

        return converter(repository.save(estabelecimento));
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(buscarEntidade(id));
    }

    private Estabelecimento buscarEntidade(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Estabelecimento não encontrado: " + id
            ));
    }

    private void atualizarDados(
            Estabelecimento estabelecimento,
            EstabelecimentoRequestDTO dto) {

        estabelecimento.setNome(dto.getNome());
        estabelecimento.setDescricao(dto.getDescricao());
        estabelecimento.setEndereco(dto.getEndereco());
        estabelecimento.setLatitude(dto.getLatitude());
        estabelecimento.setLongitude(dto.getLongitude());
        estabelecimento.setAcessibilidade(dto.getAcessibilidade());
    }

    private EstabelecimentoResponseDTO converter(
            Estabelecimento e) {

        return new EstabelecimentoResponseDTO(
            e.getId(),
            e.getNome(),
            e.getDescricao(),
            e.getEndereco(),
            e.getLatitude(),
            e.getLongitude(),
            e.getAcessibilidade()
        );
    }
}
