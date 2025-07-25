package plataforma.exticao.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import plataforma.exticao.model.Denuncia;
import plataforma.exticao.model.Especie;
import plataforma.exticao.model.StatusAprovacao;
import plataforma.exticao.model.Usuario;
import plataforma.exticao.repository.DenunciaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import plataforma.exticao.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

    @Service
    public class DenunciaService {

        private final DenunciaRepository denunciaRepository;
        private final UsuarioRepository usuarioRepository;

        public DenunciaService(DenunciaRepository denunciaRepository, UsuarioRepository usuarioRepository) {
            this.denunciaRepository = denunciaRepository;
            this.usuarioRepository = usuarioRepository;
        }

        public Denuncia registrarMultipart(
                String titulo,
                String descricao,
                Long especieId,
                String denunciadoPorId,
                Double latitude,
                Double longitude,
                MultipartFile imagemFile
        ) {
            Usuario denunciadoPor = usuarioRepository.findById(denunciadoPorId)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

            Especie especie = null;
            if (especieId != null) {
                especie = new Especie();
                especie.setId(especieId);
            }

            String base64Imagem = null;
            try {
                if (imagemFile != null && !imagemFile.isEmpty()) {
                    base64Imagem = Base64.getEncoder().encodeToString(imagemFile.getBytes());
                }
            } catch (Exception e) {
                throw new RuntimeException("Erro ao processar imagem");
            }

            Denuncia denuncia = new Denuncia();
            denuncia.setTitulo(titulo);
            denuncia.setDescricao(descricao);
            denuncia.setDenunciadoPor(denunciadoPor);
            denuncia.setImagem(base64Imagem);
            denuncia.setEspecie(especie);
            denuncia.setLatitude(latitude);
            denuncia.setLongitude(longitude);
            denuncia.setDataDenuncia(LocalDateTime.now());
            denuncia.setStatusAprovacao(StatusAprovacao.PENDENTE);
            denuncia.setAprovadoPor(null);

            return denunciaRepository.save(denuncia);
        }

        public List<Denuncia> listarTodas() {
            return denunciaRepository.findAll();
        }

        public List<Denuncia> listarPendentes() {
            return denunciaRepository.findByStatusAprovacao(StatusAprovacao.PENDENTE);
        }

        public Optional<Denuncia> buscarPorId(Long id) {
            return denunciaRepository.findById(id);
        }
    }
