/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VigenciasConceptosTC;
import InterfacePersistencia.PersistenciaVigenciasConceptosTCInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasConceptosTC'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVigenciasConceptosTC implements PersistenciaVigenciasConceptosTCInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(VigenciasConceptosTC vigenciasConceptosTC) {
        try {
            em.persist(vigenciasConceptosTC);
        } catch (Exception e) {
            System.out.println("Error crearVigenciasConceptosTC Persistencia : " + e.toString());
        }
    }

    @Override
    public void editar(VigenciasConceptosTC vigenciasConceptosTC) {
        try {
            em.merge(vigenciasConceptosTC);
        } catch (Exception e) {
            System.out.println("Error crearVigenciasConceptosTC Persistencia : " + e.toString());
        }
    }

    @Override
    public void borrar(VigenciasConceptosTC vigenciasConceptosTC) {
        try {
            em.remove(em.merge(vigenciasConceptosTC));
        } catch (Exception e) {
            System.out.println("Error crearVigenciasConceptosTC Persistencia : " + e.toString());
        }
    }

    @Override
    public boolean verificacionZonaTipoContrato(BigInteger secuenciaC, BigInteger secuenciaTC) {
        try {
            Query query = em.createQuery("SELECT COUNT(vcTC) FROM VigenciasConceptosTC vcTC WHERE vcTC.concepto.secuencia = :secuenciaConcepto AND vcTC.tipocontrato.secuencia = :secuenciaTC");
            query.setParameter("secuenciaConcepto", secuenciaC);
            query.setParameter("secuenciaTC", secuenciaTC);
            Long resultado = (Long) query.getSingleResult();
            return resultado > 0;
        } catch (Exception e) {
            System.out.println("Exepcion PersistenciaVigenciasConceptosTC: " + e);
            return false;
        }
    }

    @Override
    public List<VigenciasConceptosTC> listVigenciasConceptosTCPorConcepto(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT vcTC FROM VigenciasConceptosTC vcTC WHERE vcTC.concepto.secuencia = :secuenciaConcepto");
            query.setParameter("secuenciaConcepto", secuencia);
            List<VigenciasConceptosTC> resultado = (List<VigenciasConceptosTC>) query.getResultList();
            return resultado;
        } catch (Exception e) {
            System.out.println("Exepcion listVigenciasConceptosTCPorConcepto PersistenciaVigenciasConceptosTC : " + e.toString());
            return null;
        }
    }
}
