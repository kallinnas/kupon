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

    public ClientSession createClientSession(String email, String password) throws InvalidLoginException {
        // ADMIN DATA
        String adminEmail = "admin";
        String adminPass = "777";

        if (email.equals(adminEmail) && password.equals(adminPass)) {
            AdminService service = context.getBean(AdminService.class);
            ClientSession session = context.getBean(ClientSession.class);
            session.setRole(3);
            session.setAdminService(service);
            session.accessed();
            return session;
        }

        Optional<User> optional = userRepository.findByEmailAndPassword(email, password);
        if (!optional.isPresent())
            throw new InvalidLoginException("Invalid to login. Check email or password");

        Client client = optional.get().getClient();
        return client instanceof Customer ? getCustomerSession(client) : getCompanySession(client);
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

    public class InvalidLoginException extends RuntimeException {
        public InvalidLoginException(String msg) {
            super(msg);
        }
    }
}
