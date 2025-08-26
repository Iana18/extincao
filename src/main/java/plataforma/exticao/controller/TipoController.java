package plataforma.exticao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plataforma.exticao.dtos.TipoRequestDTO;
import plataforma.exticao.dtos.TipoResponseDTO;
import plataforma.exticao.model.Tipo;
import plataforma.exticao.service.TipoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tipos")
public class TipoController {

    @Autowired
    private TipoService tipoService;

    // Buscar todos os tipos (DTO)
    @GetMapping
    public ResponseEntity<List<TipoResponseDTO>> getAllDTO() {
        List<TipoResponseDTO> tiposDTO = tipoService.findAllDTO();
        return ResponseEntity.ok(tiposDTO);
    }

    // Buscar tipo por ID
    @GetMapping("/{id}")
    public ResponseEntity<TipoResponseDTO> getById(@PathVariable Long id) {
        return tipoService.findById(id)
                .map(tipoService::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar novo tipo (aceita lista ou um único TipoRequestDTO)
    @PostMapping("/criar")
    public ResponseEntity<List<TipoResponseDTO>> create(@RequestBody List<TipoRequestDTO> tiposDTO) {
        List<TipoResponseDTO> result = tiposDTO.stream()
                .map(tipoService::saveFromDTO)
                .map(tipoService::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    // Atualizar tipo existente
    @PutMapping("/{id}")
    public ResponseEntity<TipoResponseDTO> update(@PathVariable Long id, @RequestBody TipoRequestDTO dto) {
        try {
            Tipo tipoAtualizado = tipoService.update(id, dto);
            return ResponseEntity.ok(tipoService.toDTO(tipoAtualizado));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar tipo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            tipoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
