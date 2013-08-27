
package Persistencia;

import javax.ejb.Stateless;
import Entidades.VigenciasReformasLaborales;
import InterfacePersistencia.PersistenciaVigenciasReformasLaboralesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author user
 */
@Stateless

public class PersistenciaVigenciasReformasLaborales implements PersistenciaVigenciasReformasLaboralesInterface{
    
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear vigenciasReformalaboral.
     */

    public void crear(VigenciasReformasLaborales vigenciaRefLab) {
        try {
            em.merge(vigenciaRefLab);
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada (VigenciasReformaLaboral)");
        }
    }

    /*
     *Editar vigenciasReformalaboral. 
     */

    @Override
    public void editar(VigenciasReformasLaborales vigenciaRefLab) {
        try {
            em.merge(vigenciaRefLab);
        } catch (Exception e) {
            System.out.println("No se pudo modificar la Vigencias ReformaLaboral");
        }
    }

    /*
     *Borrar vigenciasReformalaboral.
     */

    @Override
    public void borrar(VigenciasReformasLaborales vigenciaRefLab) {
        em.remove(em.merge(vigenciaRefLab));
    }

    /*
     *Encontrar una vigenciasReformalaboral. 
     */

    @Override
    public VigenciasReformasLaborales buscarVigenciaRefLab(Object id) {
        try {
            BigInteger in = (BigInteger) id;
            //return em.find(VigenciasCargos.class, id);
            return em.find(VigenciasReformasLaborales.class, in);
        } catch (Exception e) {
            return null;
        }
    }

    /*
     *Encontrar todas las vigenciasReformalaboral.
     */

    @Override
    public List<VigenciasReformasLaborales> buscarVigenciasRefLab() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasReformasLaborales.class));
        return em.createQuery(cq).getResultList();
    }
    

    @Override
    public List<VigenciasReformasLaborales> buscarVigenciasReformasLaboralesEmpleado(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT vrl FROM VigenciasReformasLaborales vrl WHERE vrl.empleado.secuencia = :secuenciaEmpl ORDER BY vrl.fechavigencia DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);
            
            List<VigenciasReformasLaborales> vigenciasRefLab = query.getResultList();
            return vigenciasRefLab;
        } catch (Exception e) {
            System.out.println("Error en Persistencia Vigencias Reforma Laboral " + e);
            return null;
        }
    }
    
    @Override
    public VigenciasReformasLaborales buscarVigenciaReformaLaboralSecuencia(BigInteger secVRL){
        try{
            Query query = em.createNamedQuery("VigenciasReformasLaborales.findBySecuencia").setParameter("secuencia", secVRL);
            VigenciasReformasLaborales vigenciaRefLab = (VigenciasReformasLaborales)query.getSingleResult();
            return vigenciaRefLab;
        }catch(Exception e){
            return null;
        }
    }
    

}
