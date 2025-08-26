package plataforma.exticao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import plataforma.exticao.dtos.SeresRequestDTO;
import plataforma.exticao.dtos.SeresListDTO;
import plataforma.exticao.model.*;
import plataforma.exticao.repository.CategoriaRepository;
import plataforma.exticao.repository.EspecieRepository;
import plataforma.exticao.repository.TipoRepository;
import plataforma.exticao.service.SeresService;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return ResponseEntity.ok(seresService.registrar(dto));
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
            @RequestParam(required = false) MultipartFile imagemFile
    ) {
        List<Tipo> tipos = tipoRepository.findByNomeInIgnoreCase(List.of(tipoNome));
        if (tipos.isEmpty()) throw new RuntimeException("Tipo não encontrado");
        Tipo tipoEntity = tipos.get(0);

        List<Especie> especies = especieRepository.findByNomeInIgnoreCase(List.of(especieNome));
        if (especies.isEmpty()) throw new RuntimeException("Espécie não encontrada");
        Especie especieEntity = especies.get(0);

        Categoria categoria = especieEntity.getCategoria();
        if (categoria == null) throw new IllegalArgumentException("Categoria não encontrada para a espécie");

        Seres novoSeres = seresService.registrarMultipart(
                nomeComum, nomeCientifico, tipoEntity, especieEntity,
                descricao, StatusConservacao.valueOf(statusConservacao),
                latitude, longitude, usuarioLogin, usuarioEmail,
                imagemFile, categoria
        );

        return ResponseEntity.ok(novoSeres);
    }

    // ------------------------ READ ------------------------
    @GetMapping
    public ResponseEntity<List<SeresListDTO>> listarTodos() {
        List<SeresListDTO> dtos = seresService.listarTodas()
                .stream()
                .map(SeresListDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/pendentes")
    public ResponseEntity<List<SeresListDTO>> listarPendentes() {
        List<SeresListDTO> dtos = seresService.listarPendentes()
                .stream()
                .map(SeresListDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seres> buscarPorId(@PathVariable Long id) {
        return seresService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ------------------------ UPDATE ------------------------
    @PutMapping("/{id}")
    public ResponseEntity<Seres> atualizar(@PathVariable Long id,
                                           @RequestBody SeresRequestDTO dto,
                                           @RequestParam String usuarioLogin,
                                           @RequestParam String usuarioEmail) {
        Usuario usuarioAutenticado = seresService.buscarUsuario(usuarioLogin, usuarioEmail);
        return ResponseEntity.ok(seresService.atualizar(id, dto, usuarioAutenticado));
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
        return ResponseEntity.ok(seresService.aprovar(id, aprovadoPor));
    }

    // ------------------------ FILTRAGEM FLEXÍVEL ------------------------
    @GetMapping("/filtrar")
    public ResponseEntity<List<SeresListDTO>> filtrar(
            @RequestParam(required = false) String nomeComum,
            @RequestParam(required = false) String especie,
            @RequestParam(required = false) String statusConservacao,
            @RequestParam(required = false) String statusAprovacao) // <- adicionado
    {
        List<Especie> especies = null;
        if (especie != null && !especie.isBlank()) {
            especies = especieRepository.findByNomeInIgnoreCase(List.of(especie));
            if (especies.isEmpty()) throw new RuntimeException("Espécie não encontrada");
        }

        StatusConservacao statusEnum = statusConservacao != null ? StatusConservacao.valueOf(statusConservacao) : null;
        StatusAprovacao statusAprovEnum = statusAprovacao != null ? StatusAprovacao.valueOf(statusAprovacao) : null; // <- aqui converte string para enum

        List<Seres> filtrados = seresService.filtrarSeres(nomeComum, especies, statusEnum, statusAprovEnum);

        List<SeresListDTO> dtos = filtrados.stream()
                .map(SeresListDTO::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }


    // ------------------------ GET IMAGEM ------------------------
    @GetMapping("/{id}/imagem")
    public ResponseEntity<String> buscarImagem(@PathVariable Long id) {
        return seresService.buscarPorId(id)
                .map(s -> s.getImagem()) // imagem em Base64
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/atualizar-multipart")
    public ResponseEntity<Seres> atualizarMultipart(
            @PathVariable Long id,
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
            @RequestParam(required = false) MultipartFile imagemFile
    ) {
        try {
            Usuario usuarioAutenticado = seresService.buscarUsuario(usuarioLogin, usuarioEmail);

            SeresRequestDTO dto = new SeresRequestDTO();
            dto.setNomeComum(nomeComum);
            dto.setNomeCientifico(nomeCientifico);

            // Buscar Tipo
            List<Tipo> tipos = tipoRepository.findByNomeInIgnoreCase(List.of(tipoNome));
            if (tipos.isEmpty()) throw new RuntimeException("Tipo não encontrado");
            dto.setTipo(tipos.get(0));

            // Buscar Especie
            List<Especie> especies = especieRepository.findByNomeInIgnoreCase(List.of(especieNome));
            if (especies.isEmpty()) throw new RuntimeException("Espécie não encontrada");
            dto.setEspecie(especies.get(0));

            dto.setDescricao(descricao);
            dto.setStatusConservacao(StatusConservacao.valueOf(statusConservacao));
            dto.setLatitude(latitude);
            dto.setLongitude(longitude);

            // Converter imagem para Base64 se fornecida
            if (imagemFile != null && !imagemFile.isEmpty()) {
                dto.setImagem(Base64.getEncoder().encodeToString(imagemFile.getBytes()));
            }

            return ResponseEntity.ok(seresService.atualizar(id, dto, usuarioAutenticado));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

}
