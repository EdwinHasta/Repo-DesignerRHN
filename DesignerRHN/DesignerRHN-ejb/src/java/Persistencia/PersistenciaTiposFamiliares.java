/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaTiposFamiliaresInterface;
import Entidades.TiposFamiliares;
import java.math.BigDecimal;
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
public class PersistenciaTiposFamiliares implements PersistenciaTiposFamiliaresInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(TiposFamiliares tiposFamiliares) {
        em.persist(tiposFamiliares);
    }

    public void editar(TiposFamiliares tiposFamiliares) {
        em.merge(tiposFamiliares);
    }

    public void borrar(TiposFamiliares tiposFamiliares) {
        em.remove(em.merge(tiposFamiliares));
    }

    public TiposFamiliares buscarTiposFamiliares(BigInteger secuenciaTF) {
        try {
            return em.find(TiposFamiliares.class, secuenciaTF);
        } catch (Exception e) {
            return null;
        }
    }

    public List<TiposFamiliares> buscarTiposFamiliares() {
        Query query = em.createQuery("SELECT te FROM TiposFamiliares te ORDER BY te.codigo ASC ");
        List<TiposFamiliares> listMotivosDemandas = query.getResultList();
        return listMotivosDemandas;

    }

    public BigInteger contadorHvReferencias(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM  hvreferencias hvr WHERE parentesco =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.err.println("Contador PERSISTENCIATIPOSFAMILIARES contadorHvReferencias  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIATIPOSFAMILIARES  contadorHvReferencias. " + e);
            return retorno;
        }
    }

}
