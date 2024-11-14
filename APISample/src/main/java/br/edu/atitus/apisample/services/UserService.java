package br.edu.atitus.apisample.services;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

import br.edu.atitus.apisample.entities.UserEntity;
import br.edu.atitus.apisample.repositories.UserRepository;

@Service
public class UserService {
	
	//essa classe possui uma dependência de um objeto UserRepository
	private final UserRepository repository;
	
	//no método construtor existe a injeção de dependência 
	public UserService(UserRepository repository) {
		super();
		this.repository = repository;
	}


	private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,})$";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
	
	public static boolean isValidEmail(String email) {
		return EMAIL_PATTERN.matcher(email).matches();
	}
	
	        
	public UserEntity save(UserEntity newUser) throws Exception {
		// validar regras de negócio
		if (newUser == null)
			throw new Exception("Objeto nulo!");
		
		if (newUser.getName() == null || newUser.getName().isEmpty())
				throw new Exception("Nome inválido!");
		newUser.setName(newUser.getName().trim());
		
		if (newUser.getEmail() == null || newUser.getEmail().isEmpty())
			throw new Exception("Email inválido");
		newUser.setEmail(newUser.getEmail().trim());
		
		if (newUser.getPassword() == null || newUser.getPassword().isEmpty())
			throw new Exception("Senha inválida");
		
		if (this.repository.existsByEmail(newUser.getEmail()))
			throw new Exception("Já existe usuário cadastrado com este email!");
		
		newUser.setPassword(newUser.getPassword().trim());
		
		newUser.setName(newUser.getName().trim());
		
		// validar o email com regex
		if (!isValidEmail(newUser.getEmail()))
			throw new Exception("Email inválido!");
		newUser.setEmail(newUser.getEmail().trim());
		
		if (repository.existsByEmail(newUser.getEmail()))
			throw new Exception("Já existe usuário com este e-mail");
		
		//invocar métodos camada repository para persistência dos dados
		this.repository.save(newUser);
		
		return newUser;
	}

}
