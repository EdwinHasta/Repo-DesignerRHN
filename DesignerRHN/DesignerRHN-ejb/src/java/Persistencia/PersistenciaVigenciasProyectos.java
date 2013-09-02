package Persistencia;

import Entidades.VigenciasProyectos;
import InterfaceAdministrar.PersistenciaVigenciasProyectosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVigenciasProyectos implements PersistenciaVigenciasProyectosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

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
}
