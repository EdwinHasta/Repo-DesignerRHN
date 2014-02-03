/**
 * Documentación a cargo de Andres Pineda
 */
package Persistencia;

import Entidades.DiasLaborables;
import InterfacePersistencia.PersistenciaDiasLaborablesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'DiasLaborables' de la
 * base de datos
 *
 * @author Andrés Pineda
 */
@Stateless
public class PersistenciaDiasLaborables implements PersistenciaDiasLaborablesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(DiasLaborables diasLaborables) {
        try {
            em.persist(diasLaborables);
        } catch (Exception e) {
            System.out.println("Error crear DiasLaborables (PersistenciaDiasLaborables) : " + e.toString());
        }
    }

    @Override
    public void editar(DiasLaborables diasLaborables) {
        try {
            em.merge(diasLaborables);
        } catch (Exception e) {
            System.out.println("Error editar DiasLaborables (PersistenciaDiasLaborables) : " + e.toString());
        }
    }

    @Override
    public void borrar(DiasLaborables diasLaborables) {
        try {
            em.remove(em.merge(diasLaborables));
        } catch (Exception e) {
            System.out.println("Error borrar DiasLaborables (PersistenciaDiasLaborables) : " + e.toString());
        }
    }

    @Override
    public DiasLaborables buscarDiaLaborableSecuencia(BigInteger secDiaLaboral) {
        try {
            Query query = em.createQuery("SELECT dl FROM DiasLaborables dl WHERE dl.secuencia =:secuencia");
            query.setParameter("secuencia", secDiaLaboral);
            DiasLaborables diasLaborables = (DiasLaborables) query.getSingleResult();
            return diasLaborables;
        } catch (Exception e) {
            DiasLaborables diasLaborables = null;
            return diasLaborables;
        }
    }

    @Override
    public List<DiasLaborables> diasLaborables() {
        try {
            Query query = em.createQuery("SELECT dl FROM DiasLaborables dl");
            List<DiasLaborables> diasLaborables = query.getResultList();
            return diasLaborables;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<DiasLaborables> diasLaborablesParaSecuenciaTipoContrato(BigInteger secTipoContrato) {
        try {
            Query query = em.createQuery("SELECT dl FROM DiasLaborables dl WHERE dl.tipocontrato.secuencia=:secuencia");
            query.setParameter("secuencia", secTipoContrato);
            List<DiasLaborables> diasLaborables = query.getResultList();
            return diasLaborables;
        } catch (Exception e) {
            return null;
        }
    }

}
