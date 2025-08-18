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
@RequestMapping("/api/seres")
public class SeresController {

    private final SeresService seresService;
    private final TipoRepository tipoRepository;
    private final EspecieRepository especieRepository;
    private final CategoriaRepository categoriaRepository;

    public SeresController(SeresService seresService, CategoriaRepository categoriaRepository,
                           EspecieRepository especieRepository, TipoRepository tipoRepository) {
        this.seresService = seresService;
        this.categoriaRepository = categoriaRepository;
        this.tipoRepository = tipoRepository;
        this.especieRepository = especieRepository;
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
            @RequestParam String tipoNome,
            @RequestParam String especieNome,
            @RequestParam String descricao,
            @RequestParam String statusConservacao,
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam String usuarioLogin,
            @RequestParam String usuarioEmail,
            @RequestParam(required = false) MultipartFile imagemFile,
            @RequestParam String categoriaNome
    ) {
        // Buscar Tipo, Especie e Categoria no banco
        Tipo tipoEntity = tipoRepository.findByNome(tipoNome)
                .orElseThrow(() -> new RuntimeException("Tipo não encontrado"));

        Especie especieEntity = especieRepository.findByNome(especieNome)
                .orElseThrow(() -> new RuntimeException("Espécie não encontrada"));

        Categoria categoria = categoriaRepository.findByNome(categoriaNome)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada: " + categoriaNome));

        // Registrar o Seres usando o service
        Seres novoSeres = seresService.registrarMultipart(
                nomeComum,
                nomeCientifico,
                tipoEntity,
                especieEntity,
                descricao,
                StatusConservacao.valueOf(statusConservacao),
                latitude,
                longitude,
                usuarioLogin,
                usuarioEmail,
                imagemFile,
                categoria
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
                                               @RequestParam(required = false) String especie,
                                               @RequestParam(required = false) String statusConservacao) {

        Especie especieEntity = null;
        if (especie != null) {
            especieEntity = especieRepository.findByNome(especie)
                    .orElseThrow(() -> new RuntimeException("Espécie não encontrada"));
        }

        StatusConservacao statusEnum = statusConservacao != null ? StatusConservacao.valueOf(statusConservacao) : null;

        List<Seres> filtrados = seresService.filtrarSeres(nomeComum, especieEntity, statusEnum);

        return ResponseEntity.ok(filtrados);
    }
}
