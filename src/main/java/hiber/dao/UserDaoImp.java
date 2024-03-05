package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   private SessionFactory sessionFactory;

   @Autowired
   public UserDaoImp(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void add(User user) {
      Session session = sessionFactory.getCurrentSession();
      session.persist(user);
      session.persist(user.getCar());
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      Query<User> query = sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   public User getByCar(Car car) {
      String hql = "from User where car.model = :model and car.series = :series";
      Query<User> query = sessionFactory.getCurrentSession().createQuery(hql, User.class);
      query.setParameter("model", car.getModel());
      query.setParameter("series", car.getSeries());
      return query.getSingleResult();
   }
}
