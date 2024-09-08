package br.edu.iftm.rastreamento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.iftm.rastreamento.dto.PacoteDTO;
import br.edu.iftm.rastreamento.service.PacoteService;

@RestController
@RequestMapping("/pacotes")
public class PacoteController {

	@Autowired
	private PacoteService pacoteService;

	@GetMapping
	public List<PacoteDTO> getAllPacotes() {
		return pacoteService.getAllPacotes();
	}

	@GetMapping("{id}")
	public PacoteDTO getPacoteById(@PathVariable Long id) {
		PacoteDTO pacoteDTO = pacoteService.getPacoteById(id);
		return pacoteDTO;
		
	}

	@PostMapping
	public PacoteDTO createPacote(@RequestBody PacoteDTO pacoteDTO) {
		return pacoteService.createPacote(pacoteDTO);
	}
	
	@PutMapping("{id}")
	public PacoteDTO updatePacote(@PathVariable Long id, @RequestBody PacoteDTO pacoteDTO) {
		PacoteDTO updatedPacote = pacoteService.updatePacote(id, pacoteDTO);

		return updatedPacote;
	}
	@GetMapping("/status")
	public List<PacoteDTO> findByStatus(@RequestParam String status) {
        return pacoteService.findByStatus(status);
    }
	
	@GetMapping("/destinatario")
    public List<PacoteDTO> findByDestinatario(@RequestParam String destinatario) {
        return pacoteService.findByDestinatario(destinatario);
    }
}