/**
 * Documentación a cargo de AndresPineda
 */
package Persistencia;

import Entidades.Tipospagos;
import InterfacePersistencia.PersistenciaTiposPagosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Tipospagos' de la
 * base de datos.
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaTiposPagos implements PersistenciaTiposPagosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Tipospagos tipospagos) {
        try {
            em.persist(tipospagos);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaProcesos : " + e);
        }
    }

    @Override
    public void editar(Tipospagos tipospagos) {
        try {
            em.merge(tipospagos);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaProcesos : " + e);
        }
    }

    @Override
    public void borrar(Tipospagos tipospagos) {
        try {
            em.remove(em.merge(tipospagos));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaProcesos : " + e);
        }
    }

    @Override
    public List<Tipospagos> consultarTiposPagos() {
        try {
            Query query = em.createQuery("SELECT t FROM Tipospagos t ORDER BY t.codigo  ASC");
            List<Tipospagos> tipospagos = query.getResultList();
            return tipospagos;
        } catch (Exception e) {
            System.out.println("Error buscarTiposPagos ERROR" + e);
            return null;
        }
    }

    @Override
    public Tipospagos consultarTipoPago(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT t FROM Tipospagos t WHERE t.secuencia =:secuencia");
            query.setParameter("secuencia", secuencia);
            Tipospagos tipospagos = (Tipospagos) query.getSingleResult();
            return tipospagos;
        } catch (Exception e) {
            System.out.println("Error buscarTipoPagoSecuencia");
            Tipospagos tipospagos = null;
            return tipospagos;
        }
    }

    public BigInteger contarProcesosTipoPago(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = " SELECT COUNT(*)FROM procesos WHERE tipopago =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaProcesos contarProcesosTipoPago Retorno " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaProcesos contarProcesosTipoPago ERROR : " + e);
            return retorno;
        }
    }
}
