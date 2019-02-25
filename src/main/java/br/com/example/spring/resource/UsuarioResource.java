package br.com.example.spring.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.example.spring.dto.UsuarioDTO;
import br.com.example.spring.exception.RestAPIException;
import br.com.example.spring.resource.swagger.IUsuarioResource;
import br.com.example.spring.service.IUsuarioService;
import br.com.example.spring.util.LogUtil;

@RestController
@RequestMapping(value = "/usuarios", produces = "application/json")
public class UsuarioResource extends BaseResource<UsuarioDTO> implements IUsuarioResource {

	@Autowired
	private IUsuarioService usuarioService;

	@PostMapping(value = "")
	public ResponseEntity<UsuarioDTO> salvar(@Valid @RequestBody UsuarioDTO usuario) {
		try {
			UsuarioDTO usuarioRetorno = usuarioService.criar(usuario);
			return responderItemCriado(usuarioRetorno);
		} catch (Exception e) {
			LogUtil.error(e);
			throw new RestAPIException(e.getMessage());
		}
	}

	@PutMapping(value = "")
	public ResponseEntity<UsuarioDTO> atualizar(@Valid @RequestBody UsuarioDTO usuario) {
		try {
			UsuarioDTO usuarioRetorno = usuarioService.atualizar(usuario);
			return responderSucessoComItem(usuarioRetorno);
		} catch (Exception e) {
			LogUtil.error(e);
			throw new RestAPIException(e.getMessage());
		}
	}

	@GetMapping(value = "")
	public ResponseEntity<UsuarioDTO> consultar(@RequestParam(value = "email") String email) {
		try {
			UsuarioDTO usuario = usuarioService.consultarUsuario(email);
			return responderSucessoComItem(usuario);
		} catch (Exception e) {
			LogUtil.error(e);
			throw new RestAPIException(e.getMessage());
		}
	}
}
