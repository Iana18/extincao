package plataforma.exticao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import plataforma.exticao.dtos.SeresRequestDTO;
import plataforma.exticao.model.*;
import plataforma.exticao.repository.CategoriaRepository;
import plataforma.exticao.repository.EspecieRepository;
import plataforma.exticao.repository.TipoRepository;
import plataforma.exticao.service.SeresService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/seres")
public class SeresController {

    private final SeresService seresService;
    private final TipoRepository tipoRepository;
    private final EspecieRepository especieRepository;
    private final CategoriaRepository categoriaRepository;

    public SeresController(SeresService seresService,
                           TipoRepository tipoRepository,
                           EspecieRepository especieRepository,
                           CategoriaRepository categoriaRepository) {
        this.seresService = seresService;
        this.tipoRepository = tipoRepository;
        this.especieRepository = especieRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // ------------------------ CREATE ------------------------
    @PostMapping("/registrar")
    public ResponseEntity<Seres> registrar(@RequestBody SeresRequestDTO dto) {
        Seres novoSeres = seresService.registrar(dto);
        return ResponseEntity.ok(novoSeres);
    }

    @PostMapping("/registrar-multipart")
    public ResponseEntity<Seres> registrarMultipart(
            @RequestParam String nomeComum,
            @RequestParam String nomeCientifico,
            @RequestParam Long tipoId,
            @RequestParam Long especieId,
            @RequestParam String descricao,
            @RequestParam String statusConservacao,
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam String usuarioLogin,
            @RequestParam String usuarioEmail,
            @RequestParam(required = false) MultipartFile imagemFile,
            @RequestParam Long categoriaId
    ) {
        Tipo tipo = tipoRepository.findById(tipoId)
                .orElseThrow(() -> new RuntimeException("Tipo não encontrado: " + tipoId));
        Especie especie = especieRepository.findById(especieId)
                .orElseThrow(() -> new RuntimeException("Especie não encontrada: " + especieId));
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada: " + categoriaId));

        Seres novoSeres = seresService.registrarMultipart(
                nomeComum, nomeCientifico, tipo, especie, descricao,
                StatusConservacao.valueOf(statusConservacao.toUpperCase()),
                latitude, longitude, usuarioLogin, usuarioEmail, imagemFile, categoria
        );

        return ResponseEntity.ok(novoSeres);
    }

    // ------------------------ READ ------------------------
    @GetMapping
    public ResponseEntity<List<Seres>> listarTodos() {
        return ResponseEntity.ok(seresService.listarTodas());
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<Seres>> listarPendentes() {
        return ResponseEntity.ok(seresService.listarPendentes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seres> buscarPorId(@PathVariable Long id) {
        Optional<Seres> seres = seresService.buscarPorId(id);
        return seres.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ------------------------ UPDATE ------------------------
    @PutMapping("/{id}")
    public ResponseEntity<Seres> atualizar(@PathVariable Long id,
                                           @RequestBody SeresRequestDTO dto,
                                           @RequestParam String usuarioLogin,
                                           @RequestParam String usuarioEmail) {
        Usuario usuarioAutenticado = seresService.buscarUsuario(usuarioLogin, usuarioEmail);
        Seres atualizado = seresService.atualizar(id, dto, usuarioAutenticado);
        return ResponseEntity.ok(atualizado);
    }

    // ------------------------ DELETE ------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id,
                                        @RequestParam String usuarioLogin,
                                        @RequestParam String usuarioEmail) {
        Usuario usuarioAutenticado = seresService.buscarUsuario(usuarioLogin, usuarioEmail);
        seresService.deletar(id, usuarioAutenticado);
        return ResponseEntity.noContent().build();
    }

    // ------------------------ APPROVE ------------------------
    @PostMapping("/{id}/aprovar")
    public ResponseEntity<Seres> aprovar(@PathVariable Long id,
                                         @RequestParam String usuarioLogin,
                                         @RequestParam String usuarioEmail) {
        Usuario aprovadoPor = seresService.buscarUsuario(usuarioLogin, usuarioEmail);
        Seres aprovado = seresService.aprovar(id, aprovadoPor);
        return ResponseEntity.ok(aprovado);
    }

    // ------------------------ FILTRAGEM ------------------------
    @GetMapping("/filtrar")
    public ResponseEntity<List<Seres>> filtrar(@RequestParam(required = false) String nomeComum,
                                               @RequestParam(required = false) Long especieId,
                                               @RequestParam(required = false) String statusConservacao) {
        Especie especie = null;
        if (especieId != null) {
            especie = especieRepository.findById(especieId)
                    .orElseThrow(() -> new RuntimeException("Especie não encontrada: " + especieId));
        }

        List<Seres> filtrados = seresService.filtrarSeres(
                nomeComum,
                especie,
                statusConservacao != null ? StatusConservacao.valueOf(statusConservacao.toUpperCase()) : null
        );

        return ResponseEntity.ok(filtrados);
    }
}
