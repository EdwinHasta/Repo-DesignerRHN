/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.TiposIndicadores;
import InterfacePersistencia.PersistenciaTiposIndicadoresInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'TiposIndicadores' de
 * la base de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaTiposIndicadores implements PersistenciaTiposIndicadoresInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(TiposIndicadores tiposIndicadores) {
        try {
            em.persist(tiposIndicadores);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaTiposIndicadores : " + e.toString());
        }
    }

    @Override
    public void editar(TiposIndicadores tiposIndicadores) {
        try {
            em.merge(tiposIndicadores);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaTiposIndicadores : " + e.toString());
        }
    }

    @Override
    public void borrar(TiposIndicadores tiposIndicadores) {
        try {
            em.remove(em.merge(tiposIndicadores));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaTiposIndicadores : " + e.toString());
        }
    }

    @Override
    public List<TiposIndicadores> buscarTiposIndicadores() {
        try {
            Query query = em.createQuery("SELECT g FROM TiposIndicadores g ORDER BY g.codigo ASC ");
            List< TiposIndicadores> listMotivosDemandas = query.getResultList();
            return listMotivosDemandas;

        } catch (Exception e) {
            System.out.println("Error buscarTiposIndicadores PersistenciaTiposIndicadores : " + e.toString());
            return null;
        }
    }

    @Override
    public TiposIndicadores buscarTiposIndicadoresSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT te FROM TiposIndicadores te WHERE te.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            TiposIndicadores tiposIndicadores = (TiposIndicadores) query.getSingleResult();
            return tiposIndicadores;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposIndicadores buscarTiposIndicadoresSecuencia : " + e.toString());
            TiposIndicadores tiposEntidades = null;
            return tiposEntidades;
        }
    }

    @Override
    public BigInteger contadorVigenciasIndicadores(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*) FROM vigenciasindicadores WHERE tipoindicador= ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.err.println("Contador TiposIndicadores contadorVigenciasIndicadores persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error TiposIndicadores contadorVigenciasIndicadores. " + e);
            return retorno;
        }
    }
}
