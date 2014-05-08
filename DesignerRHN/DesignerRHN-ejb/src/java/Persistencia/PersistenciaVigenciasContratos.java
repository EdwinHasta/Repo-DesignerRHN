/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VigenciasContratos;
import InterfacePersistencia.PersistenciaVigenciasContratosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasContratos'
 * de la base de datos.
 * @author Andres Pineda.
 */
@Stateless
public class PersistenciaVigenciasContratos implements PersistenciaVigenciasContratosInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
*/

    @Override
    public void crear(EntityManager em, VigenciasContratos vigenciasContratos) {
        try {
            em.merge(vigenciasContratos);
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada (VigenciasReformaLaboral)");
        }
    }

    @Override
    public void editar(EntityManager em, VigenciasContratos vigenciasContratos) {
        try {
            em.merge(vigenciasContratos);
        } catch (Exception e) {
            System.out.println("No se pudo modificar la Vigencias Contratos");
        }
    }

    @Override
    public void borrar(EntityManager em, VigenciasContratos vigenciasContratos) {
        try{
            em.remove(em.merge(vigenciasContratos));
        }catch(Exception e){
            System.out.println("la vigencia contrato no se ha podido eliminar");
        }
    }

    @Override
    public List<VigenciasContratos> buscarVigenciasContratos(EntityManager em) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasContratos.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<VigenciasContratos> buscarVigenciaContratoEmpleado(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT vc FROM VigenciasContratos vc WHERE vc.empleado.secuencia = :secuenciaEmpl ORDER BY vc.fechainicial DESC");
            query.setParameter("secuenciaEmpl", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<VigenciasContratos> vigenciasC = query.getResultList();
            return vigenciasC;
        } catch (Exception e) {
            System.out.println("Error en Persistencia Vigencias Contratos " + e);
            return null;
        }
    }
    
    @Override
    public VigenciasContratos buscarVigenciaContratoSecuencia(EntityManager em, BigInteger secuencia){
        try{
            Query query = em.createNamedQuery("VigenciasContratos.findBySecuencia").setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            VigenciasContratos vigenciaC = (VigenciasContratos)query.getSingleResult();
            return vigenciaC;
        }catch(Exception e){
            return null;
        }
    }
}
