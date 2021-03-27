package com.system.kupon.rest;

import com.system.kupon.db.UserRepository;
import com.system.kupon.model.Client;
import com.system.kupon.model.Customer;
import com.system.kupon.model.User;
import com.system.kupon.ex.InvalidLoginException;
import com.system.kupon.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserSystem {
    private final ApplicationContext context;
    private final UserRepository userRepository;
    private final ClientSession session;

    public ClientSession createClientSession(String email, String password) throws InvalidLoginException {
        // ADMIN DATA
        String adminEmail = "admin";
        String adminPass = "777";

        if (email.equals(adminEmail) && password.equals(adminPass)) {
            AdminService service = context.getBean(AdminService.class);
            session.setRole(3);
            session.setAdminService(service);
            session.accessed();
            return session;
        }

        Optional<User> optional = userRepository.findByEmailAndPassword(email, password);
        if (optional.isEmpty())
            throw new InvalidLoginException("Invalid to login. Check carefully your email address and password.");

        Client client = optional.get().getClient();
        return client instanceof Customer ? getCustomerSession(client) : getCompanySession(client);

    }

    private ClientSession getCustomerSession(Client client) {
        CustomerServiceImpl service = context.getBean(CustomerServiceImpl.class);
        service.setCustomerId(client.getId());
        session.setRole(1);
        session.setCustomerService(service);
        session.setUserService(context.getBean(UserServiceImpl.class));
        session.accessed();
        return session;
    }

    private ClientSession getCompanySession(Client client) {
        CompanyServiceImpl service = context.getBean(CompanyServiceImpl.class);
        service.setCompanyId(client.getId());
        session.setRole(2);
        session.setCompanyService(service);
        session.setUserService(context.getBean(UserServiceImpl.class));
        session.accessed();
        return session;
    }

}
