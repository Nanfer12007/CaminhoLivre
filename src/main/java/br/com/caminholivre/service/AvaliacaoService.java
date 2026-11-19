package br.com.caminholivre.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.caminholivre.dto.request.AvaliacaoRequestDTO;
import br.com.caminholivre.dto.response.AvaliacaoResponseDTO;
import br.com.caminholivre.entity.Avaliacao;
import br.com.caminholivre.entity.Estabelecimento;
import br.com.caminholivre.entity.Usuario;
import br.com.caminholivre.exception.ResourceNotFoundException;
import br.com.caminholivre.repository.AvaliacaoRepository;
import br.com.caminholivre.repository.EstabelecimentoRepository;
import br.com.caminholivre.repository.UsuarioRepository;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;

    public AvaliacaoService(
            AvaliacaoRepository avaliacaoRepository,
            UsuarioRepository usuarioRepository,
            EstabelecimentoRepository estabelecimentoRepository) {

        this.avaliacaoRepository = avaliacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.estabelecimentoRepository = estabelecimentoRepository;
    }

    @Transactional
    public AvaliacaoResponseDTO criar(
            AvaliacaoRequestDTO dto, Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Usuário não encontrado"
            ));

        Estabelecimento estabelecimento =
            estabelecimentoRepository.findById(dto.getEstabelecimentoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Estabelecimento não encontrado"
                ));

        if (avaliacaoRepository.existsByUsuarioIdAndEstabelecimentoId(
                usuarioId, estabelecimento.getId())) {
            throw new IllegalArgumentException(
                "Você já avaliou este estabelecimento"
            );
        }

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setNota(dto.getNota());
        avaliacao.setComentario(dto.getComentario());
        avaliacao.setUsuario(usuario);
        avaliacao.setEstabelecimento(estabelecimento);

        return converter(avaliacaoRepository.save(avaliacao));
    }

    @Transactional(readOnly = true)
    public Page<AvaliacaoResponseDTO> listar(Pageable pageable) {
        return avaliacaoRepository.findAll(pageable)
            .map(this::converter);
    }

    @Transactional(readOnly = true)
    public AvaliacaoResponseDTO buscarPorId(Long id) {
        return converter(buscarEntidade(id));
    }

    @Transactional
    public AvaliacaoResponseDTO atualizar(
            Long id, AvaliacaoRequestDTO dto) {

        Avaliacao avaliacao = buscarEntidade(id);

        Estabelecimento estabelecimento =
            estabelecimentoRepository.findById(dto.getEstabelecimentoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                    "Estabelecimento não encontrado"
                ));

        avaliacao.setNota(dto.getNota());
        avaliacao.setComentario(dto.getComentario());
        avaliacao.setEstabelecimento(estabelecimento);

        return converter(avaliacaoRepository.save(avaliacao));
    }

    @Transactional
    public void excluir(Long id) {
        avaliacaoRepository.delete(buscarEntidade(id));
    }

    private Avaliacao buscarEntidade(Long id) {
        return avaliacaoRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Avaliação não encontrada: " + id
            ));
    }

    private AvaliacaoResponseDTO converter(Avaliacao a) {
        return new AvaliacaoResponseDTO(
            a.getId(),
            a.getNota(),
            a.getComentario(),
            a.getDataAvaliacao(),
            a.getUsuario().getId(),
            a.getEstabelecimento().getId()
        );
    }
}