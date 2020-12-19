package com.system.kupon.service;

import com.system.kupon.entity.User;
import com.system.kupon.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        return null;
//        return userRepository.findByEmailAndPassword(email, password);
    }
}
