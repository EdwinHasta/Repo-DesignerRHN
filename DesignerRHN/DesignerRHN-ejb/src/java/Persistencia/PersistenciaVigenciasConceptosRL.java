/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VigenciasConceptosRL;
import InterfacePersistencia.PersistenciaVigenciasConceptosRLInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasConceptosRL'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVigenciasConceptosRL implements PersistenciaVigenciasConceptosRLInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
*/

    @Override
    public void crear(EntityManager em, VigenciasConceptosRL vigenciasConceptosRL) {
        try {
            em.persist(vigenciasConceptosRL);
        } catch (Exception e) {
            System.out.println("Error crearVigenciasConceptosRL Persistencia : " + e.toString());
        }
    }

    @Override
    public void editar(EntityManager em, VigenciasConceptosRL vigenciasConceptosRL) {
        try {
            em.merge(vigenciasConceptosRL);
        } catch (Exception e) {
            System.out.println("Error crearVigenciasConceptosRL Persistencia : " + e.toString());
        }
    }

    @Override
    public void borrar(EntityManager em, VigenciasConceptosRL vigenciasConceptosRL) {
        try {
            em.remove(em.merge(vigenciasConceptosRL));
        } catch (Exception e) {
            System.out.println("Error crearVigenciasConceptosRLa Persistencia : " + e.toString());
        }
    }
    
    @Override
    public boolean verificacionZonaTipoReformasLaborales(EntityManager em, BigInteger secuenciaC, BigInteger secuenciaTS) {
        try {
            Query query = em.createQuery("SELECT COUNT(vcRL) FROM VigenciasConceptosRL vcRL WHERE vcRL.concepto.secuencia = :secuenciaConcepto AND vcRL.tiposalario.secuencia = :secuenciaTS");
            query.setParameter("secuenciaConcepto", secuenciaC);
            query.setParameter("secuenciaTS", secuenciaTS);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long resultado = (Long) query.getSingleResult();
            return resultado > 0;
        } catch (Exception e) {
            System.out.println("Exepcion PersistenciaVigenciasConceptosRL: " + e);
            return false;
        }
    }
    
    @Override
    public List<VigenciasConceptosRL> listVigenciasConceptosRLPorConcepto(EntityManager em, BigInteger secuencia){
        try {
            Query query = em.createQuery("SELECT vcRL FROM VigenciasConceptosRL vcRL WHERE vcRL.concepto.secuencia = :secuenciaConcepto");
            query.setParameter("secuenciaConcepto", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<VigenciasConceptosRL> resultado = (List<VigenciasConceptosRL>) query.getResultList();
            return resultado;
        } catch (Exception e) {
            System.out.println("Exepcion PersistenciaVigenciasConceptosRL: " + e);
            return null;
        }
    }
}
