package br.com.example.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.example.spring.model.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByEmail(String email);

	@Modifying
	@Query("update Usuario u set u.senha = ?1 where u.email = ?2")
	void atualizarSenha(String senha, String email);
}
