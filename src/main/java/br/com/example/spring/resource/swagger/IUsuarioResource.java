package br.com.example.spring.resource.swagger;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.example.spring.dto.ErrorDTO;
import br.com.example.spring.dto.UsuarioDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@Api(value = "usuarios", description = "Operações relacionadas a Usuários")
public interface IUsuarioResource {

	@ApiOperation(value = "Salvar novo usuário", nickname = "salvarUsuario", notes = "", response = UsuarioDTO.class, responseContainer = "object", authorizations = {
			@Authorization(value = "basicAuth") }, tags = { "usuarios", })
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Usuário criado com sucesso"),
			@ApiResponse(code = 400, message = "Dados informados para a requisição estão inconsistentes", response = ErrorDTO.class, responseContainer = "object"),
			@ApiResponse(code = 401, message = "Usuário sem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Usuário não encontrada") })
	@PostMapping(value = "")
	ResponseEntity<UsuarioDTO> salvar(
			@ApiParam(value = "Usuário criado com sucesso", required = true) @Valid @RequestBody UsuarioDTO usuario);

	@ApiOperation(value = "Atualizar usuario", nickname = "atualizarUsuario", notes = "", response = UsuarioDTO.class, responseContainer = "object", authorizations = {
			@Authorization(value = "basicAuth") }, tags = { "usuarios", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Usuário atualizado com sucesso"),
			@ApiResponse(code = 400, message = "Dados informados para a requisição estão inconsistentes", response = ErrorDTO.class, responseContainer = "object"),
			@ApiResponse(code = 401, message = "Usuário sem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Usuário não encontrado") })
	@PutMapping(value = "")
	ResponseEntity<UsuarioDTO> atualizar(
			@ApiParam(value = "Observar os campos obrigatótios do modelo para executar a requisição", required = true) @Valid @RequestBody UsuarioDTO usuario);

	@ApiOperation(value = "Consultar usuário por email", nickname = "getUsuarios", notes = "", response = UsuarioDTO.class, responseContainer = "object", authorizations = {
			@Authorization(value = "basicAuth") }, tags = { "usuarios", })
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Usuário encontrado com sucesso", response = UsuarioDTO.class, responseContainer = "object"),
			@ApiResponse(code = 400, message = "Dados informados para a requisição estão inconsistentes", response = ErrorDTO.class, responseContainer = "object"),
			@ApiResponse(code = 401, message = "Usuário sem permissão para acessar o recurso"),
			@ApiResponse(code = 404, message = "Usuário não encontrada") })
	@GetMapping(value = "")
	ResponseEntity<UsuarioDTO> consultar(@RequestParam(value = "email", required = false) String email);

}
