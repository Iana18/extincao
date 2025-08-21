package plataforma.exticao.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import plataforma.exticao.dtos.DenunciaCreateRequestDTO;
import plataforma.exticao.dtos.DenunciaResponseDTO;
import plataforma.exticao.model.Denuncia;
import plataforma.exticao.model.Usuario;
import plataforma.exticao.service.DenunciaService;

import java.util.List;

@RestController
@RequestMapping("/api/denuncias")
public class DenunciaController {

    private final DenunciaService denunciaService;

    public DenunciaController(DenunciaService denunciaService) {
        this.denunciaService = denunciaService;
    }

    // ------------------------ CREATE ------------------------
    @PostMapping
    public ResponseEntity<DenunciaResponseDTO> criarDenuncia(@RequestBody DenunciaCreateRequestDTO dto) {
        Denuncia denuncia = denunciaService.registrar(dto); // retorna Denuncia
        DenunciaResponseDTO dtoResponse = denunciaService.mapToDTO(denuncia); // converte para DTO
        return ResponseEntity.ok(dtoResponse);
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
                titulo, descricao, latitude, longitude, especieId,
                usuarioLogin, usuarioEmail, imagem
        );
        DenunciaResponseDTO dtoResponse = denunciaService.mapToDTO(denuncia);
        return ResponseEntity.ok(dtoResponse);
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
                                                               @RequestBody Usuario usuarioAutenticado) {
        return ResponseEntity.ok(
                denunciaService.mapToDTO(
                        denunciaService.aprovarDenuncia(id, usuarioAutenticado)
                )
        );
    }

    @PatchMapping("/{id}/rejeitar")
    public ResponseEntity<DenunciaResponseDTO> rejeitarDenuncia(@PathVariable Long id,
                                                                @RequestBody Usuario usuarioAutenticado) {
        return ResponseEntity.ok(
                denunciaService.mapToDTO(
                        denunciaService.rejeitarDenuncia(id, usuarioAutenticado)
                )
        );
    }

    @PatchMapping("/{id}/resolver")
    public ResponseEntity<DenunciaResponseDTO> resolverDenuncia(@PathVariable Long id,
                                                                @RequestBody Usuario usuarioAutenticado) {
        return ResponseEntity.ok(
                denunciaService.mapToDTO(
                        denunciaService.resolverDenuncia(id, usuarioAutenticado)
                )
        );
    }

    // ------------------------ DELETE ------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id,
                                        @RequestBody Usuario usuarioAutenticado) {
        denunciaService.deletar(id, usuarioAutenticado);
        return ResponseEntity.noContent().build();
    }
}
