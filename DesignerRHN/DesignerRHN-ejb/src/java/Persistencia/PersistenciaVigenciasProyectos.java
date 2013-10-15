package Persistencia;

import Entidades.VigenciasProyectos;
import InterfacePersistencia.PersistenciaVigenciasProyectosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

@Stateless
public class PersistenciaVigenciasProyectos implements PersistenciaVigenciasProyectosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
    
    @Override
    public void crear(VigenciasProyectos vigenciasProyectos) {
        try {
//            System.out.println("Persona: " + vigenciasFormales.getPersona().getNombreCompleto());
            em.merge(vigenciasProyectos);
        } catch (PersistenceException ex) {
            System.out.println("Error PersistenciaVigenciasFormales.crear");
        }
    }
       
     
    // Editar Vigencias Proyectos. 
     
    @Override
    public void editar(VigenciasProyectos vigenciasProyectos) {
        em.merge(vigenciasProyectos);
    }

    /*
     *Borrar VigenciasProyectos.
     */
    @Override
    public void borrar(VigenciasProyectos vigenciasProyectos) {
        em.remove(em.merge(vigenciasProyectos));
    }
    
     /*
     *Encontrar todas las Vigencias de Proyectos.
     */
    @Override
    public List<VigenciasProyectos> buscarVigenciasProyectos() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasProyectos.class));
        return em.createQuery(cq).getResultList();
    }
    

    @Override
    public List<VigenciasProyectos> proyectosPersona(BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT COUNT(vp) FROM VigenciasProyectos vp WHERE vp.empleado.secuencia = :secuenciaEmpleado");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT vp FROM VigenciasProyectos vp WHERE vp.empleado.secuencia = :secuenciaEmpleado and vp.fechainicial = (SELECT MAX(vpr.fechainicial) FROM VigenciasProyectos vpr WHERE vpr.empleado.secuencia = :secuenciaEmpleado)");
                queryFinal.setParameter("secuenciaEmpleado", secuenciaEmpleado);
                List<VigenciasProyectos> listaVigenciasProyectos = queryFinal.getResultList();
                return listaVigenciasProyectos;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasProyectos.proyectosPersona" + e);
            return null;
        }
    }
    
     //METODO PARA TRAER LAS VIGENCIAS DE UNA PERSONA

    @Override
    public List<VigenciasProyectos> vigenciasProyectosEmpleado(BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT vp FROM VigenciasProyectos vp WHERE vp.empleado.secuencia= :secuenciaEmpleado ORDER BY vp.fechainicial DESC");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            List<VigenciasProyectos> listaVigenciasProyectos = query.getResultList();
            return listaVigenciasProyectos;
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasProyectos.vigenciasProyectosEmpleado" + e);
            return null;
        }
    }
    
    
    
}
