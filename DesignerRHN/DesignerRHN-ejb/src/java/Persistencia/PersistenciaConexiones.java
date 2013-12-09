package Persistencia;

import Entidades.Conexiones;
import InterfacePersistencia.PersistenciaConexionesInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

@Stateless
public class PersistenciaConexiones implements PersistenciaConexionesInterface {

    /*
     * Crear conexión.
     */
    @Override
    public void crear_Modificar(Conexiones conexion, EntityManager em) {
        try {
            em.getTransaction().begin();
            em.merge(conexion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("Error PersistenciaConexiones.crear: " + ex);
        }
    }

    @Override
    public BigInteger buscarSID(EntityManager em) {
        try {
            em.getTransaction().begin();
            String sqlQuery = "select sys_context('USERENV','SID') FROM DUAL";
            Query query = em.createNativeQuery(sqlQuery);
            String strSID = (String) query.getSingleResult();
            em.getTransaction().commit();
            BigInteger SID = new BigInteger(strSID);
            return SID;
        } catch (Exception e) {
            System.out.println("Error PersistenciaConexiones.buscarSID: " + e);
            return null;
        }
    }

    @Override
    public void verificarSID(EntityManager em, Conexiones conexion) {
        try {
            BigInteger SID = buscarSID(em);
            em.getTransaction().begin();
            Query query = em.createQuery("SELECT COUNT(c) FROM Conexiones c WHERE c.sid = :SID");
            query.setParameter("SID", SID);
            Long conteo = (Long) query.getSingleResult();
            em.getTransaction().commit();
            conexion.setSid(SID);
            if (conteo == 0) {
                crear_Modificar(conexion, em);
            } else {
                em.getTransaction().begin();
                query = em.createQuery("SELECT c.secuencia FROM Conexiones c WHERE c.sid = :SID");
                query.setParameter("SID", SID);
                BigInteger secuenciaConexion = (BigInteger) query.getSingleResult();
                em.getTransaction().commit();
                conexion.setSecuencia(secuenciaConexion);
                crear_Modificar(conexion, em);
            }
        } catch (Exception e) {
            System.out.println("Error PersistenciaConexiones.verificarSID: " + e);
        }
    }
}
