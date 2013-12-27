/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import InterfacePersistencia.PersistenciaTiposAuxiliosInterface;
import Entidades.TiposAuxilios;
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
public class PersistenciaTiposAuxilios implements PersistenciaTiposAuxiliosInterface {
 /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
    public void crear(TiposAuxilios tiposAuxilios) {
        em.persist(tiposAuxilios);
    }

    public void editar(TiposAuxilios tiposAuxilios) {
        em.merge(tiposAuxilios);
    }

    public void borrar(TiposAuxilios tiposAuxilios) {
        try {
            em.remove(em.merge(tiposAuxilios));
        } catch (Exception e) {
            System.err.println("Error borrando TiposDias");
            System.out.println(e);
        }
    }

    public TiposAuxilios buscarTipoAuxilio(BigInteger secuenciaME) {
        try {
            return em.find(TiposAuxilios.class, secuenciaME);
        } catch (Exception e) {
            return null;
        }
    }

    public List<TiposAuxilios> buscarTiposAuxilios() {
        Query query = em.createQuery("SELECT m FROM TiposAuxilios m ORDER BY m.codigo ASC");
        List<TiposAuxilios> listaMotivosEmbargos = query.getResultList();
        return listaMotivosEmbargos;
    }

    public BigInteger contadorTablasAuxilios(BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = "SELECT COUNT(*)FROM tablasauxilios ta , tiposauxilios ti WHERE ta.tipoauxilio = ti.secuencia AND ti.secuencia = ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIATIPOSAUXILIOS contadorTablasAuxilios = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIATIPOSAUXILIOS contadorTablasAuxilios  ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }
}
