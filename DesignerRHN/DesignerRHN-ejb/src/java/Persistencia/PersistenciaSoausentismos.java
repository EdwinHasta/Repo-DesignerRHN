/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Soausentismos;
import InterfacePersistencia.PersistenciaSoausentismosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'Soausentismos'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaSoausentismos implements PersistenciaSoausentismosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Soausentismos soausentismos) {
        try {
            em.merge(soausentismos);
        } catch (PersistenceException ex) {
            System.out.println("Error PersistenciaSoausentismos.crear");
        }
    }

    @Override
    public void editar(Soausentismos soausentismos) {
        em.merge(soausentismos);
    }

    @Override
    public void borrar(Soausentismos soausentismos) {
        em.remove(em.merge(soausentismos));
    }

    @Override
    public List<Soausentismos> ausentismosEmpleado(BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT soa FROM Soausentismos soa WHERE soa.empleado.secuencia= :secuenciaEmpleado");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            List<Soausentismos> todosAusentismos = query.getResultList();
            return todosAusentismos;
        } catch (Exception e) {
            System.out.println("Error: (todasNovedades)" + e);
            return null;
        }
    }

    @Override
    public List<Soausentismos> prorrogas(BigInteger secuenciaEmpleado, BigInteger secuenciaCausa, BigInteger secuenciaAusentismo) {
        try {
            Query query = em.createQuery("SELECT soa FROM Soausentismos soa WHERE soa.empleado.secuencia= :secuenciaEmpleado AND soa.causa.secuencia= :secuenciaCausa AND soa.secuencia= :secuenciaAusentismo");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            query.setParameter("secuenciaCausa", secuenciaCausa);
            query.setParameter("secuenciaAusentismo", secuenciaAusentismo);
            List<Soausentismos> prorrogas = query.getResultList();
            return prorrogas;
        } catch (Exception e) {
            System.out.println("Error: (prorrogas)" + e);
            return null;
        }
    }

    @Override
    public String prorrogaMostrar(BigInteger secuenciaProrroga) {
        try {
            String sqlQuery = ("SELECT nvl(A.NUMEROCERTIFICADO,'Falta # Certificado')||':'||A.fecha||'->'||A.fechafinaus\n"
                    + "FROM SOAUSENTISMOS A\n"
                    + "WHERE A.SECUENCIA = ?");
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuenciaProrroga);
            String resultado = (String) query.getSingleResult();
            return resultado;
        } catch (Exception e) {
            System.out.println("Error: (prorrogaMostrar)" + e);
            return null;
        }
    }     
}
