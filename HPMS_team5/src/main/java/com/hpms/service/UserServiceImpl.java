package com.hpms.service;

import com.hpms.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
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
			User existingUser = session.get(User.class, updatedUser.getId());
			if (existingUser != null) {
				existingUser.setFirstName(updatedUser.getFirstName());
				existingUser.setLastName(updatedUser.getLastName());
				existingUser.setEmail(updatedUser.getEmail());
				existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
				existingUser.setGender(updatedUser.getGender());
				existingUser.setRole(updatedUser.getRole());
				// Password is not updated
				session.update(existingUser);
			}
			transaction.commit();
		}
	}

	@Transactional
	public void deleteUser(int userId) {
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
					.setParameter("email", email).setParameter("password", password).uniqueResult();
		}
	}

	@Transactional
	public User getUserById(int userId) {
		try (Session session = sessionFactory.openSession()) {
			return session.get(User.class, userId);
		}
	}

	@Transactional
	public List<User> getPatients() {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM User WHERE role = 'USER'").list();
		}
	}

	@Transactional
	public List<User> getDoctors() {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("FROM User WHERE role = 'ADMIN'").list();
		}
	}

	@Transactional
	public User getUserByEmail(String email) {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("FROM User WHERE email = :email", User.class).setParameter("email", email)
				.uniqueResult();
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    User user = getUserByEmail(email);
	    
	    if (user == null) {
	        throw new UsernameNotFoundException("User not found");
	    }
	    
	    UserDetails userDetails = org.springframework.security.core.userdetails.User
	        .withUsername(user.getEmail())
	        .password(user.getPassword())
	        .roles(user.getRole())
	        .build();
	        
	    return userDetails;
	}

	@Override
	public boolean existsByEmail(String email) {
		return getUserByEmail(email) != null;
	}

	@Override
	public void save(User user) {
		addUser(user);
	}
}