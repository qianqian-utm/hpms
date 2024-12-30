package com.hpms.service;

import com.hpms.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import javax.transaction.Transactional;


@Service
public class UserService {
	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
    public void addUser(User newUser) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(newUser);
            transaction.commit();
        }
    }

	@Transactional
	@SuppressWarnings("unchecked")
	public List<User> getUserList() {
	    try (Session session = sessionFactory.openSession()) {
	        return session.createQuery("from User order by role asc, firstName asc").list();
	    }
	}

	@Transactional
    public void updateUser(User updatedUser) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(updatedUser);
            transaction.commit();
        }
    }

	@Transactional
    public void deleteUser(Long userId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, userId);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        }
    }
	
	@Transactional
	public User getUserByEmailAndPassword(String email, String password) {
	    try (Session session = sessionFactory.openSession()) {
	        return (User) session.createQuery("FROM User WHERE email = :email AND password = :password")
	            .setParameter("email", email)
	            .setParameter("password", password)
	            .uniqueResult();
	    }
	}
}