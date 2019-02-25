package br.com.example.spring.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.example.spring.constante.MensagemValidacao;
import br.com.example.spring.model.Usuario;
import br.com.example.spring.service.IAutenticacaoService;
import br.com.example.spring.service.IUsuarioService;

@Service
public class AutenticacaoService implements UserDetailsService, IAutenticacaoService {

	@Autowired
	private IUsuarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String email) {
		Usuario usuario = usuarioService.consultar(email);
		List<GrantedAuthority> listaAutorizacoes = new ArrayList<>();

		if (!validarUsuario(usuario)) {
			throw new UsernameNotFoundException(MensagemValidacao.ERRO_USUARIO_SEM_PERMISSAO);
		}

		String role = usuario.getPermissao().getCodigo();
		listaAutorizacoes.add(new SimpleGrantedAuthority(role));

		return new User(usuario.getEmail(), usuario.getSenha(), listaAutorizacoes);
	}

	private boolean validarUsuario(Usuario usuario) {
		boolean usuarioValidado = false;

		if (usuario != null && usuario.getPermissao() != null && BooleanUtils.toBoolean(usuario.getStatus())) {
			usuarioValidado = true;
		}

		return usuarioValidado;
	}

}
