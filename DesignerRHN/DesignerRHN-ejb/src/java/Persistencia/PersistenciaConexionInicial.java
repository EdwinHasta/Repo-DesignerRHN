package Persistencia;

import Entidades.Perfiles;
import InterfacePersistencia.PersistenciaConexionInicialInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

@Stateless
public class PersistenciaConexionInicial implements PersistenciaConexionInicialInterface {

    private EntityManager em;

    public boolean validarUsuario(EntityManager eManager, String usuario) {
        try {
            em = eManager;
            em.getTransaction().begin();
            String sqlQuery = "SET ROLE ROLENTRADA";
            Query query = em.createNativeQuery(sqlQuery);
            query.executeUpdate();
            sqlQuery = "select COUNT(*) from usuarios where alias = ? AND activo = 'S'";
            query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, usuario);
            BigDecimal retorno = (BigDecimal) query.getSingleResult();
            Integer instancia = retorno.intValueExact();
            em.getTransaction().commit();
            if (instancia > 0) {
                System.out.println("El usuario es correcto y esta activo");
                return true;
            } else {
                System.out.println("El usuario es incorrecto y/o esta inactivo");
                em.getEntityManagerFactory().close();
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error validarUsuario: " + e);
            return false;
        }
    }

    public void accesoDefault(EntityManager eManager) {
        em = eManager;
        em.getTransaction().begin();
        String sqlQuery = "SET ROLE ROLENTRADA";
        Query query = em.createNativeQuery(sqlQuery);
        query.executeUpdate();
        em.getTransaction().commit();
    }

    public EntityManager validarConexionUsuario(EntityManagerFactory emf, String usuario, String contraseña, String baseDatos) {
        try {
            em = emf.createEntityManager();
            if (em.isOpen()) {
                return em;
            }
        } catch (Exception e) {
            emf.close();
            //System.out.println("NO SE PUEDE CONECTAR, PROBLEMA ESTA EN LA CONTRASEÑA");
            //Throwable t = getLastThrowable(e);
            //SQLException exxx = (SQLException) t;
            //System.out.println(exxx.getSQLState());
            //System.out.println(exxx.getErrorCode());
        }
        return null;
    }

    private Throwable getLastThrowable(Exception e) {
        Throwable t = null;
        for (t = e.getCause(); t.getCause() != null; t = t.getCause());
        return t;
    }

    public Perfiles perfilUsuario(EntityManager eManager, BigInteger secPerfil) {
        em = eManager;
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT p FROM Perfiles p WHERE p.secuencia = :secPerfil ");
        query.setParameter("secPerfil", secPerfil);
        Perfiles perfil = (Perfiles) query.getSingleResult();
        em.getTransaction().commit();
        return perfil;
    }

    @Override
    public BigInteger usuarioLogin(EntityManager eManager, String usuarioBD) {
        em = eManager;
        Query query = em.createQuery("SELECT u.perfil.secuencia FROM Usuarios u WHERE u.alias = :usuarioBD");
        query.setParameter("usuarioBD", usuarioBD);
        BigInteger secPerfil = (BigInteger) query.getSingleResult();
        return secPerfil;
    }

    public void setearUsuario(EntityManager eManager, String rol, String pwd) {
        String texto = "SET ROLE " + rol + " IDENTIFIED BY " + pwd;
        em = eManager;
        em.getTransaction().begin();
        System.out.println("Rol: " + rol);
        System.out.println("Password: " + pwd);
        System.out.println("Texto: " + texto);
        //String sqlQuery = "SET ROLE ? IDENTIFIED BY ?";
        String sqlQuery = texto;
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter(1, rol);
        query.setParameter(2, pwd);
        int resultado = query.executeUpdate();
        em.getTransaction().commit();
    }

    public int cambiarClave(EntityManager eManager, String usuario, String nuevaClave) {
        try {
            em = eManager;
            em.getTransaction().begin();
            String sqlQuery = "ALTER USER " + usuario + " IDENTIFIED BY " + nuevaClave;
            Query query = em.createNativeQuery(sqlQuery);
            int resultado = query.executeUpdate();
            em.getTransaction().commit();
            return resultado;
        } catch (Exception e) {
            Throwable t = getLastThrowable(e);
            SQLException exxx = (SQLException) t;
            em.getTransaction().rollback();
            return exxx.getErrorCode();
        }
    }
}
