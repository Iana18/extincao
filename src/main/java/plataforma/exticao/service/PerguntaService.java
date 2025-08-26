package plataforma.exticao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plataforma.exticao.dtos.PerguntaComRespostasDTO;
import plataforma.exticao.dtos.RespostaDTO;
import plataforma.exticao.model.Pergunta;
import plataforma.exticao.model.Quiz;
import plataforma.exticao.model.RespostaQuiz;
import plataforma.exticao.repository.PerguntaRepository;
import plataforma.exticao.repository.QuizRepository; // <--- adicionado

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PerguntaService {

    @Autowired
    private PerguntaRepository perguntaRepository;

    @Autowired
    private QuizRepository quizRepository; // <--- substitui QuizService

    @Autowired
    private RespostaQuizService respostaQuizService;

    // Listar todas perguntas
    public List<Pergunta> listarTodos() {
        return perguntaRepository.findAll();
    }

    // Listar por Quiz
    public List<Pergunta> listarPorQuizId(Long quizId) {
        return perguntaRepository.findByQuizId(quizId);
    }

    // Buscar por ID
    public Optional<Pergunta> buscarPorId(Long id) {
        return perguntaRepository.findById(id);
    }

    // Buscar perguntas aleatórias limitadas
    public List<Pergunta> buscarPerguntasAleatorias(Long quizId, int quantidade) {
        List<Pergunta> perguntas = perguntaRepository.findByQuizId(quizId);
        Collections.shuffle(perguntas);
        if (perguntas.size() > quantidade) {
            return perguntas.subList(0, quantidade);
        }
        return perguntas;
    }

    // Salvar pergunta simples com validação
    public Pergunta salvar(Pergunta pergunta) {
        if (!validarPergunta(pergunta)) {
            throw new IllegalArgumentException("A pergunta deve ter 4 respostas, sendo apenas uma correta.");
        }
        return perguntaRepository.save(pergunta);
    }

    // Atualizar pergunta existente
    public Pergunta atualizar(Long id, Pergunta perguntaAtualizada) {
        Pergunta perguntaExistente = perguntaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pergunta não encontrada com ID: " + id));

        perguntaExistente.setTexto(perguntaAtualizada.getTexto());
        perguntaExistente.setQuiz(perguntaAtualizada.getQuiz());
        perguntaExistente.setRespostas(perguntaAtualizada.getRespostas());

        if (!validarPergunta(perguntaExistente)) {
            throw new IllegalArgumentException("A pergunta deve ter 4 respostas, sendo apenas uma correta.");
        }

        return perguntaRepository.save(perguntaExistente);
    }

    // Validar 4 respostas com 1 correta
    private boolean validarPergunta(Pergunta pergunta) {
        if (pergunta.getRespostas() == null || pergunta.getRespostas().size() != 4) {
            return false;
        }
        long countCorretas = pergunta.getRespostas().stream().filter(RespostaQuiz::isCorreta).count();
        return countCorretas == 1;
    }

    // Deletar pergunta
    public void deletar(Long id) {
        perguntaRepository.deleteById(id);
    }

    // ===== Novo método: salvar pergunta com respostas via DTO =====
    public Pergunta salvarComRespostas(PerguntaComRespostasDTO dto) {
        // Buscar Quiz diretamente pelo repository
        Quiz quiz = quizRepository.findById(dto.getQuizId())
                .orElseThrow(() -> new IllegalArgumentException("Quiz não encontrado"));

        // Criar Pergunta
        Pergunta pergunta = new Pergunta();
        pergunta.setTexto(dto.getTexto());
        pergunta.setQuiz(quiz);

        // Salvar pergunta para gerar ID
        Pergunta perguntaSalva = perguntaRepository.save(pergunta);

        // Validar respostas
        List<RespostaDTO> respostasDTO = dto.getRespostas();
        if (respostasDTO == null || respostasDTO.size() != 4) {
            throw new IllegalArgumentException("A pergunta deve ter exatamente 4 respostas.");
        }
        long countCorretas = respostasDTO.stream().filter(RespostaDTO::isCorreta).count();
        if (countCorretas != 1) {
            throw new IllegalArgumentException("Deve haver exatamente 1 resposta correta.");
        }

        // Criar respostas
        for (RespostaDTO r : respostasDTO) {
            RespostaQuiz resposta = new RespostaQuiz();
            resposta.setTexto(r.getTexto());
            resposta.setCorreta(r.isCorreta());
            resposta.setPergunta(perguntaSalva);
            respostaQuizService.salvar(resposta);
        }

        // Atualizar lista de respostas na pergunta (opcional)
        perguntaSalva.setRespostas(respostaQuizService.listarPorPerguntaId(perguntaSalva.getId()));

        return perguntaSalva;
    }
}
