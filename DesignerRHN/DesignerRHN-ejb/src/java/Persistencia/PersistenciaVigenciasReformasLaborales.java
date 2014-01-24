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
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(VigenciasReformasLaborales vigenciaRefLab) {
        try {
            em.persist(vigenciaRefLab);
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada (VigenciasReformaLaboral)");
        }
    }

    @Override
    public void editar(VigenciasReformasLaborales vigenciaRefLab) {
        try {
            em.merge(vigenciaRefLab);
        } catch (Exception e) {
            System.out.println("No se pudo modificar la Vigencias ReformaLaboral");
        }
    }

    @Override
    public void borrar(VigenciasReformasLaborales vigenciaRefLab) {
        em.remove(em.merge(vigenciaRefLab));
    }

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
