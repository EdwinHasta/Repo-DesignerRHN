package Persistencia;

import Entidades.VigenciasProrrateosProyectos;
import InterfacePersistencia.PersistenciaVigenciasProrrateosProyectosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaVigenciasProrrateosProyectos implements PersistenciaVigenciasProrrateosProyectosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear VigenciasProrrateosProyectos.
     */
    @Override
    public void crear(VigenciasProrrateosProyectos vigenciasProrrateosProyectos) {
        try {
            em.persist(vigenciasProrrateosProyectos);
        } catch (Exception e) {
            System.out.println("El registro VigenciasProrrateosProyectos no exite o esta reservada por lo cual no puede ser modificada (VigenciasProrrateosProyectos)");
        }
    }

    /*
     *Editar VigenciasProrrateosProyectos. 
     */
    @Override
    public void editar(VigenciasProrrateosProyectos vigenciasProrrateosProyectos) {
        try {
            em.merge(vigenciasProrrateosProyectos);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el registro VigenciasProrrateosProyectos");
        }
    }

    /*
     *Borrar VigenciasProrrateosProyectos.
     */
    @Override
    public void borrar(VigenciasProrrateosProyectos vigenciasProrrateosProyectos) {
        try {
            System.out.println("");
            em.remove(em.merge(vigenciasProrrateosProyectos));
        } catch (Exception e) {
            System.out.println("No se pudo borrar el registro VigenciasProrrateosProyectos");
        }
    }

    /*
     *Encontrar un VigenciasProrrateosProyectos. 
     */
    @Override
    public VigenciasProrrateosProyectos buscarVigenciaProrrateoProyecto(Object id) {
        try {
            BigInteger in = (BigInteger) id;
            return em.find(VigenciasProrrateosProyectos.class, in);
        } catch (Exception e) {
            System.out.println("Error buscarVigenciaProrrateo PersistenciaVigenciasProrrateos");
            return null;
        }
    }

    /*
     *Encontrar todos los VigenciasProrrateosProyectos.
     */
    @Override
    public List<VigenciasProrrateosProyectos> buscarVigenciasProrrateosProyectos() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VigenciasProrrateosProyectos.class));
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasProrrateosProyectos PersistenciaVigenciasProrrateosProyectos");
            return null;
        }
    }

    @Override
    public List<VigenciasProrrateosProyectos> buscarVigenciasProrrateosProyectosEmpleado(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT vpp FROM VigenciasProrrateosProyectos vpp WHERE vpp.vigencialocalizacion.empleado.secuencia = :secuenciaEmpl ORDER BY vpp.fechainicial DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);
            List<VigenciasProrrateosProyectos> prorrateosProyectos = query.getResultList();
            return prorrateosProyectos;
        } catch (Exception e) {
            System.out.println("Error en buscarVigenciasProrrateosProyectosEmpleado PersistenciaVigenciasProrrateosProyectos " + e);
            return null;
        }
    }

    @Override
    public VigenciasProrrateosProyectos buscarVigenciasProrrateosProyectosSecuencia(BigInteger secVPP) {
        try {
            Query query = em.createNamedQuery("VigenciasProrrateosProyectos.findBySecuencia").setParameter("secuencia", secVPP);
            VigenciasProrrateosProyectos prorrateosProyectos = (VigenciasProrrateosProyectos) query.getSingleResult();
            return prorrateosProyectos;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasProrrateosProyectosSecuencia PersistenciaVigenciasProrrateosProyectos");
            return null;
        }
    }

    @Override
    public List<VigenciasProrrateosProyectos> buscarVigenciasProrrateosProyectosVigenciaSecuencia(BigInteger secVigencia) {
        try {
            Query query = em.createQuery("SELECT vpp FROM VigenciasProrrateosProyectos vpp WHERE vpp.vigencialocalizacion.secuencia = :secVigencia");
            query.setParameter("secVigencia", secVigencia);
            List<VigenciasProrrateosProyectos> prorrateosProyectos = query.getResultList();
            return prorrateosProyectos;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasProrrateosProyectosVigenciaSecuencia PersistenciaVigenciasProrrateosProyectos");
            return null;
        }
    }
}
