/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.ClasesPensiones;
import InterfacePersistencia.PersistenciaClasesPensionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'ClasesPensiones' de
 * la base de datos
 *
 * @author Andrés Pineda
 */
@Stateless
public class PersistenciaClasesPensiones implements PersistenciaClasesPensionesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(ClasesPensiones clasesPensiones) {
        try {
            em.persist(clasesPensiones);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaClasesPensiones");
        }
    }

    @Override
    public void editar(ClasesPensiones clasesPensiones) {
        try {
            em.merge(clasesPensiones);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaClasesPensiones");
        }
    }

    @Override
    public void borrar(ClasesPensiones clasesPensiones) {
        try {
            em.remove(em.merge(clasesPensiones));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaClasesPensiones");
        }
    }

    @Override
    public List<ClasesPensiones> consultarClasesPensiones() {
        try {
            List<ClasesPensiones> clasesPensionesLista = (List<ClasesPensiones>) em.createNamedQuery("ClasesPensiones.findAll").getResultList();
            return clasesPensionesLista;
        } catch (Exception e) {
            System.out.println("Error buscarClasesPensiones PersistenciaClasesPensiones");
            return null;
        }
    }

    @Override
    public ClasesPensiones consultarClasePension(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT cp FROM ClasesPensiones cp WHERE cp.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            ClasesPensiones claseP = (ClasesPensiones) query.getSingleResult();
            return claseP;
        } catch (Exception e) {
            System.out.println("Error buscarClasePennsion PersistenciaClasesPensiones");
            ClasesPensiones claseP = null;
            return claseP;
        }
    }

    @Override
    public BigInteger contarRetiradosClasePension(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM pensionados WHERE clase=?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.err.println("Contador PersistenciaMotivosRetiros  contarRetiradosClasePension  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PersistenciaMotivosRetiros   contarRetiradosClasePension. " + e);
            return retorno;
        }
    }
}
