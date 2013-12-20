/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VigenciasEventos;
import InterfacePersistencia.PersistenciaVigenciasEventosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasEventos'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVigenciasEventos implements PersistenciaVigenciasEventosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(VigenciasEventos vigenciasEventos) {
        em.persist(vigenciasEventos);
    }

    @Override
    public void editar(VigenciasEventos vigenciasEventos) {
        em.merge(vigenciasEventos);
    }

    @Override
    public void borrar(VigenciasEventos vigenciasEventos) {
        em.remove(em.merge(vigenciasEventos));
    }

    @Override
    public List<VigenciasEventos> eventosEmpleado(BigInteger secuenciaEmpl) {
        try {
            Query query = em.createQuery("SELECT COUNT(ve) FROM VigenciasEventos ve WHERE ve.empleado.secuencia = :secuenciaEmpl");
            query.setParameter("secuenciaEmpl", secuenciaEmpl);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT ve FROM VigenciasEventos ve WHERE ve.empleado.secuencia = :secuenciaEmpl and ve.fechainicial = (SELECT MAX(vev.fechainicial) FROM VigenciasEventos vev WHERE vev.empleado.secuencia = :secuenciaEmpl)");
                queryFinal.setParameter("secuenciaEmpl", secuenciaEmpl);
                List<VigenciasEventos> listaVigenciasEventos = queryFinal.getResultList();
                return listaVigenciasEventos;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasEventos.eventosPersona" + e);
            return null;
        }
    }

    @Override
    public List<VigenciasEventos> vigenciasEventosSecuenciaEmpleado(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT ve FROM VigenciasEventos ve WHERE ve.empleado.secuencia = :secuenciaEmpl");
            query.setParameter("secuenciaEmpl", secuencia);
            List<VigenciasEventos> resultado = (List<VigenciasEventos>) query.getResultList();
            return resultado;
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasEventos.vigenciasEventosSecuenciaEmpleado" + e);
            return null;
        }
    }
}
