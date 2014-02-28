package com.searchbox.framework.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.searchbox.framework.domain.User;
import com.searchbox.framework.repository.UserRepository;
import com.searchbox.framework.web.user.RegistrationForm;

@Service
@Transactional
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
   
	private PasswordEncoder passwordEncoder;

	private UserRepository repository;

	@Autowired
	public UserService(PasswordEncoder passwordEncoder,
			UserRepository repository) {
		this.passwordEncoder = passwordEncoder;
		this.repository = repository;
	}

	public User registerNewUserAccount(String email, String password){
		RegistrationForm form = new RegistrationForm();
		form.setEmail(email);
		form.setPassword(password);
		return registerNewUserAccount(form);
	}
	
	public User registerNewUserAccount(RegistrationForm userAccountData)
			throws RuntimeException {
		logger.debug("Registering new user account with information: {}",
				userAccountData);

		if (emailExist(userAccountData.getEmail())) {
			logger.debug("Email: {} exists. Throwing exception.",
					userAccountData.getEmail());
			throw new RuntimeException("The email address: "
					+ userAccountData.getEmail() + " is already in use.");
		}

		logger.debug("Email: {} does not exist. Continuing registration.",
				userAccountData.getEmail());

		String encodedPassword = encodePassword(userAccountData);


		User registered = new User();
		registered.setEmail(userAccountData.getEmail());
		registered.setPassword(encodedPassword);
		
//		User.Builder user = User.getBuilder().email(userAccountData.getEmail())
//				.firstName(userAccountData.getFirstName())
//				.lastName(userAccountData.getLastName())
//				.password(encodedPassword);

		if (userAccountData.isSocialSignIn()) {
			registered.setSignInProvider(userAccountData.getSignInProvider());
		}

//		User registered = user.build();

		logger.debug("Persisting new user with information: {}", registered);

		return repository.save(registered);
	}

	private boolean emailExist(String email) {
		logger.debug(
				"Checking if email {} is already found from the database.",
				email);

		User user = repository.findByEmail(email);

		if (user != null) {
			logger.debug(
					"User account: {} found with email: {}. Returning true.",
					user, email);
			return true;
		}

		logger.debug("No user account found with email: {}. Returning false.",
				email);

		return false;
	}

	private String encodePassword(RegistrationForm dto) {
		String encodedPassword = null;

		if (dto.isNormalRegistration()) {
			logger.debug("Registration is normal registration. Encoding password.");
			encodedPassword = passwordEncoder.encode(dto.getPassword());
		}

		return encodedPassword;
	}
}
