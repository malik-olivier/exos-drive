package fr.eservices.drive.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import fr.eservices.drive.dao.UserDao;
import fr.eservices.drive.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Qualifier("UserJPADao")
public class UserJPADao extends UserDao {
	@Autowired
	EntityManager em;
	
	@Override
	public User find(String login) {
		return em.find(User.class, login);
	}
	
	@Override
	public void save(User user) {
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
	}
	
	@Override
	public void doChangePassword(String login, String password) {
		em.getTransaction().begin();
		em.createQuery("update User set password = :PWD where login = :LOGIN")
			.setParameter("LOGIN", login)	
			.setParameter("PWD", password)
			.executeUpdate();
		em.getTransaction().commit();
	}
	
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("myApp");
		UserJPADao dao = new UserJPADao();
		dao.em = emf.createEntityManager();
		
		User u = new User();
		u.setFirstname("malik");
		u.setLastname("olivier");
		u.setLogin("malik");
		u.setPassword("eservices");
		dao.save(u);
		
		emf.close();
	}

}
