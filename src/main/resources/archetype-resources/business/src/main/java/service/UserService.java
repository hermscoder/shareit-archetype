package ${package}.service;

import ${package}.data.repository.UserRepository;
import ${package}.domain.UserEntity;
import ${package}.domain.dto.CreateUser;
import ${package}.domain.dto.User;
import ${package}.domain.mapper.UserMapper;
import ${package}.exception.UserNotFoundException;
import ${package}.infrastructure.cryptography.Encrypter;
import ${package}.utils.commons.exception.InvalidParameterException;
import ${package}.utils.validator.EmailValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final EmailValidator emailValidator;
    private final Encrypter encrypter;

    public UserService(UserRepository userRepository, EmailValidator emailValidator, Encrypter encrypter) {
        this.userRepository = userRepository;
        this.emailValidator = emailValidator;
        this.encrypter = encrypter;
    }

    public User findById(Long userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException(userId);
        }
        //Use mapstruct to map entity to dto User and return
        return UserMapper.INSTANCE.toModel(userOptional.get());
    }

    public Long createUser(CreateUser createUser) {
        if(!emailValidator.isValid(createUser.getEmail())) {
            throw new InvalidParameterException("email");
        }

        if(!createUser.getPassword().equals(createUser.getPasswordConfirmation())) {
            throw new InvalidParameterException("passwordConfirmation");
        }

        UserEntity userEntity = userRepository.save(
                new UserEntity(createUser.getEmail(),
                        encrypter.encrypt(createUser.getPassword()),
                        createUser.getName(),
                        createUser.getBirthDate()));
        return userEntity.getId();
    }
}
