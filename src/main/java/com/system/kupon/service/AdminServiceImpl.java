package com.system.kupon.service;

import com.system.kupon.db.CompanyRepository;
import com.system.kupon.db.CouponRepository;
import com.system.kupon.db.CustomerRepository;
import com.system.kupon.db.UserRepository;
import com.system.kupon.model.Company;
import com.system.kupon.model.Coupon;
import com.system.kupon.model.Customer;
import com.system.kupon.model.User;
import com.system.kupon.ex.NoSuchCustomerException;
import com.system.kupon.ex.UserIsNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
//@Transactional
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminServiceImpl implements AdminService {

    private final ApplicationContext context;
    private final CouponRepository couponRepository;
    private final CustomerRepository customerRepository;
    private final CompanyRepository companyRepository;

    /* COUPON */
    @Override
    public Coupon getCouponById(long id) {
        return couponRepository.findExistById(id);
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public Coupon updateCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public String deleteCoupon(long id) {
        Coupon coupon = couponRepository.findExistById(id);
        for (Customer customer : customerRepository.findAll()) {
            customer.getCoupons().remove(coupon);
        }
        Company company = coupon.getCompany();
        company.getCoupons().remove(coupon);
        couponRepository.deleteById(id);
        return "Coupon id# " + id + "was deleted successfully!";
    }

    /* CUSTOMER */
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(long id) {
        return customerRepository.findById(id).orElse(Customer.empty());
    }

    @Override
    public List<Coupon> getCustomerCouponsById(long id) {
        return couponRepository.findAllByCustomerId(id);
    }

    @Override
    public Customer updateCustomer(Customer update) throws NoSuchCustomerException {
        Optional<Customer> optCustomer = customerRepository.findById(update.getId());
        if (optCustomer.isEmpty()) {
            throw new NoSuchCustomerException("There is no update with such id: " + update.getId());
        }
        Customer customer = Customer.builder()
                .firstName(update.getFirstName())
                .lastName(update.getLastName()).build();
        optCustomer.get();
        customerRepository.save(customer);
        return customer;
    }

    @Override
    public void deleteCustomerById(long id) {
        Optional<Customer> optCustomer = customerRepository.findById(id);
        if (optCustomer.isEmpty()) {
            throw new NoSuchCustomerException(String.format("Customer with id#%d does not exist.", id));
        }
        Customer customer = optCustomer.get();

        for (Coupon c : couponRepository.findAll()) {
            if (c.getCustomers().contains(customer)) {
                c.getCustomers().remove(customer);
                couponRepository.save(c);
            }
        }

        UserRepository repository = context.getBean(UserRepository.class);
        for (User user : repository.findAll()) {
            if (user.getClient().getId() == id) {
                repository.delete(user);
            }
        }
        customerRepository.deleteById(id);
    }

    @Override
    public void deleteCustomersCoupon(long customerId, long couponId) {
        Optional<Customer> optCustomer = customerRepository.findById(customerId);
        if (optCustomer.isEmpty()) {
            throw new NoSuchCustomerException("There is no company with such id: " + customerId);
        }
        Optional<Coupon> optCoupon = couponRepository.findById(couponId);
        if (optCoupon.isEmpty()) {
            throw new NoSuchCustomerException("There is no company with such id: " + couponId);
        }
        Customer customer = optCustomer.get();
        Coupon coupon = optCoupon.get();
        coupon.getCustomers().remove(customer);
        couponRepository.save(coupon);
        customer.getCoupons().remove(coupon);
        customerRepository.save(customer);
    }

    /* COMPANY */
    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(long id) {
        return companyRepository.findById(id).orElse(Company.empty());
    }

    @Override
    public List<Coupon> getCouponsByCompanyId(long id) {
        return couponRepository.findAllByCompanyId(id);
    }

    @Override
    public void updateCompany(Company update) {
        Optional<Company> optCompany = companyRepository.findById(update.getId());
        if (optCompany.isEmpty()) {
            throw new NoSuchCustomerException("There is no company with such id: " + update.getId());
        }
        Company company = optCompany.get();
        company.setName(update.getName());
        company.setImageURL(update.getImageURL());
        companyRepository.save(update);
    }

    @Override
    public void deleteCompanyById(long id) throws UserIsNotExistException {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isEmpty()) {
            throw new UserIsNotExistException(String.format("Can't delete company with such id#%d", id));
        }
        companyRepository.deleteById(id);
    }


}
