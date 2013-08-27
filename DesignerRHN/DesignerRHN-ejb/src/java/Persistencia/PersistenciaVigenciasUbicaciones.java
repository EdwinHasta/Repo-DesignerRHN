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

@Stateless
public class PersistenciaVigenciasUbicaciones implements PersistenciaVigenciasUbicacionesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear vigenciasCargos.
     */
    @Override
    public void crear(VigenciasUbicaciones vigenciaUbicacion) {
        try {
            em.merge(vigenciaUbicacion);
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada (VigenciasUbicaciones)");
        }
    }

    /*
     *Editar vigenciasCargos. 
     */
    @Override
    public void editar(VigenciasUbicaciones vigenciaUbicacion) {
        try {
            em.merge(vigenciaUbicacion);
        } catch (Exception e) {
            System.out.println("No se pudo modificar la Vigencias Ubicacion");
        }
    }

    /*
     *Borrar vigenciasCargos.
     */
    @Override
    public void borrar(VigenciasUbicaciones vigenciaUbicacion) {
        em.remove(em.merge(vigenciaUbicacion));
    }

    /*
     *Encontrar una vigenciasCargos. 
     */
    @Override
    public VigenciasUbicaciones buscarVigenciaUbicacion(Object id) {
        try {
            BigInteger in = (BigInteger) id;
            //return em.find(VigenciasCargos.class, id);
            return em.find(VigenciasUbicaciones.class, in);
        } catch (Exception e) {
            return null;
        }
    }

    /*
     *Encontrar todas las vigenciasCargos.
     */
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
