package br.edu.iff.ccc.bsi.foreverfashion.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Cargo;
import br.edu.iff.ccc.bsi.foreverfashion.exception.IdNaoEncontrado;
import br.edu.iff.ccc.bsi.foreverfashion.exception.JaCadastrado;
import br.edu.iff.ccc.bsi.foreverfashion.repository.CargoRepository;
import jakarta.transaction.Transactional;

@Service
public class CargoService {
    private final CargoRepository cargoRepository;

    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    @Transactional
    public Cargo create(Cargo cargo) {
        if (cargoRepository.existsByDescricao(cargo.getDescricao())) {
            throw new JaCadastrado("Cargo já cadastrado com o nome fornecido.");
        }   
        return cargoRepository.save(cargo);
    } 

    public List<Cargo> readAll() {
        return cargoRepository.findAll();
    }

    @Transactional
    public Cargo update(Long id, Cargo cargo){
        if(!cargoRepository.existsById(id)){
            throw new IdNaoEncontrado("Cargo não encontrado com ID "+id);
        }
        cargo.setId_cargo(id);
        return cargoRepository.save(cargo);
    }

    @Transactional
    public void delete(Long id){
        if(!cargoRepository.existsById(id)){
            throw new IdNaoEncontrado("Cargo não encontrado com ID "+id);
        }
        cargoRepository.deleteById(id);
    }

    public Cargo readById(Long id){
        return cargoRepository.findById(id).orElseThrow(() -> new IdNaoEncontrado("Cargo não encontrado com ID "+id));
    }
}
