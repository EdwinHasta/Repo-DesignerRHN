/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaPaisesInterface;
import Entidades.Paises;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaPaises implements PersistenciaPaisesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Paises tiposAusentismos) {
        try {
            em.merge(tiposAusentismos);
        } catch (PersistenceException ex) {
            System.err.println("Error PersistenciaPaises.crear " + ex);
        }
    }

    @Override
    public void editar(Paises tiposAusentismos) {
        em.merge(tiposAusentismos);
    }

    @Override
    public void borrar(Paises tiposAusentismos) {
        em.remove(em.merge(tiposAusentismos));
    }

    @Override
    public List<Paises> consultarPaises() {
        try {
            Query query = em.createQuery("SELECT ta FROM Paises ta ORDER BY ta.codigo");
            List<Paises> todosPaises = query.getResultList();
            return todosPaises;
        } catch (Exception e) {
            System.err.println("Error: PersistenciaPaises consultarPaises ERROR " + e);
            return null;
        }
    }

    public Paises consultarPais(BigInteger secClaseCategoria) {
        try {
            Query query = em.createNamedQuery("SELECT cc FROM Paises cc WHERE cc.secuencia=:secuencia");
            query.setParameter("secuencia", secClaseCategoria);
            Paises clasesCategorias = (Paises) query.getSingleResult();
            return clasesCategorias;
        } catch (Exception e) {
            return null;
        }
    }

    public BigInteger contarDepartamentosPais(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM departamentos WHERE pais = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaPaises contarDepartamentosPais Retorno " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaPaises contarDepartamentosPais ERROR : " + e);
            return retorno;
        }
    }

    public BigInteger contarFestivosPais(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM festivos WHERE pais = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaPaises contarFestivosPais Retorno " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaPaises contarFestivosPais ERROR : " + e);
            return retorno;
        }
    }

}
