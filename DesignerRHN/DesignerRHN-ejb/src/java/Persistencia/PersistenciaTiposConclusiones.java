/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.TiposConclusiones;
import InterfacePersistencia.PersistenciaTiposConclusionesInterface;
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
public class PersistenciaTiposConclusiones implements PersistenciaTiposConclusionesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
*/

    public void crear(EntityManager em, TiposConclusiones tiposConclusiones) {
        try {
            em.persist(tiposConclusiones);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaTiposConclusiones : " + e);
        }
    }

    public void editar(EntityManager em, TiposConclusiones tiposConclusiones) {
        try {
            em.merge(tiposConclusiones);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaTiposConclusiones : " + e);
        }
    }

    public void borrar(EntityManager em, TiposConclusiones tiposConclusiones) {
        try {
            em.remove(em.merge(tiposConclusiones));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaTiposConclusiones : " + e);
        }
    }

    public List<TiposConclusiones> consultarTiposConclusiones(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT t FROM TiposConclusiones t ORDER BY t.codigo  ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<TiposConclusiones> tiposConclusiones = query.getResultList();
            return tiposConclusiones;
        } catch (Exception e) {
            System.out.println("Error buscarTiposConclusiones ERROR" + e);
            return null;
        }
    }

    public TiposConclusiones consultarTipoConclusion(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT t FROM TiposConclusiones t WHERE t.secuencia =:secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            TiposConclusiones tiposConclusiones = (TiposConclusiones) query.getSingleResult();
            return tiposConclusiones;
        } catch (Exception e) {
            System.out.println("Error buscarTipoConclusionSecuencia");
            TiposConclusiones tiposConclusiones = null;
            return tiposConclusiones;
        }
    }

    public BigInteger contarChequeosMedicosTipoConclusion(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM chequeosmedicos WHERE tipochequeo = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaTiposConclusiones contarChequeosMedicosTipoConclusion Retorno " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaTiposConclusiones contarChequeosMedicosTipoConclusion ERROR : " + e);
            return retorno;
        }
    }
}
