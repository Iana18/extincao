package plataforma.exticao.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import plataforma.exticao.dtos.DenunciaCreateRequestDTO;
import plataforma.exticao.dtos.DenunciaResponseDTO;
import plataforma.exticao.dtos.DenunciaUpdateRequestDTO;
import plataforma.exticao.model.Denuncia;
import plataforma.exticao.model.Especie;
import plataforma.exticao.model.Seres;
import plataforma.exticao.model.Usuario;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import plataforma.exticao.repository.EspecieRepository;
import plataforma.exticao.repository.SeresRepository;
import plataforma.exticao.repository.UsuarioRepository;
import plataforma.exticao.service.DenunciaService;
import plataforma.exticao.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/denuncias")
public class DenunciaController {

    private final DenunciaService denunciaService;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final EspecieRepository especieRepository;

    public DenunciaController(DenunciaService denunciaService, EspecieRepository especieRepository,
                              UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.denunciaService = denunciaService;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
        this.especieRepository = especieRepository;
    }

    // ------------------------ CREATE ------------------------
    @PostMapping
    public ResponseEntity<DenunciaResponseDTO> criarDenuncia(@RequestBody DenunciaCreateRequestDTO dto) {
        Denuncia denuncia = denunciaService.registrar(dto);
        return ResponseEntity.ok(denunciaService.mapToDTO(denuncia));
    }

    @PostMapping("/upload")
    public ResponseEntity<DenunciaResponseDTO> criarDenunciaComImagem(
            @RequestParam String titulo,
            @RequestParam String descricao,
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(required = false) Long especieId,
            @RequestParam String usuarioLogin,
            @RequestParam String usuarioEmail,
            @RequestParam(required = false) MultipartFile imagem) {

        Denuncia denuncia = denunciaService.registrarMultipart(
                titulo, descricao, latitude, longitude, especieId, usuarioLogin, usuarioEmail, imagem
        );
        return ResponseEntity.ok(denunciaService.mapToDTO(denuncia));
    }

    // ------------------------ READ ------------------------
    @GetMapping
    public ResponseEntity<List<DenunciaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(denunciaService.listarTodas());
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<DenunciaResponseDTO>> listarPendentes() {
        return ResponseEntity.ok(denunciaService.listarPendentes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DenunciaResponseDTO> buscarPorId(@PathVariable Long id) {
        return denunciaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ------------------------ UPDATE STATUS ------------------------
    @PatchMapping("/{id}/aprovar")
    public ResponseEntity<DenunciaResponseDTO> aprovarDenuncia(@PathVariable Long id,
                                                               @AuthenticationPrincipal Usuario usuario) {


        if (usuario == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        return ResponseEntity.ok(denunciaService.mapToDTO(
                denunciaService.aprovarDenuncia(id, usuario)
        ));
    }

    @PatchMapping("/{id}/rejeitar")
    public ResponseEntity<DenunciaResponseDTO> rejeitarDenuncia(@PathVariable Long id,
                                                                @RequestBody Usuario usuarioAutenticado) {
        return ResponseEntity.ok(denunciaService.mapToDTO(
                denunciaService.rejeitarDenuncia(id, usuarioAutenticado)
        ));
    }

    @PatchMapping("/{id}/resolver")
    public ResponseEntity<DenunciaResponseDTO> resolverDenuncia(@PathVariable Long id,
                                                                @RequestBody Usuario usuarioAutenticado) {
        return ResponseEntity.ok(denunciaService.mapToDTO(
                denunciaService.resolverDenuncia(id, usuarioAutenticado)
        ));
    }

    // ------------------------ DELETE ------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id, Authentication authentication) {
        String login = authentication.getName();
        denunciaService.deletar(id, login);
        return ResponseEntity.noContent().build();
    }

    // ------------------------ SEARCH ------------------------
    @GetMapping("/buscar")
    public ResponseEntity<List<DenunciaResponseDTO>> buscar(@RequestParam("q") String termo) {
        return ResponseEntity.ok(denunciaService.buscarPorTituloOuDescricao(termo));
    }

    // ------------------------ UPDATE DENUNCIA ------------------------
    @PutMapping("/{id}/atualizar-por-nome")
    public ResponseEntity<DenunciaResponseDTO> atualizarDenunciaPorNome(
            @PathVariable Long id,
            @RequestBody DenunciaUpdateRequestDTO dto
    ) {
        try {
            Usuario usuarioAutenticado = usuarioRepository
                    .findByLoginAndEmail(dto.getUsuarioLogin(), dto.getUsuarioEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

            Denuncia denunciaAtualizada = denunciaService.atualizar(id, dto, usuarioAutenticado);
            return ResponseEntity.ok(denunciaService.mapToDTO(denunciaAtualizada));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

}

