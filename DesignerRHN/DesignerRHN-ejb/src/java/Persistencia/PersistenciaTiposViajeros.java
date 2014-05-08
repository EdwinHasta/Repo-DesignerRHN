/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Tiposviajeros;
import InterfacePersistencia.PersistenciaTiposViajerosInterface;
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
public class PersistenciaTiposViajeros implements PersistenciaTiposViajerosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
*/

    @Override
    public void crear(EntityManager em, Tiposviajeros subCategorias) {
        try {
            em.persist(subCategorias);
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada (SubCategoria)");
        }
    }

    @Override
    public void editar(EntityManager em, Tiposviajeros subCategorias) {
        try {
            em.merge(subCategorias);
        } catch (Exception e) {
            System.out.println("No se pudo modificar la SubCategoria");
        }
    }

    @Override
    public void borrar(EntityManager em, Tiposviajeros subCategorias) {
        try {
            em.remove(em.merge(subCategorias));
        } catch (Exception e) {
            System.out.println("No se pudo borrar la SubCategoria");
        }
    }

    @Override
    public List<Tiposviajeros> consultarTiposViajeros(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT l FROM Tiposviajeros  l ORDER BY l.codigo ASC ");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Tiposviajeros> listTiposViajeros = query.getResultList();
            return listTiposViajeros;
        } catch (Exception e) {
            System.err.println("ERROR PersistenciaTiposViajeros ConsultarTiposViajeros ERROR :" + e);
            return null;
        }

    }

    @Override
    public Tiposviajeros consultarSubCategoria(EntityManager em, BigInteger secSubCategoria) {
        try {
            Query query = em.createQuery("SELECT sc FROM Tiposviajeros sc WHERE sc.secuencia=:secuencia");
            query.setParameter("secuencia", secSubCategoria);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Tiposviajeros subCategorias = (Tiposviajeros) query.getSingleResult();
            return subCategorias;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public BigInteger contarVigenciasViajerosTipoViajero(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM vigenciasviajeros WHERE tipoviajero = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaTiposViajeros contarVigenciasViajeros persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaTiposViajeros contarVigenciasViajeros. " + e);
            return retorno;
        }
    }

    @Override
    public BigInteger contarTiposLegalizacionesTipoViajero(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM tiposlegalizaciones WHERE tipoviajero = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaTiposViajeros contarTiposLegalizaciones persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaTiposViajeros contarTiposLegalizaciones. " + e);
            return retorno;
        }
    }
}
