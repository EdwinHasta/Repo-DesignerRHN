/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.TiposPensionados;
import InterfacePersistencia.PersistenciaTiposPensionadosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'TiposPensionados' de
 * la base de datos.
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaTiposPensionados implements PersistenciaTiposPensionadosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
*/

    @Override
    public void crear(EntityManager em, TiposPensionados tiposPensionados) {
        try {
            em.persist(tiposPensionados);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaTiposPensionados");
        }
    }

    @Override
    public void editar(EntityManager em, TiposPensionados tiposPensionados) {
        try {
            em.merge(tiposPensionados);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaTiposPensionados");
        }
    }

    @Override
    public void borrar(EntityManager em, TiposPensionados tiposPensionados) {
        try {
            em.remove(em.merge(tiposPensionados));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaTiposPensionados");
        }
    }

    @Override
    public List<TiposPensionados> consultarTiposPensionados(EntityManager em) {
        try {
            //List<TiposPensionados> tiposPensionadosLista = (List<TiposPensionados>) em.createNamedQuery("TiposPensionados.findAll").getResultList();
            //return tiposPensionadosLista;
            Query query = em.createQuery("SELECT t FROM TiposPensionados t");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<TiposPensionados> listaTiposPensionados = query.getResultList();
            return listaTiposPensionados;
        } catch (Exception e) {
            System.out.println("Error buscarTiposPensionados PersistenciaTiposPensionados");
            return null;
        }
    }

    @Override
    public TiposPensionados consultarTipoPensionado(EntityManager em, BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT tp FROM TiposPensionados tp WHERE tp.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            TiposPensionados tipoP = (TiposPensionados) query.getSingleResult();
            return tipoP;
        } catch (Exception e) {
            System.out.println("Error buscarTipoPensionSecuencia PersistenciaTiposPensionados");
            TiposPensionados tipoP = null;
            return tipoP;
        }
    }

    @Override
    public BigInteger contarPensionadosTipoPension(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM pensionados WHERE tipopensionado=?";
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
