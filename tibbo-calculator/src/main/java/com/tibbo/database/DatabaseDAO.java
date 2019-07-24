package com.tibbo.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DatabaseDAO
{

  private static Session session = null;
  
  static
  {
    SessionFactory sessionFactory = new Configuration()
        .configure("hibernate.cfg.xml")
        .buildSessionFactory();
    
    session = sessionFactory.openSession();
  }
  
  public static Session getSession()
  {
    return session;
  }
  
  public static void registerUser(String username, String password)
  {
    //Выполнить Insert в базу данных
  }
  
  public static boolean checkUserExist(String username, String password)
  {
    //Выполнить SELECT в базу данных
    //если то, что вернулось не пусто, то true в другом случае false;
    return true;
  }
}
