package br.edu.iftm.rastreamento.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.iftm.rastreamento.dto.PacoteDTO;
import br.edu.iftm.rastreamento.model.Endereco;
import br.edu.iftm.rastreamento.model.Pacote;
import br.edu.iftm.rastreamento.model.Rastreamento;
import br.edu.iftm.rastreamento.repository.EnderecoRepository;
import br.edu.iftm.rastreamento.repository.PacoteRepository;
import br.edu.iftm.rastreamento.repository.RastreamentoRepository;
import br.edu.iftm.rastreamento.service.exceptions.NaoAcheiException;
import br.edu.iftm.rastreamento.service.util.Converters;

@Service
public class PacoteService {

    @Autowired
    private PacoteRepository pacoteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private RastreamentoRepository rastreamentoRepository;
    @Autowired
    private Converters converters;

    public List<PacoteDTO> getAllPacotes() {
        Iterable<Pacote> pacotesIterable = pacoteRepository.findAll();
        List<Pacote> pacotesList = new ArrayList<>();
        pacotesIterable.forEach(pacotesList::add);
        return pacotesList.stream().map((pacote) -> converters.convertToDTO(pacote)).collect(Collectors.toList());
    }

    public PacoteDTO getPacoteById(Long id) {
        Pacote pacote = pacoteRepository.findById(id).orElseThrow(() -> new NaoAcheiException("O ID não foi encontrado"));
        return converters.convertToDTO(pacote);
    }

    public PacoteDTO createPacote(PacoteDTO pacoteDTO) {
        Endereco endereco = enderecoRepository.findById(pacoteDTO.getEndereco().getId()).get();
        Pacote pacote = converters.convertToEntity(pacoteDTO);
        pacote.setEndereco(endereco);
        Pacote savedPacote = pacoteRepository.save(pacote);
        Rastreamento rastreamento = pacote.getRastreamentos().get(pacote.getRastreamentos().size() - 1);
        rastreamentoRepository.save(rastreamento);
        return converters.convertToDTO(savedPacote);
    }

    public PacoteDTO updatePacote(Long id, PacoteDTO pacoteDTO) {
        Pacote pacoteVerificado = pacoteRepository.findById(id).orElseThrow(() -> new NaoAcheiException("O ID não foi encontrado"));
        pacoteVerificado.setId(id);   
        Pacote updatedPacote = pacoteRepository.save(pacoteVerificado);
        return converters.convertToDTO(updatedPacote);
    }

    public List<PacoteDTO> findByStatus(String status) {
        Iterable<Pacote> pacotesIterable = pacoteRepository.findPacotesByStatus(status);
        List<Pacote> pacotesList = new ArrayList<>();
        pacotesIterable.forEach(pacotesList::add);
        return pacotesList.stream().map((pacote) -> converters.convertToDTO(pacote)).collect(Collectors.toList());
    }
    public List<PacoteDTO> findByDestinatario(String destinatario) {
        Iterable<Pacote> pacotesIterable = pacoteRepository.findPacotesByDestinatario(destinatario);
        List<Pacote> pacotesList = new ArrayList<>();
        pacotesIterable.forEach(pacotesList::add);
        return pacotesList.stream().map((pacote) -> converters.convertToDTO(pacote)).collect(Collectors.toList());
    }
}