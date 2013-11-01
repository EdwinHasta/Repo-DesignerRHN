/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.TiposCentrosCostos;
import InterfacePersistencia.PersistenciaTiposCentrosCostosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author user
 */
@Stateless
@LocalBean
public class PersistenciaTiposCentrosCostos implements PersistenciaTiposCentrosCostosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    EntityManager em;

    @Override
    public void crear(TiposCentrosCostos TiposCentrosCostos) {
        try {
            em.persist(TiposCentrosCostos);
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaTiposCentrosCostos crear ERROR " + e);
        }
    }

    @Override
    public void editar(TiposCentrosCostos TiposCentrosCostos) {
        try {
            em.merge(TiposCentrosCostos);
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaTiposCentrosCostos editar ERROR " + e);
        }
    }

    @Override
    public void borrar(TiposCentrosCostos TiposCentrosCostos) {
        try {
            em.remove(em.merge(TiposCentrosCostos));
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaTiposCentrosCostos borrar ERROR " + e);
        }
    }

    @Override
    public TiposCentrosCostos buscarTipoCentrosCostos(BigInteger secuenciaTiposCentrosCostos) {
        try {
            return em.find(TiposCentrosCostos.class, secuenciaTiposCentrosCostos);
        } catch (Exception e) {
            System.err.println("ERROR PersistenciaTiposCentosCostos buscarTiposCentrosCostos ERROR " + e);
            return null;
        }
    }

    @Override
    public List<TiposCentrosCostos> buscarTiposCentrosCostos() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TiposCentrosCostos.class));
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaTiposCentrosCostos buscarTiposCentrosCostos ERROR" + e);
            return null;
        }
    }
    
     @Override
    public Long verificarBorradoCentrosCostos(BigInteger secuencia) {
        Long retorno = new Long(-1);
        try {
            Query query = em.createQuery("SELECT count(cc) FROM CentrosCostos cc WHERE cc.tipocentrocosto.secuencia = :secTipoCentroCosto ");
            query.setParameter("secTipoCentroCosto", secuencia);
            retorno = (Long) query.getSingleResult();
            System.err.println("PersistenciaTiposCentrosCostos retorno ==" + retorno.intValue());

        } catch (Exception e) {
            System.err.println("ERROR EN PersistenciaTiposCentrosCostos verificarBorrado ERROR :" + e);
        } finally {
            return retorno;
        }
    }
     
   @Override
    public Long verificarBorradoVigenciasCuentas(BigInteger secuencia) {
        Long retorno = new Long(-1);
        try {
            Query query = em.createQuery("SELECT count(vc) FROM VigenciasCuentas vc WHERE vc.tipocc.secuencia  = :secTipoCentroCosto ");
            query.setParameter("secTipoCentroCosto", secuencia);
            retorno = (Long) query.getSingleResult();
            System.err.println("PersistenciaTiposCentrosCostos retorno ==" + retorno.intValue());

        } catch (Exception e) {
            System.err.println("ERROR EN PersistenciaTiposCentrosCostos verificarBorrado ERROR :" + e);
        } finally {
            return retorno;
        }
    }  
   
   @Override
    public Long verificarBorradoRiesgosProfesionales(BigInteger secuencia) {
        Long retorno = new Long(-1);
        try {
            Query query = em.createQuery("SELECT count(rp) FROM RiesgosProfesionales rp WHERE rp.tipocentrocosto.secuencia =:secTipoCentroCosto ");
            query.setParameter("secTipoCentroCosto", secuencia);
            retorno = (Long) query.getSingleResult();
            System.err.println("PersistenciaTiposCentrosCostos retorno ==" + retorno.intValue());

        } catch (Exception e) {
            System.err.println("ERROR EN PersistenciaTiposCentrosCostos verificarBorrado ERROR :" + e);
        } finally {
            return retorno;
        }
    }
}