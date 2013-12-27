/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaTiposEmbargosInterface;
import Entidades.TiposEmbargos;
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
public class PersistenciaTiposEmbargos implements PersistenciaTiposEmbargosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(TiposEmbargos tiposEmbargos) {
        em.persist(tiposEmbargos);
    }

    public void editar(TiposEmbargos tiposEmbargos) {
        em.merge(tiposEmbargos);
    }

    public void borrar(TiposEmbargos tiposEmbargos) {
        try {
            em.remove(em.merge(tiposEmbargos));
        } catch (Exception e) {
            System.err.println("Error borrando TiposEmbargos");
            System.out.println(e);
        }
    }

    public TiposEmbargos buscarTipoEmbargo(BigInteger secuenciaTE) {
        try {
            return em.find(TiposEmbargos.class, secuenciaTE);
        } catch (Exception e) {
            return null;
        }
    }

    public List<TiposEmbargos> buscarTiposEmbargos() {
        Query query = em.createQuery("SELECT m FROM TiposEmbargos m ORDER BY m.codigo ASC");
        List<TiposEmbargos> listaMotivosPrestamos = query.getResultList();
        return listaMotivosPrestamos;
    }

    public BigInteger contadorEerPrestamos(BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = " SELECT COUNT(*)FROM eersprestamos ee , tiposembargos te WHERE ee.tipoembargo = te.secuencia AND te.secuencia = ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIATIPOSEMBARGOS CONTADOREERPRESTAMOS = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIATIPOSEMBARGOS CONTADOREERPRESTAMOS ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }

    public BigInteger contadorFormasDtos(BigInteger secuencia) {
        BigInteger retorno;
        try {
            String sqlQuery = " SELECT COUNT(*)FROM formasdtos fdts , tiposembargos te WHERE fdts.tipoembargo = te.secuencia AND te.secuencia =  ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIATIPOSEMBARGOS CONTADORFORMASDTOS = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIATIPOSEMBARGOS CONTADORFORMASDTOS ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }
}
