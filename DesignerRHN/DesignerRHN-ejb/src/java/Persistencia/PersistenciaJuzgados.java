/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaJuzgadosInterface;
import Entidades.Juzgados;
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
public class PersistenciaJuzgados implements PersistenciaJuzgadosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(Juzgados juzgados) {
        em.persist(juzgados);
    }

    public void editar(Juzgados juzgados) {
        em.merge(juzgados);
    }

    public void borrar(Juzgados juzgados) {
        try {
            em.remove(em.merge(juzgados));
        } catch (Exception e) {
            System.err.println("PERSISTENCIA JUZGADOS ERROR : ");
            System.out.println(e);
        }
    }

    public Juzgados buscarJuzgado(BigInteger secuenciaJ) {
        try {
            return em.find(Juzgados.class, secuenciaJ);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Juzgados> buscarJuzgados() {
        Query query = em.createQuery("SELECT m FROM Juzgados m ORDER BY m.codigo ASC");
        List<Juzgados> listaMotivosPrestamos = query.getResultList();
        return listaMotivosPrestamos;
    }

    public List<Juzgados> buscarJuzgadosPorCiudad(BigInteger secCiudad) {
        try {
            Query query = em.createQuery("SELECT cce FROM Juzgados cce WHERE cce.ciudad.secuencia = :secuenciaJuzgado ORDER BY cce.codigo ASC");
            query.setParameter("secuenciaJuzgado", secCiudad);
            List<Juzgados> listaJuzgadosPorCiudad = query.getResultList();
            return listaJuzgadosPorCiudad;
        } catch (Exception e) {
            System.out.println("Error en Persistencia PersistenciaCentrosCostos BuscarCentrosCostosEmpr " + e);
            return null;
        }
    }

    public BigInteger contadorEerPrestamos(BigInteger secuencia) {
        BigInteger retorno;
        try {
            System.out.println("Persistencia secuencia borrado " + secuencia);
            String sqlQuery = " SELECT COUNT(*)FROM eersprestamos ers , juzgados juz WHERE ers.juzgado = juz.secuencia AND juz.secuencia = ? ";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("PERSISTENCIAJUZGADOS CONTADOREERPRESTAMOS = " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAJUZGADOS CONTADOREERPRESTAMOS ERROR = " + e);
            retorno = new BigInteger("-1");
            return retorno;
        }
    }

}
