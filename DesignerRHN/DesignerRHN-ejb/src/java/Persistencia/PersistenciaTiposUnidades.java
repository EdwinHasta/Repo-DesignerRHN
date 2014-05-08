/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import Entidades.TiposUnidades;
import InterfacePersistencia.PersistenciaTiposUnidadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaTiposUnidades implements PersistenciaTiposUnidadesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
*/

    @Override
    public void crear(EntityManager em, TiposUnidades tiposUnidades) {
        try {
            em.persist(tiposUnidades);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaTiposUnidades : " + e.toString());
        }
    }

    @Override
    public void editar(EntityManager em, TiposUnidades tiposUnidades) {
        try {
            em.merge(tiposUnidades);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaTiposUnidades : " + e.toString());
        }
    }

    @Override
    public void borrar(EntityManager em, TiposUnidades tiposUnidades) {
        try {
            em.remove(em.merge(tiposUnidades));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaTiposUnidades : " + e.toString());
        }
    }

    @Override
    public List<TiposUnidades> consultarTiposUnidades(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT g FROM TiposUnidades g ORDER BY g.codigo ASC ");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List< TiposUnidades> listMotivosDemandas = query.getResultList();
            return listMotivosDemandas;

        } catch (Exception e) {
            System.out.println("Error consultarTiposUnidades PersistenciaTiposUnidades : " + e.toString());
            return null;
        }
    }

    @Override
    public TiposUnidades consultarTipoUnidad(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT te FROM TiposUnidades te WHERE te.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            TiposUnidades tiposUnidades = (TiposUnidades) query.getSingleResult();
            return tiposUnidades;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposUnidades consultarTipoUnidad : " + e.toString());
            TiposUnidades tiposEntidades = null;
            return tiposEntidades;
        }
    }

    @Override
    public BigInteger contarUnidadesTipoUnidad(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM unidades WHERE tipounidad = ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.err.println("Contador TiposUnidades contadorVigenciasIndicadores persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error TiposUnidades contadorVigenciasIndicadores. " + e);
            return retorno;
        }
    }
}
