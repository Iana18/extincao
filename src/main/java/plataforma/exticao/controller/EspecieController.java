package plataforma.exticao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import plataforma.exticao.dtos.EspecieRequestDTO;
import plataforma.exticao.model.*;
import plataforma.exticao.repository.EspecieRepository;
import plataforma.exticao.repository.UsuarioRepository;
import plataforma.exticao.service.EspecieService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/especies")
//@CrossOrigin(origins = "*")
public class EspecieController {

    private final EspecieService especieService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    EspecieRepository especieRepository;

    public EspecieController(EspecieService especieService) {
        this.especieService = especieService;
    }

    // POST: Registrar nova espécie via JSON (DTO)
    @PostMapping("registrar")
    public Especie registrar(@RequestBody EspecieRequestDTO dto) {
        return especieService.registrar(dto);
    }

    // NOVO: POST registrar espécie com imagem via multipart (para XMLHttpRequest + FormData)
    // NOVO: POST registrar espécie com imagem via multipart (para XMLHttpRequest + FormData)
    @PostMapping(value = "/registrar-multipart", consumes = {"multipart/form-data"})
    public Especie registrarMultipart(
            @RequestParam String nomeComum,
            @RequestParam String nomeCientifico,
            @RequestParam TipoEspecie tipo,
            @RequestParam String descricao,
            @RequestParam StatusConservacao statusConservacao,
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam String registradoPorId,
            @RequestParam("imagem") MultipartFile imagemFile
    ) {
        return especieService.registrarMultipart(
                nomeComum,
                nomeCientifico,
                tipo,
                descricao,
                statusConservacao,
                latitude,
                longitude,
                registradoPorId,
                imagemFile
        );
    }

    // PUT: Aprovar espécie
    @PutMapping("/{id}/aprovar")
    public ResponseEntity<Especie> aprovar(@PathVariable Long id, @RequestBody Especie dadosAprovacao) {
        try {
            Especie aprovada = especieService.aprovar(id, dadosAprovacao);
            return ResponseEntity.ok(aprovada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET: Listar todas as espécies
    @GetMapping
    public ResponseEntity<List<Especie>> listarTodas() {
        return ResponseEntity.ok(especieService.listarTodas());
    }

    // GET: Listar espécies pendentes
    @GetMapping("/pendentes")
    public ResponseEntity<List<Especie>> listarPendentes() {
        return ResponseEntity.ok(especieService.listarPendentes());
    }

    // GET: Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Especie> buscarPorId(@PathVariable Long id) {
        return especieService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET: Buscar por nome
    @GetMapping("/buscar/nome")
    public ResponseEntity<List<Especie>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(especieService.buscarPorNome(nome));
    }

    // GET: Buscar por tipo
    @GetMapping("/buscar/tipo")
    public ResponseEntity<List<Especie>> buscarPorTipo(@RequestParam TipoEspecie tipo) {
        return ResponseEntity.ok(especieService.buscarPorTipo(tipo));
    }

    // GET: Buscar por status de conservação
    @GetMapping("/buscar/status")
    public ResponseEntity<List<Especie>> buscarPorStatus(@RequestParam StatusConservacao status) {
        return ResponseEntity.ok(especieService.buscarPorStatusConservacao(status));
    }

    @GetMapping("/Filtros")
    public ResponseEntity<List<Especie>> listarComFiltros(
            @RequestParam(required = false) String nomeComum,
            @RequestParam(required = false) TipoEspecie tipo,
            @RequestParam(required = false) StatusConservacao status) {

        List<Especie> especiesFiltradas = especieService.filtrar(nomeComum, tipo, status);
        return ResponseEntity.ok(especiesFiltradas);
    }

}
