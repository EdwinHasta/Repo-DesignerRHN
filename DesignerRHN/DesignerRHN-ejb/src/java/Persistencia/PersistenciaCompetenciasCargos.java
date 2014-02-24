/**
 * Documentación a cargo de AndresPineda
 */
package Persistencia;

import Entidades.Competenciascargos;
import InterfacePersistencia.PersistenciaCompetenciasCargosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'CompetenciasCargos'
 * de la base de datos.
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaCompetenciasCargos implements PersistenciaCompetenciasCargosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Competenciascargos competenciascargos) {
        try {
            em.persist(competenciascargos);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaCompetenciasCargos : " + e.toString());
        }
    }

    @Override
    public void editar(Competenciascargos competenciascargos) {
        try {
            em.merge(competenciascargos);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaCompetenciasCargos : " + e.toString());
        }
    }

    @Override
    public void borrar(Competenciascargos competenciascargos) {
        try {
            em.remove(em.merge(competenciascargos));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaCompetenciasCargos : " + e.toString());
        }
    }

    @Override
    public List<Competenciascargos> buscarCompetenciasCargos() {
        try {
            Query query = em.createQuery("SELECT cc FROM Competenciascargos cc ORDER BY cc.peso ASC");
            List<Competenciascargos> competenciascargos = query.getResultList();
            return competenciascargos;
        } catch (Exception e) {
            System.out.println("Error buscarCompetenciasCargos PersistenciaCompetenciasCargos : " + e.toString());
            return null;
        }
    }

    @Override
    public Competenciascargos buscarCompetenciasCargosSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT cc FROM Competenciascargos cc WHERE cc.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            Competenciascargos competenciascargos = (Competenciascargos) query.getSingleResult();
            return competenciascargos;
        } catch (Exception e) {
            System.out.println("Error buscarCompetenciasCargosSecuencia PersistenciaCompetenciasCargos : " + e.toString());
            Competenciascargos competenciascargos = null;
            return competenciascargos;
        }
    }

    @Override
    public List<Competenciascargos> buscarCompetenciasCargosParaSecuenciaCargo(BigInteger secCargo) {
        try {
            Query query = em.createQuery("SELECT cc FROM Competenciascargos cc WHERE cc.cargo.secuencia=:secCargo");
            query.setParameter("secCargo", secCargo);
            List<Competenciascargos> competenciascargos = query.getResultList();
            return competenciascargos;
        } catch (Exception e) {
            System.out.println("Error buscarCompetenciasCargosParaSecuenciaCargo PersistenciaCompetenciasCargos : " + e.toString());
            return null;
        }
    }
}
