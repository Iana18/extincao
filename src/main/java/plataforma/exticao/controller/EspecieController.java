package plataforma.exticao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import plataforma.exticao.dtos.SeresRequestDTO;
import plataforma.exticao.model.*;
import plataforma.exticao.repository.SeresRepository;
import plataforma.exticao.repository.UsuarioRepository;
import plataforma.exticao.service.SeresService;

import java.util.List;

@RestController
@RequestMapping("/api/especies")
public class EspecieController {

    private final SeresService especieService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SeresRepository especieRepository;

    public EspecieController(SeresService especieService) {
        this.especieService = especieService;
    }

    // POST: Registrar nova espécie via JSON (DTO)
    @PostMapping("registrar")
    public Seres registrar(@RequestBody SeresRequestDTO dto) {
        return especieService.registrar(dto);
    }

    // POST registrar espécie com imagem via multipart/form-data
    @PostMapping(value = "/registrar-multipart", consumes = {"multipart/form-data"})
    public Seres registrarMultipart(
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
    public ResponseEntity<Seres> aprovar(@PathVariable Long id, @RequestBody Seres dadosAprovacao) {
        try {
            Seres aprovada = especieService.aprovar(id, dadosAprovacao);
            return ResponseEntity.ok(aprovada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT: Atualizar espécie (só admin)
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody SeresRequestDTO dto) {
        try {
            // Pega o ID do usuário autenticado do SecurityContext
            String usuarioId = SecurityContextHolder.getContext().getAuthentication().getName();
            Seres atualizada = especieService.atualizar(id, dto, usuarioId);
            return ResponseEntity.ok(atualizada);
        } catch (SecurityException se) {
            return ResponseEntity.status(403).body(se.getMessage());
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: Deletar espécie (só admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            String usuarioId = SecurityContextHolder.getContext().getAuthentication().getName();
            especieService.deletar(id, usuarioId);
            return ResponseEntity.noContent().build();
        } catch (SecurityException se) {
            return ResponseEntity.status(403).body(se.getMessage());
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.notFound().build();
        }
    }

    // GET: Listar todas as espécies
    @GetMapping
    public ResponseEntity<List<Seres>> listarTodas() {
        return ResponseEntity.ok(especieService.listarTodas());
    }

    // GET: Listar espécies pendentes
    @GetMapping("/pendentes")
    public ResponseEntity<List<Seres>> listarPendentes() {
        return ResponseEntity.ok(especieService.listarPendentes());
    }

    // GET: Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Seres> buscarPorId(@PathVariable Long id) {
        return especieService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET: Buscar por nome
    @GetMapping("/buscar/nome")
    public ResponseEntity<List<Seres>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(especieService.buscarPorNome(nome));
    }

    // GET: Buscar por tipo
    @GetMapping("/buscar/tipo")
    public ResponseEntity<List<Seres>> buscarPorTipo(@RequestParam TipoEspecie tipo) {
        return ResponseEntity.ok(especieService.buscarPorTipo(tipo));
    }

    // GET: Buscar por status de conservação
    @GetMapping("/buscar/status")
    public ResponseEntity<List<Seres>> buscarPorStatus(@RequestParam StatusConservacao status) {
        return ResponseEntity.ok(especieService.buscarPorStatusConservacao(status));
    }

    // GET: Listar com filtros
    @GetMapping("/Filtros")
    public ResponseEntity<List<Seres>> listarComFiltros(
            @RequestParam(required = false) String nomeComum,
            @RequestParam(required = false) TipoEspecie tipo,
            @RequestParam(required = false) StatusConservacao status) {

        List<Seres> especiesFiltradas = especieService.filtrar(nomeComum, tipo, status);
        return ResponseEntity.ok(especiesFiltradas);
    }
}
