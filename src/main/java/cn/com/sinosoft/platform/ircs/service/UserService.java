package cn.com.sinosoft.platform.ircs.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.sinosoft.platform.ircs.dao.AuthorityDao;
import cn.com.sinosoft.platform.ircs.dao.UserDao;
import cn.com.sinosoft.platform.ircs.domain.Authority;
import cn.com.sinosoft.platform.ircs.domain.User;
import cn.com.sinosoft.platform.ircs.security.SecurityUtils;
import cn.com.sinosoft.platform.ircs.service.util.RandomUtil;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private UserDao userDao;

    @Inject
    private AuthorityDao authorityDao;

    public  User activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        User user = userDao.findOneByActivationKey(key);
        // activate given user for the registration key.
        if (user != null) {
            user.setActivated(true);
            user.setActivationKey(null);
            userDao.save(user);
            log.debug("Activated user: {}", user);
        }
        return user;
    }

    public User createUserInformation(String login, String password, String firstName, String lastName, String email,
                                      String langKey) {
        User newUser = new User();
        Authority authority = authorityDao.findOne("ROLE_USER");
        Set<Authority> authorities = new HashSet<>();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(login);
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setLangKey(langKey);
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        authorities.add(authority);
        newUser.setAuthorities(authorities);
		userDao.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public void updateUserInformation(String firstName, String lastName, String email) {
        User currentUser = userDao.findOneByLogin(SecurityUtils.getCurrentLogin());
        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setEmail(email);
        userDao.save(currentUser);
        log.debug("Changed Information for User: {}", currentUser);
    }

    public void changePassword(String password) {
        User currentUser = userDao.findOneByLogin(SecurityUtils.getCurrentLogin());
        String encryptedPassword = passwordEncoder.encode(password);
        currentUser.setPassword(encryptedPassword);
        userDao.save(currentUser);
        log.debug("Changed password for User: {}", currentUser);
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities() {
        User currentUser = userDao.findOneByLogin(SecurityUtils.getCurrentLogin());
        currentUser.getAuthorities().size(); // eagerly load the association
        return currentUser;
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p/>
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     * </p>
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        DateTime now = new DateTime();
        List<User> users = userDao.findAllByActivatedIsFalseAndCreatedDateBefore(now.minusDays(3));
        for (User user : users) {
            log.debug("Deleting not activated user {}", user.getLogin());
            userDao.delete(user);
        }
    }
}
