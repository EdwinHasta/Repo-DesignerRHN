/**
 * Documentación a cargo de Andres Pineda
 */
package Persistencia;

import Entidades.OperandosGruposConceptos;
import InterfacePersistencia.PersistenciaOperandosGruposConceptosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla
 * 'OperandosGruposConceptos' de la base de datos.
 *
 * @author Andres Pineda.
 */
@Stateless
public class PersistenciaOperandosGruposConceptos implements PersistenciaOperandosGruposConceptosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(OperandosGruposConceptos gruposConceptos) {
        try {
            em.persist(gruposConceptos);
        } catch (Exception e) {
            System.out.println("El OperandosGruposConceptos no exite o esta reservada por lo cual no puede ser modificada (PersistenciaOperandosGruposConceptos): " + e.toString());
        }
    }

    @Override
    public void editar(OperandosGruposConceptos gruposConceptos) {
        try {
            em.merge(gruposConceptos);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el OperandosGruposConceptos (PersistenciaOperandosGruposConceptos) : " + e.toString());
        }
    }

    @Override
    public void borrar(OperandosGruposConceptos gruposConceptos) {
        try {
            em.remove(em.merge(gruposConceptos));
        } catch (Exception e) {
            System.out.println("El OperandosGruposConceptos no se ha podido eliminar (PersistenciaOperandosGruposConceptos) : " + e.toString());
        }
    }

    @Override
    public List<OperandosGruposConceptos> buscarOperandosGruposConceptos() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OperandosGruposConceptos.class));
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.out.println("Error buscarOperandosGruposConceptos PersistenciaOperandosGruposConceptos : " + e.toString());
            return null;
        }
    }

    @Override
    public OperandosGruposConceptos buscarOperandosGruposConceptosPorSecuencia(BigInteger secOperando) {
        try {
            Query query = em.createQuery("SELECT o FROM OperandosGruposConceptos o WHERE o.secuencia=:secOperando");
            query.setParameter("secOperando", secOperando);
            OperandosGruposConceptos gruposConceptos = (OperandosGruposConceptos) query.getSingleResult();
            return gruposConceptos;
        } catch (Exception e) {
            System.out.println("Error buscarOperandosGruposConceptosPorSecuencia (PersistenciaOperandosGruposConceptos) : " + e.toString());
            return null;
        }
    }

    @Override
    public List<OperandosGruposConceptos> buscarOperandosGruposConceptosPorProcesoSecuencia(BigInteger secProceso) {
        try {
            Query query = em.createQuery("SELECT o FROM OperandosGruposConceptos o WHERE o.proceso.secuencia=:secuencia");
            query.setParameter("secuencia", secProceso);
            List<OperandosGruposConceptos> gruposConceptos = query.getResultList();
            return gruposConceptos;
        } catch (Exception e) {
            System.out.println("Error buscarOperandosGruposConceptosPorProcesoSecuencia (PersistenciaOperandosGruposConceptos) : " + e.toString());
            return null;
        }
    }

}
