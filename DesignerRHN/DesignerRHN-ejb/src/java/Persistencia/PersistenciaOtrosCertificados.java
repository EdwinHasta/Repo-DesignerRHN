/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.OtrosCertificados;
import InterfacePersistencia.PersistenciaOtrosCertificadosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'OtrosCertificados' de
 * la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaOtrosCertificados implements PersistenciaOtrosCertificadosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;
    @Override
    public void crear(EntityManager em, OtrosCertificados certificados) {
        try {
            em.getTransaction().begin();
            em.merge(certificados);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaOtrosCertificados : " + e.toString());
        }
    }

    @Override
    public void editar(EntityManager em, OtrosCertificados certificados) {
        try {
            em.getTransaction().begin();
            em.merge(certificados);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaOtrosCertificados : " + e.toString());
        }
    }

    @Override
    public void borrar(EntityManager em, OtrosCertificados certificados) {
        try {
            em.getTransaction().begin();
            em.remove(em.merge(certificados));
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaOtrosCertificados : " + e.toString());
        }
    }

    @Override
    public List<OtrosCertificados> buscarOtrosCertificados(EntityManager em) {
        Query query = em.createNamedQuery("OtrosCertificados.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<OtrosCertificados> certificados = (List<OtrosCertificados>) query.getResultList();
        return certificados;
    }

    @Override
    public OtrosCertificados buscarOtroCertificadoSecuencia(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT oc FROM OtrosCertificados oc WHERE oc.secuencia= :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            OtrosCertificados certificados = (OtrosCertificados) query.getResultList();
            return certificados;
        } catch (Exception e) {
            OtrosCertificados certificados = null;
            return certificados;
        }
    }

    @Override
    public List<OtrosCertificados> buscarOtrosCertificadosEmpleado(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT oc FROM OtrosCertificados oc WHERE oc.empleado.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<OtrosCertificados> certificados = (List<OtrosCertificados>) query.getResultList();
            return certificados;
        } catch (Exception e) {
            List<OtrosCertificados> certificados = null;
            return certificados;
        }
    }
}
