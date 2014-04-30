package com.searchbox.framework.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

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

  private MailSender mailSender;

  @Autowired
  public UserService(PasswordEncoder passwordEncoder, UserRepository repository,
      MailSender mailSender) {
    this.passwordEncoder = passwordEncoder;
    this.repository = repository;
    this.mailSender = mailSender;
  }

  public String getResetHash(String email) {
    byte[] stuff = (email + System.currentTimeMillis() + "").getBytes();
    String hash = DigestUtils.md5DigestAsHex(stuff);
    return hash;
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

  public boolean emailExist(String email) {
    LOGGER.info("Checking if email {} is already found from the database.",
        email);

    UserEntity user = repository.findByEmail(email);

    if (user != null) {
      LOGGER.info("User account: {} found with email: {}. Returning true.",
          user, email);
      return true;
    }

    LOGGER
        .info("No user account found with email: {}. Returning false.", email);

    return false;
  }

  public String encodePasswordString(String password){
    return passwordEncoder.encode(password);
  }

  private String encodePassword(RegistrationForm dto) {
    String encodedPassword = null;

    if (dto.isNormalRegistration()) {
      LOGGER.debug("Registration is normal registration. Encoding password.");
      encodedPassword = passwordEncoder.encode(dto.getPassword());
    }

    return encodedPassword;
  }

  public UserEntity addRole(UserEntity user, Role... role) {
    user.getRoles().addAll(Arrays.asList(role));
    return repository.save(user);
  }

  public String resetPasswordWithEmail(String email, String host,
      Integer port,  String path) {
    UserEntity user = repository.findByEmail(email);
    // disable log-in for User Account
    //user.setAccountNonLocked(false);
    user.setResetHash(this.getResetHash(email));
    user.setResetDate(new Date());
    //TODO Steph: This method doesn't seem to update the user.
    repository.save(user);
    
    LOGGER.info("Saved user {}",user);

    String resetUrl = "http://"+host+
        ((port!=80)?":"+port:"")+
        path+"/user/resetPassword/"+user.getResetHash();

    LOGGER.info("Preparing the reset email with link {}", resetUrl);

    // send notification mail.
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setFrom("no-reply@opportunity-finder.com");
    msg.setTo(email);
    msg.setSubject("Reset your password on opportunity-finder");
    msg.setText("Dear"+((user.getFirstName()!=null)?" "+user.getFirstName():"")+
                      ((user.getLastName()!=null)?" "+user.getLastName():"")+",\n\n"+
                "We received a request from you to reset your "+
                "Opportunity-Finder password. To complete the process, "+
                "simply click the link below: \n\n"+
                resetUrl+"\n\n"+
                "The link is valid for 24 hours.\n\n"+
                "If you don't want to change your Opportunity-Finder password "+
                "you can ignore this mail.\n\n"+
                "If you need any help, contact us at contact@opportunity-finder.ch.\n\n"+
                "Your Opportunity-Finder Team");

    mailSender.send(msg);

    return resetUrl;
  }

  public UserEntity changePassword(UserEntity user, String password) {
    user.setPassword(passwordEncoder.encode(password));
    return repository.save(user);
  }

  /**
   * Returns a new object which specifies the the wanted result page.
   * @param pageIndex The index of the wanted result page
   * @return
   */
  private Pageable constructPageSpecification() {
      Pageable pageSpecification = new PageRequest(0, 10, sortByEmail());
      return pageSpecification;
  }

  /**
   * Returns a Sort object which sorts persons in ascending order by using the last name.
   * @return
   */
  private Sort sortByEmail() {
      return new Sort(Sort.Direction.ASC, "email");
  }


  public List<UserEntity> findAll(){
    return findAll(constructPageSpecification());
  }

  public List<UserEntity> findAll(Pageable pageRequest){
    LOGGER.debug("Listing all persons for page: " + pageRequest.getPageNumber());
    Page<UserEntity> requestedPage = repository.findAll(pageRequest);
    return requestedPage.getContent();
  }

  public long countAll() {
    return repository.count();
  }
}
