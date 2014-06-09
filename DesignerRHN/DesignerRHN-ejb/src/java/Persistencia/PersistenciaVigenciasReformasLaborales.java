/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import javax.ejb.Stateless;
import Entidades.VigenciasReformasLaborales;
import InterfacePersistencia.PersistenciaVigenciasReformasLaboralesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasReformasLaborales'
 * de la base de datos.
 * @author Andres Pineda
 */
@Stateless
public class PersistenciaVigenciasReformasLaborales implements PersistenciaVigenciasReformasLaboralesInterface{
    
   /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    @Override
    public void crear(EntityManager em, VigenciasReformasLaborales vigenciaRefLab) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(vigenciaRefLab);
            tx.commit();
        } catch (Exception e) {
            System.out.println("PersistenciaVigenciasReformasLaborales La vigencia no exite o esta reservada por lo cual no puede ser modificada: " + e);
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("No se puede hacer rollback porque no hay una transacción");
            }
        }
    }

    @Override
    public void editar(EntityManager em, VigenciasReformasLaborales vigenciaRefLab) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(vigenciaRefLab);
            tx.commit();
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada: " + e);
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("No se puede hacer rollback porque no hay una transacción");
            }
        }
    }

    @Override
    public void borrar(EntityManager em, VigenciasReformasLaborales vigenciaRefLab) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(vigenciaRefLab));
            tx.commit();
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada: " + e);
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("No se puede hacer rollback porque no hay una transacción");
            }
        }
        
    }

    @Override
    public List<VigenciasReformasLaborales> buscarVigenciasRefLab(EntityManager em) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasReformasLaborales.class));
        return em.createQuery(cq).getResultList();
    }
    
    @Override
    public List<VigenciasReformasLaborales> buscarVigenciasReformasLaboralesEmpleado(EntityManager em, BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT vrl FROM VigenciasReformasLaborales vrl WHERE vrl.empleado.secuencia = :secuenciaEmpl ORDER BY vrl.fechavigencia DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<VigenciasReformasLaborales> vigenciasRefLab = query.getResultList();
            return vigenciasRefLab;
        } catch (Exception e) {
            System.out.println("Error en Persistencia Vigencias Reforma Laboral " + e);
            return null;
        }
    }
    
    @Override
    public VigenciasReformasLaborales buscarVigenciaReformaLaboralSecuencia(EntityManager em, BigInteger secVRL){
        try{
            Query query = em.createNamedQuery("VigenciasReformasLaborales.findBySecuencia").setParameter("secuencia", secVRL);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            VigenciasReformasLaborales vigenciaRefLab = (VigenciasReformasLaborales)query.getSingleResult();
            return vigenciaRefLab;
        }catch(Exception e){
            return null;
        }
    }
}
