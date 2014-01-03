/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.GruposConceptos;
import InterfacePersistencia.PersistenciaGruposConceptosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'GruposConceptos'
 * de la base de datos.
 * @author AndresPineda
 */
@Stateless
public class PersistenciaGruposConceptos implements PersistenciaGruposConceptosInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(GruposConceptos gruposConceptos) {
        try {
            em.persist(gruposConceptos);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaGruposConceptos " + e.toString());
        }
    }

    @Override
    public void editar(GruposConceptos gruposConceptos) {
        try {
            em.merge(gruposConceptos);
        } catch (Exception e) {
            System.out.println("error editar PersistenciaGruposConceptos " + e.toString());
        }
    }

    @Override
    public void borrar(GruposConceptos gruposConceptos) {
        try {
            em.remove(em.merge(gruposConceptos));
        } catch (Exception e) {
            System.out.println("Error borrar gruposConceptos PersistenciaGruposConceptos");
        }
    }
   
    @Override
    public List<GruposConceptos> buscarGruposConceptos() {
        try {
            List<GruposConceptos> gruposConceptos = (List<GruposConceptos>) em.createNamedQuery("GruposConceptos.findAll").getResultList();
            return gruposConceptos;
        } catch (Exception e) {
            System.out.println("error buscarGruposConceptos PersistenciaGruposConceptos");
            return null;
        }
    }

    @Override
    public GruposConceptos buscarGruposConceptosSecuencia(BigInteger secuencia) {
        GruposConceptos gruposConceptos;
        try {
            Query query = em.createQuery("SELECT e FROM GruposConceptos e WHERE e.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            gruposConceptos = (GruposConceptos) query.getSingleResult();
            return gruposConceptos;
        } catch (Exception e) {
            gruposConceptos = null;
            System.out.println("Error buscarGruposConceptosSecuencia PersistenciaGruposConceptos");
            return gruposConceptos;
        }
    }
}
