package com.system.kupon.service;

import com.system.kupon.db.*;
import com.system.kupon.entity.*;
import com.system.kupon.rest.UserSystem;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    //    private final ApplicationContext context;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;

    private User getCurrentUser(String email) {
        Optional<User> optional = repository.findByEmail(email);
        if (!optional.isPresent()) throw new UserSystem.InvalidLoginException("NEVER HAPPENS IN USER-SERVICE-IMPL!!!");
        return optional.get();
    }

    @Override
    @Transactional
    public void registerNewUser(String email, String password, int role) throws UserSystem.InvalidLoginException {
        Optional<User> optional = repository.findByEmail(email);
        if (optional.isPresent())
            throw new UserSystem.InvalidLoginException(String.format("USER WITH SUCH EMAIL %s ALREADY EXIST!", email));
        User user = new User(email, password, role);
        if (user.getClient() instanceof Customer) //Call one of repo to save it in User's client
            customerRepository.save((Customer) user.getClient());
        else companyRepository.save((Company) user.getClient());
//        this.context.getBean(CustomerRepository.class).save((Customer) user.getClient());
//        else this.context.getBean(CompanyRepository.class).save((Company) user.getClient());
        repository.save(user);
    }

    @Override
    public void updateEmail(String email, String updateEmail) {
        User user = getCurrentUser(email);
        user.setEmail(updateEmail);
        repository.save(user);
    }

    @Override
    public void updatePassword(String email, String password) {
        User user = getCurrentUser(email);
        user.setPassword(password);
        repository.save(user);
    }


}
