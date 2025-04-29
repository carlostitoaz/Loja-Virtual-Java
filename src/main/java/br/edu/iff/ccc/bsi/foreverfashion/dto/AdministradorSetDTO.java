package br.edu.iff.ccc.bsi.foreverfashion.dto;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Administrador;
import br.edu.iff.ccc.bsi.foreverfashion.entities.Cargo;
import br.edu.iff.ccc.bsi.foreverfashion.entities.Endereco;
import br.edu.iff.ccc.bsi.foreverfashion.entities.Usuario;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class AdministradorSetDTO {
    private String descricao;
    private String nome;
    private String email;
    private String telefone;
    private Long id_cargo;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String complemento;
    private String numero;
    private String usuario;
    private String senha;

    public Administrador transformaParaObjeto(){
        Administrador administrador = new Administrador();

        administrador.setDescricao(descricao);
        administrador.setNome(nome);
        administrador.setEmail(email);
        administrador.setTelefone(telefone);

        Cargo cargo = new Cargo();
        cargo.setId_cargo(id_cargo);
        administrador.setCargo(cargo);

        Endereco endereco = new Endereco();
        endereco.setRua(rua);
        endereco.setBairro(bairro);
        endereco.setCidade(cidade);
        endereco.setEstado(estado);
        endereco.setCep(cep);
        endereco.setComplemento(complemento);
        endereco.setNumero(numero);
        administrador.setEndereco(endereco);
        
        Usuario usuario = new Usuario();
        usuario.setUsuario(this.usuario);
        usuario.setSenha(senha);
        administrador.setUsuario(usuario);

        return administrador;
    }

}
