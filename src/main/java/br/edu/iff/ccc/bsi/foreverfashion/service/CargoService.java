package br.edu.iff.ccc.bsi.foreverfashion.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Cargo;
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
            throw new RuntimeException("Cargo já cadastrado com o nome fornecido.");
        }   
        return cargoRepository.save(cargo);
    } 

    public List<Cargo> readAll() {
        return cargoRepository.findAll();
    }

    @Transactional
    public Cargo update(Long id, Cargo cargo){
        if(!cargoRepository.existsById(id)){
            throw new RuntimeException("Cargo não encontrado");
        }
        cargo.setId_cargo(id);
        return cargoRepository.save(cargo);
    }

    @Transactional
    public boolean delete(Long id){
        if(cargoRepository.existsById(id)){
            cargoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Cargo> readById(Long id){
        if(!cargoRepository.existsById(id)){
           throw new RuntimeException("Cargo não encontrado");
        }
        return cargoRepository.findById(id);
    }
}
