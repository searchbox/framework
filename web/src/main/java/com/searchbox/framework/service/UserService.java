package com.searchbox.framework.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.searchbox.framework.domain.Role;
import com.searchbox.framework.model.UserEntity;
import com.searchbox.framework.repository.UserRepository;
import com.searchbox.framework.web.user.RegistrationForm;

@Service
@Transactional
public class UserService {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(UserService.class);

  private PasswordEncoder passwordEncoder;

  private UserRepository repository;

  @Autowired
  public UserService(PasswordEncoder passwordEncoder, UserRepository repository) {
    this.passwordEncoder = passwordEncoder;
    this.repository = repository;
  }

  public UserEntity registerNewUserAccount(String email, String password) {
    RegistrationForm form = new RegistrationForm();
    form.setEmail(email);
    form.setPassword(password);
    return registerNewUserAccount(form);
  }

  public UserEntity registerNewUserAccount(RegistrationForm userAccountData)
      throws RuntimeException {
    LOGGER.debug("Registering new user account with information: {}",
        userAccountData);

    if (emailExist(userAccountData.getEmail())) {
      LOGGER.debug("Email: {} exists. Throwing exception.",
          userAccountData.getEmail());
      throw new RuntimeException("The email address: "
          + userAccountData.getEmail() + " is already in use.");
    }

    LOGGER.debug("Email: {} does not exist. Continuing registration.",
        userAccountData.getEmail());

    String encodedPassword = encodePassword(userAccountData);

    UserEntity registered = new UserEntity();
    registered.setEmail(userAccountData.getEmail());
    registered.setPassword(encodedPassword);
    registered.setFirstName(userAccountData.getFirstName());
    registered.setLastName(userAccountData.getLastName());
    registered.getRoles().add(Role.USER);

    // User.Builder user =
    // User.getBuilder().email(userAccountData.getEmail())
    // .firstName(userAccountData.getFirstName())
    // .lastName(userAccountData.getLastName())
    // .password(encodedPassword);

    if (userAccountData.isSocialSignIn()) {
      registered.setSignInProvider(userAccountData.getSignInProvider());
    }

    // User registered = user.build();

    LOGGER.debug("Persisting new user with information: {}", registered);

    return repository.save(registered);
  }

  private boolean emailExist(String email) {
    LOGGER.debug("Checking if email {} is already found from the database.",
        email);

    UserEntity user = repository.findByEmail(email);

    if (user != null) {
      LOGGER.debug("User account: {} found with email: {}. Returning true.",
          user, email);
      return true;
    }

    LOGGER.debug("No user account found with email: {}. Returning false.",
        email);

    return false;
  }

  public void sendPasswordRestoreMail(String email, String serverPath) {

    /*
     * String passwordRestoreUrl = serverPath +
     * FlowsConstatns.RESTORE_PASSWORD_ENDPOINT + "?" + // "a=" +
     * FlowsConstatns.MailMessage.OAUTH_ACTIVATE_ACCOUNT + "&" + "uts=" +
     * cryptoService.createEncodedContent( new Date(System.currentTimeMillis()),
     * email);
     */

  }

  private String encodePassword(RegistrationForm dto) {
    String encodedPassword = null;

    if (dto.isNormalRegistration()) {
      LOGGER.debug("Registration is normal registration. Encoding password.");
      encodedPassword = passwordEncoder.encode(dto.getPassword());
    }

    return encodedPassword;
  }

  private void sendMail(String email, String mailSubject, String mailBody) {
    MailSender mailSender = new JavaMailSenderImpl();
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setTo(email);
    msg.setSubject(mailSubject);
    msg.setText(mailBody);
    mailSender.send(msg);
  }

  public UserEntity addRole(UserEntity user, Role... role) {
    user.getRoles().addAll(Arrays.asList(role));
    return repository.save(user);
  }
}
