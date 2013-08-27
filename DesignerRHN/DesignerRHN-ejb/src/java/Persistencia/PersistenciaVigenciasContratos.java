
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
 *
 * @author user
 */

@Stateless
public class PersistenciaVigenciasContratos implements PersistenciaVigenciasContratosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear VigenciasContratos.
     */

    @Override
    public void crear(VigenciasContratos vigenciasContratos) {
        try {
            em.merge(vigenciasContratos);
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada (VigenciasReformaLaboral)");
        }
    }

    /*
     *Editar VigenciasContratos. 
     */

    @Override
    public void editar(VigenciasContratos vigenciasContratos) {
        try {
            em.merge(vigenciasContratos);
        } catch (Exception e) {
            System.out.println("No se pudo modificar la Vigencias Contratos");
        }
    }

    /*
     *Borrar VigenciasContratos.
     */

    @Override
    public void borrar(VigenciasContratos vigenciasContratos) {
        try{
            em.remove(em.merge(vigenciasContratos));
        }catch(Exception e){
            System.out.println("la vigencia contrato no se ha podido eliminar");
        }
    }

    /*
     *Encontrar una VigenciasContratos. 
     */

    @Override
    public VigenciasContratos buscarVigenciaContrato(Object id) {
        try {
            BigInteger in = (BigInteger) id;
            return em.find(VigenciasContratos.class, in);
        } catch (Exception e) {
            return null;
        }
    }

    /*
     *Encontrar todas las VigenciasContratos.
     */

    @Override
    public List<VigenciasContratos> buscarVigenciasContratos() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasContratos.class));
        return em.createQuery(cq).getResultList();
    }
    

    @Override
    public List<VigenciasContratos> buscarVigenciaContratoEmpleado(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT vc FROM VigenciasContratos vc WHERE vc.empleado.secuencia = :secuenciaEmpl ORDER BY vc.fechainicial DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);
            
            List<VigenciasContratos> vigenciasC = query.getResultList();
            return vigenciasC;
        } catch (Exception e) {
            System.out.println("Error en Persistencia Vigencias Contratos " + e);
            return null;
        }
    }
    
    @Override
    public VigenciasContratos buscarVigenciaContratoSecuencia(BigInteger secVC){
        try{
            Query query = em.createNamedQuery("VigenciasContratos.findBySecuencia").setParameter("secuencia", secVC);
            VigenciasContratos vigenciaC = (VigenciasContratos)query.getSingleResult();
            return vigenciaC;
        }catch(Exception e){
            return null;
        }
    }
    

   

}
