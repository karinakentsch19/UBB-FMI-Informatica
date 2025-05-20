package ro.iss.biblioteca.Utils;
import org.hibernate.AnnotationException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ro.iss.biblioteca.Domain.Carte;
import ro.iss.biblioteca.Domain.Cos;
import ro.iss.biblioteca.Domain.Imprumut;
import ro.iss.biblioteca.Domain.Utilizator;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        if ((sessionFactory==null)||(sessionFactory.isClosed()))
            sessionFactory=createNewSessionFactory();
        return sessionFactory;
    }

    private static SessionFactory createNewSessionFactory() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(Carte.class)
                .addAnnotatedClass(Cos.class)
                .addAnnotatedClass(Imprumut.class)
                .addAnnotatedClass(Utilizator.class)
                .buildSessionFactory();
        return sessionFactory;
    }

    public static  void closeSessionFactory(){
        if (sessionFactory!=null)
            sessionFactory.close();
    }
}