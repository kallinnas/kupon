package com.system.kupon.service;

import com.system.kupon.db.CompanyRepository;
import com.system.kupon.db.CouponRepository;
import com.system.kupon.db.CustomerRepository;
import com.system.kupon.db.UserRepository;
import com.system.kupon.model.*;
import com.system.kupon.ex.NoSuchCouponException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CompanyServiceImpl implements CompanyService {

    @Setter // to set id on service for session in UserSys
    private long companyId;
    private final CompanyRepository companyRepository;
    private final CouponRepository couponRepository;
    private final ApplicationContext context;

    /* COMPANY */
    @Override
    public Company getCompany(){
        return companyRepository.findExistById(companyId);
    }

    @Override
    public Company getCompanyById(long id) {
        return companyRepository.findById(id).orElse(Company.empty());
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public void updateCompany(Company update) {
        Company company = companyRepository.findExistById(companyId);
        company.setName(update.getName());
        company.setImageURL(update.getImageURL());
        companyRepository.save(company);
    }

    @Override
    public String updateCompanyEmail(String email) {
        UserRepository repository = context.getBean(UserRepository.class);
        User user = repository.findAll().stream()
                .filter(u -> u.getClient().equals(getCompany()))
                .findFirst().get();
        user.setEmail(email);
        repository.save(user);
        return String.format("%s your email was successfully changed to %s", getCompany().getName(), user.getEmail());
    }

    @Override
    public String updateCompanyPassword(String newPassword) {
        UserRepository repository = context.getBean(UserRepository.class);
        User user = repository.findAll().stream()
                .filter(u -> u.getClient().equals(getCompany()))
                .findFirst().get();
        user.setPassword(newPassword);
        repository.save(user);
        return String.format("%s your password was successfully updated!", getCompany().getName());
    }



    @Override
    public Boolean confirmPassword(String password) {
        return context.getBean(UserRepository.class).findByPassword(password).isPresent() &&
                context.getBean(UserRepository.class).findByPassword(password).get().getClient().equals(getCompany());
    }


    /* COUPON */
    @Override
    public Coupon getCouponById(long id){
        return couponRepository.findExistById(id);
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public List<Coupon> getCouponsByCompanyId(long id) {
        return couponRepository.findAllByCompanyId(id);
    }

    @Override
    public List<Coupon> getCompanyCoupons() {
        return couponRepository.findAllByCompanyId(companyId);
    }

    // UPDATE FOR COUPON
    @Override
    @Transactional
    public Coupon createCoupon(Coupon coupon) {
        /* Checks coupon similarity, if coupon already exist
        it'll add the amount of new coupons to current amount */
        if (getCompany().getCoupons() != null) {
            for (Coupon existCoupon : getCompany().getCoupons()) {
                if (coupon.similarCoupon(existCoupon)) {
                    existCoupon.setAmount(existCoupon.getAmount() + coupon.getAmount());
                    return couponRepository.save(existCoupon);
                }
            }
        }
        /* CompanyId needs to be set under new coupon!!!
        * Otherwise DB coupons won't have any affiliation (приналежність) */
        coupon.setCompany(getCompanyById(companyId));
        getCompanyById(companyId).add(coupon);
        return couponRepository.save(coupon);
    }

    @Override
    public Coupon updateCoupon(Coupon update) {
        Coupon coupon = couponRepository.findExistById(update.getId());
        coupon.setTitle(update.getTitle());
        coupon.setStartDate(update.getStartDate());
        coupon.setEndDate(update.getEndDate());
        coupon.setCategory(update.getCategory());
        coupon.setAmount(update.getAmount());
        coupon.setPrice(update.getPrice());
        coupon.setDescription(update.getDescription());
        coupon.setImageURL(update.getImageURL());
        return couponRepository.save(coupon);
    }

    @Override
    public String deleteCouponById(long id) {
        Coupon coupon = couponRepository.findExistById(id);
        for (Customer customer : context.getBean(CustomerRepository.class).findAll()) {
            customer.getCoupons().remove(coupon);
        }

        Company company = getCompanyById(companyId);
        List<Coupon> coupons = company.getCoupons();
        coupons.remove(coupon);
        company.setCoupons(coupons);
        couponRepository.deleteById(id);
        return "Coupon: " + coupon.getTitle() + " was deleted successfully!";
    }




}
