package br.com.caminholivre.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.caminholivre.dto.request.DenunciaRequestDTO;
import br.com.caminholivre.dto.response.DenunciaResponseDTO;
import br.com.caminholivre.entity.Denuncia;
import br.com.caminholivre.entity.Usuario;
import br.com.caminholivre.exception.ResourceNotFoundException;
import br.com.caminholivre.repository.DenunciaRepository;
import br.com.caminholivre.repository.UsuarioRepository;

@Service
public class DenunciaService {

    private final DenunciaRepository denunciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ApplicationEventPublisher eventPublisher;

    public DenunciaService(
            DenunciaRepository denunciaRepository,
            UsuarioRepository usuarioRepository,
            ApplicationEventPublisher eventPublisher) {
        this.denunciaRepository = denunciaRepository;
        this.usuarioRepository = usuarioRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public DenunciaResponseDTO criar(
            DenunciaRequestDTO dto, Long usuarioId) {

        Usuario usuario = usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Usuário não encontrado: " + usuarioId
            ));

        Denuncia denuncia = new Denuncia();
        denuncia.setUsuario(usuario);
        atualizarDados(denuncia, dto);

        return converter(denunciaRepository.save(denuncia));
        
        
    }

    @Transactional(readOnly = true)
    public Page<DenunciaResponseDTO> listar(Pageable pageable) {
        return denunciaRepository.findAll(pageable)
            .map(this::converter);
    }

    @Transactional(readOnly = true)
    public DenunciaResponseDTO buscarPorId(Long id) {
        return converter(buscarEntidade(id));
    }

    @Transactional
    public DenunciaResponseDTO atualizar(
            Long id, DenunciaRequestDTO dto) {

        Denuncia denuncia = buscarEntidade(id);
        atualizarDados(denuncia, dto);

        return converter(denunciaRepository.save(denuncia));
    }

    @Transactional
    public void excluir(Long id) {
        denunciaRepository.delete(buscarEntidade(id));
    }

    private Denuncia buscarEntidade(Long id) {
        return denunciaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(
                "Denúncia não encontrada: " + id
            ));
    }

    private void atualizarDados(
            Denuncia denuncia,
            DenunciaRequestDTO dto) {

        denuncia.setTitulo(dto.getTitulo());
        denuncia.setDescricao(dto.getDescricao());
        denuncia.setLatitude(dto.getLatitude());
        denuncia.setLongitude(dto.getLongitude());
        denuncia.setFotoUrl(dto.getFotoUrl());
        denuncia.setCategoria(dto.getCategoria());
    }

    private DenunciaResponseDTO converter(Denuncia d) {
        return new DenunciaResponseDTO(
            d.getId(),
            d.getTitulo(),
            d.getDescricao(),
            d.getLatitude(),
            d.getLongitude(),
            d.getFotoUrl(),
            d.getCategoria(),
            d.getStatus(),
            d.getGravidade(),
            d.getResumoIA(),
            d.getDataCriacao(),
            d.getUsuario().getId(),
            d.getRiscosIA(),
            d.getStatusAnaliseIA()
        );
    }
}
