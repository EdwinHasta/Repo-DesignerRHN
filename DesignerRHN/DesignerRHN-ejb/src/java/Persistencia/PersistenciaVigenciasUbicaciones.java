/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VigenciasUbicaciones;
import InterfacePersistencia.PersistenciaVigenciasUbicacionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasUbicaciones'
 * de la base de datos.
 * @author AndresPineda
 */
@Stateless
public class PersistenciaVigenciasUbicaciones implements PersistenciaVigenciasUbicacionesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(VigenciasUbicaciones vigenciaUbicacion) {
        try {
            em.merge(vigenciaUbicacion);
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada (VigenciasUbicaciones)");
        }
    }

    @Override
    public void editar(VigenciasUbicaciones vigenciaUbicacion) {
        try {
            em.merge(vigenciaUbicacion);
        } catch (Exception e) {
            System.out.println("No se pudo modificar la Vigencias Ubicacion");
        }
    }

    @Override
    public void borrar(VigenciasUbicaciones vigenciaUbicacion) {
        em.remove(em.merge(vigenciaUbicacion));
    }

    @Override
    public VigenciasUbicaciones buscarVigenciaUbicacion(BigInteger secuencia) {
        try {
            return em.find(VigenciasUbicaciones.class, secuencia);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<VigenciasUbicaciones> buscarVigenciasUbicaciones() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasUbicaciones.class));
        return em.createQuery(cq).getResultList();
    }
    
    @Override
    public List<VigenciasUbicaciones> buscarVigenciaUbicacionesEmpleado(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT vu FROM VigenciasUbicaciones vu WHERE vu.empleado.secuencia = :secuenciaEmpl ORDER BY vu.fechavigencia DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);
            List<VigenciasUbicaciones> vigenciasUbicaciones = query.getResultList();
            return vigenciasUbicaciones;
        } catch (Exception e) {
            System.out.println("Error en Persistencia Vigencias Ubicaciones " + e);
            return null;
        }
    }
}
