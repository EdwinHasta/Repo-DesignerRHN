/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Jornadas;
import InterfacePersistencia.PersistenciaJornadasInterface;
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
public class PersistenciaJornadas implements PersistenciaJornadasInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    EntityManager em;

    public void crear(Jornadas jornadas) {
        try {
            em.persist(jornadas);
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaJornadas crear ERROR : " + e);
        }
    }

    public void editar(Jornadas jornadas) {
        try {
            em.merge(jornadas);
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaJornadas editar error " + e);
        }
    }

    public void borrar(Jornadas jornadas) {
        try {
            em.remove(em.merge(jornadas));
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaJornadas borrar ERROR +" + e);
        }
    }

    public Jornadas consultarJornada(BigInteger secuencia) {
        try {

            return em.find(Jornadas.class, secuencia);
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaJornadas buscarJornada ERROR " + e);

            return null;
        }
    }

    public List<Jornadas> consultarJornadas() {
        try {
            Query query = em.createQuery("SELECT ta FROM Jornadas ta ORDER BY ta.codigo");
            List<Jornadas> todosJornadas = query.getResultList();
            return todosJornadas;
        } catch (Exception e) {
            System.err.println("Error: PersistenciaJornadas consultarJornadas ERROR " + e);
            return null;
        }
    }

    public BigInteger contarTarifasEscalafonesJornada(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT FROM(*)tarifasescalafones WHERE jornada =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaJornadas contarTarifasEscalafonesJornada Retorno " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaJornadas contarTiposEntidadesJornada ERROR : " + e);
            return retorno;
        }
    }

    public BigInteger contarJornadasLaboralesJornada(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT FROM(*)jornadaslaborales WHERE jornada =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaJornadas contarJornadasLaboralesJornada Retorno " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaJornadas contarTSjornadasTipoEntidad ERROR : " + e);
            return retorno;
        }
    }

}
