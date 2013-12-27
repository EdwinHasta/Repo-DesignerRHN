/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import InterfacePersistencia.PersistenciaTiposChequeosInterface;
import Entidades.TiposChequeos;
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
public class PersistenciaTiposChequeos implements PersistenciaTiposChequeosInterface {

     /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    
     public void crear(TiposChequeos tiposChequeos) {
        em.persist(tiposChequeos);
    }

    public void editar(TiposChequeos tiposChequeos) {
        em.merge(tiposChequeos);
    }

    public void borrar(TiposChequeos tiposChequeos) {
        em.remove(em.merge(tiposChequeos));
    }

    public TiposChequeos buscarTipoChequeo(BigInteger secuenciaTC) {
        try {
            return em.find(TiposChequeos.class, secuenciaTC);
        } catch (Exception e) {
            return null;
        }
    }

    public List<TiposChequeos> buscarTiposChequeos() {
        try {
            Query query = em.createQuery("SELECT tc FROM TiposChequeos tc ORDER BY tc.codigo ASC ");
            List<TiposChequeos> listMotivosDemandas = query.getResultList();
            return listMotivosDemandas;
        } catch (Exception e) {
            System.err.println("ERROR BUSCAR TIPOS CHEQUEOS  " + e);
            return null;
        }

    }

     public BigInteger contadorChequeosMedicos(BigInteger secuencia) {
        BigInteger retorno;
        try {
            System.out.println("Persistencia secuencia borrado " + secuencia );
            String sqlQuery = " SELECT COUNT(*)FROM tiposchequeos tc , chequeosmedicos cm WHERE cm.tipochequeo = cm.secuencia AND tc.secuencia= ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIAJUZGADOS CONTADORCHEQUEOSMEDICOS = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIATIPOSCHEQUEOS CONTADORCHEQUETOSMEDICOS ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }
     public BigInteger contadorTiposExamenesCargos(BigInteger secuencia) {
        BigInteger retorno;
        try {
            System.out.println("Persistencia secuencia borrado " + secuencia );
            String sqlQuery = " SELECT COUNT(*)FROM tiposchequeos tc , tiposexamenescargos cm WHERE cm.tipochequeo = cm.secuencia AND tc.secuencia= ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIAJUZGADOS CONTADORCHEQUEOSMEDICOS = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIATIPOSCHEQUEOS CONTADORCHEQUETOSMEDICOS ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }
}
