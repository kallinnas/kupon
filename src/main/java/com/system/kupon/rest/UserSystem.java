package com.system.kupon.rest;

import com.system.kupon.entity.*;
import com.system.kupon.repository.UserRepository;
import com.system.kupon.service.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class UserSystem {
    private final ApplicationContext context;
    private final UserRepository userRepository;

    public ClientSession login(String email, String password) {
        Optional<User> optional = userRepository.findByEmailAndPassword(email, password);
        if (!optional.isPresent())
            throw new InvalidLoginException("Invalid to login. Check email or password");

        Client client = optional.get().getClient();
        if (client instanceof Customer) return getCustomerSession(client);
        else if(client instanceof Company) return getCompanySession(client);
        else throw new InvalidSessionTypeException("\nUnable to identify type like: %s for client session.\n" +
                    "Stay on your place! The special forces were been sent to you.");
    }

    private ClientSession getCustomerSession(Client client) {
        CustomerServiceImpl service = context.getBean(CustomerServiceImpl.class);
        service.setCustomerId(client.getId());
        ClientSession session = context.getBean(ClientSession.class);
        session.setRole(1);
        session.setCustomerService(service);
        session.accessed();
        return session;
    }

    private ClientSession getCompanySession(Client client) {
        CompanyService service = context.getBean(CompanyService.class);
        service.setCompanyId(client.getId());
        ClientSession session = context.getBean(ClientSession.class);
        session.setRole(2);
        session.setCompanyService(service);
        session.accessed();
        return session;
    }

    private class InvalidLoginException extends RuntimeException {
        public InvalidLoginException(String msg) {
            super(msg);
        }
    }

    private class InvalidSessionTypeException extends RuntimeException {
        public InvalidSessionTypeException(String msg) {
            super(msg);
        }
    }
}
