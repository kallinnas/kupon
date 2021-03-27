package com.system.kupon.service;

import com.system.kupon.db.CompanyRepository;
import com.system.kupon.db.CouponRepository;
import com.system.kupon.db.CustomerRepository;
import com.system.kupon.db.UserRepository;
import com.system.kupon.model.Company;
import com.system.kupon.model.Coupon;
import com.system.kupon.model.Customer;
import com.system.kupon.model.User;
import com.system.kupon.ex.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ApplicationContext context;

    private User getCurrentUser(String email) {
        Optional<User> optional = repository.findByEmail(email);
        if (optional.isEmpty())
            throw new InvalidLoginException("NEVER HAPPENS IN USER-SERVICE-IMPL!!!");/////////?????????
        return optional.get();
    }

    @Override
    public void registerNewUser(String email, String password, int role) throws UserAlreadyExistException {
        Optional<User> optional = repository.findByEmail(email);
        if (optional.isPresent())
            throw new UserAlreadyExistException(String.format("USER WITH SUCH EMAIL %s ALREADY EXIST!", email));
        User user = new User(email, password, role);
        if (user.getClient() instanceof Customer) //Call one of repo to save it in User's client
            this.context.getBean(CustomerRepository.class).save((Customer) user.getClient());
        else this.context.getBean(CompanyRepository.class).save((Company) user.getClient());
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

    /* COUPON */
    @Override
    public Coupon getCouponById(long id) {
        return context.getBean(CouponRepository.class).findExistById(id);
    }

}
