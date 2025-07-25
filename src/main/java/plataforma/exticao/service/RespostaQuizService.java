package plataforma.exticao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plataforma.exticao.model.RespostaQuiz;
import plataforma.exticao.repository.RespostaQuizRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RespostaQuizService {

    @Autowired
    private RespostaQuizRepository respostaQuizRepository;

    public List<RespostaQuiz> listarTodos() {
        return respostaQuizRepository.findAll();
    }

    public List<RespostaQuiz> listarPorPerguntaId(Long perguntaId) {
        return respostaQuizRepository.findByPerguntaId(perguntaId);
    }

    public Optional<RespostaQuiz> buscarPorId(Long id) {
        return respostaQuizRepository.findById(id);
    }

    public RespostaQuiz salvar(RespostaQuiz respostaQuiz) {
        return respostaQuizRepository.save(respostaQuiz);
    }

    public void deletar(Long id) {
        respostaQuizRepository.deleteById(id);
    }
}
