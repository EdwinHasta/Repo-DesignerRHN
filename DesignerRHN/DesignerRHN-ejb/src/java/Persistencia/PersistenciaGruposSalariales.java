/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.GruposSalariales;
import InterfacePersistencia.PersistenciaGruposSalarialesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'GruposSalariales'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaGruposSalariales implements PersistenciaGruposSalarialesInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(GruposSalariales gruposSalariales) {
        try {
            em.persist(gruposSalariales);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaGruposSalariales : " + e.toString());
        }
    }

    @Override
    public void editar(GruposSalariales gruposSalariales) {
        try {
            em.merge(gruposSalariales);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaGruposSalariales : " + e.toString());
        }
    }

    @Override
    public void borrar(GruposSalariales gruposSalariales) {
        try {
            em.remove(em.merge(gruposSalariales));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaGruposSalariales : " + e.toString());
        }
    }

    @Override
    public List<GruposSalariales> buscarGruposSalariales() {
        try {
            Query query = em.createQuery("SELECT gs FROM GruposSalariales gs ORDER BY gs.secuencia");
            List<GruposSalariales> gruposSalariales = query.getResultList();
            return gruposSalariales;
        } catch (Exception e) {
            System.out.println("Error buscarGruposSalariales PersistenciaGruposSalariales : " + e.toString());
            return null;
        }
    }

    @Override
    public GruposSalariales buscarGrupoSalarialSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT gs FROM GruposSalariales gs WHERE gs.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            GruposSalariales gruposSalariales = (GruposSalariales) query.getSingleResult();
            return gruposSalariales;
        } catch (Exception e) {
            System.out.println("Error buscarGrupoSalarialSecuencia PersistenciaGruposSalariales : " + e.toString());
            GruposSalariales gruposSalariales = null;
            return gruposSalariales;
        }

    }
}
