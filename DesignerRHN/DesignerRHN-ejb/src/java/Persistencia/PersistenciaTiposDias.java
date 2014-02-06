/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.TiposDias;
import InterfacePersistencia.PersistenciaTiposDiasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'TiposDias' de la base
 * de datos.
 *
 * @author AndresPineda.
 */
@Stateless
public class PersistenciaTiposDias implements PersistenciaTiposDiasInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(TiposDias tiposDias) {
        em.persist(tiposDias);
    }

    @Override
    public void editar(TiposDias tiposDias) {
        em.merge(tiposDias);
    }

    @Override
    public void borrar(TiposDias tiposDias) {
        em.remove(em.merge(tiposDias));
    }

    @Override
    public TiposDias buscarTipoDia(BigInteger secuencia) {
        try {
            return em.find(TiposDias.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposDias buscarTipoDia : " + e.toString());
            return null;
        }
    }

    @Override
    public List<TiposDias> buscarTiposDias() {
        try {
            Query query = em.createQuery("SELECT td FROM TiposDias td ORDER BY td.codigo DESC");
            List<TiposDias> tiposDias = query.getResultList();
            return tiposDias;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposDias buscarTiposDias : " + e.toString());
            return null;
        }
    }

    @Override
    public BigInteger contadorDiasLaborales(BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = " SELECT COUNT(*)FROM diaslaborables WHERE tipodia = ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIATIPOSDIAS CONTADORDIASLABORALES = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIATIPOSDIAS CONTADORDIASLABORALES ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }

    @Override
    public BigInteger contadorExtrasRecargos(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = " SELECT COUNT(*)FROM extrasrecargos WHERE tipodia = ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIATIPOSDIAS CONTADOREXTRASRECARGOS = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIATIPOSDIAS CONTADOREXTRASRECARGOS ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }
}
