package Persistencia;

import Entidades.Proyectos;
import InterfacePersistencia.PersistenciaProyectosInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaProyectos implements PersistenciaProyectosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear Proyectos
     */
    @Override
    public void crear(Proyectos proyectos) {
        try {
            em.persist(proyectos);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaProyectos");
        }
    }

    /*
     *Editar Proyectos
     */
    @Override
    public void editar(Proyectos proyectos) {
        try {
            em.merge(proyectos);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaProyectos");
        }
    }

    /*
     *Borrar Proyectos
     */
    @Override
    public void borrar(Proyectos proyectos) {
        try {
            em.remove(em.merge(proyectos));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaProyectos");
        }
    }

    /*
     *Encontrar un Proyectos
     */
    @Override
    public Proyectos buscarProyecto(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            return em.find(Proyectos.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarProyecto PersistenciaProyectos");
            return null;
        }

    }

    /*
     *Encontrar todas los proyectos
     */
    @Override
    public List<Proyectos> buscarProyectos() {
        try {
            List<Proyectos> proyectos = (List<Proyectos>) em.createNamedQuery("Proyectos.findAll").getResultList();
            return proyectos;
        } catch (Exception e) {
            System.out.println("Error buscarProyectos PersistenciaProyectos");
            return null;
        }
    }

    @Override
    public Proyectos buscarProyectoSecuencia(BigDecimal secuencia) {

        try {
            Query query = em.createQuery("SELECT p FROM Proyectos p WHERE p.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            Proyectos proyectos = (Proyectos) query.getSingleResult();
            return proyectos;
        } catch (Exception e) {
            System.out.println("Error buscarProyectoSecuencia PersistenciaProyectos");
            Proyectos proyectos = null;
            return proyectos;
        }

    }
}
