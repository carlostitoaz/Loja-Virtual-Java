package br.edu.iff.ccc.bsi.foreverfashion.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import br.edu.iff.ccc.bsi.foreverfashion.entities.Administrador;
import br.edu.iff.ccc.bsi.foreverfashion.entities.Pessoa;
import br.edu.iff.ccc.bsi.foreverfashion.entities.Usuario;
import br.edu.iff.ccc.bsi.foreverfashion.service.PessoaService;
import br.edu.iff.ccc.bsi.foreverfashion.service.UsuarioService;

@Service
public class ServiceDetalhesUsuario implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PessoaService pessoaService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.findByUsuario(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: "+username));

        Pessoa pessoa = pessoaService.findByUsuario(usuario).orElseThrow(() -> new UsernameNotFoundException("Pessoa não encontrada"));

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (pessoa instanceof Administrador) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new User(
            usuario.getUsuario(),
            usuario.getSenha(),
            authorities
        );
    }
}