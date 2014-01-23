/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Papeles;
import InterfacePersistencia.PersistenciaPapelesInterface;
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
public class PersistenciaPapeles implements PersistenciaPapelesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(Papeles papel) {
        try {
            em.persist(papel);
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAPAPELES AL CREAR ERROR : " + e);
        }
    }

    public void editar(Papeles papel) {
        try {
            em.merge(papel);
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAPAPELES AL EDITAR ERROR : " + e);
        }
    }

    public void borrar(Papeles papel) {
        try {
            em.remove(em.merge(papel));
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAPAPELES AL BORRAR ERROR : " + e);
        }
    }

    public List<Papeles> consultarPapeles() {
        try {
            List<Papeles> listaPapeles = (List<Papeles>) em.createNamedQuery("Papeles.findAll").getResultList();
            return listaPapeles;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAPAPELES AL CONSULTARPAPELES ERROR : " + e);
            return null;
        }
    }

    public Papeles consultarPapel(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT papelillo FROM Papeles papelillo WHERE papelillo.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            Papeles papel = (Papeles) query.getSingleResult();
            return papel;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAPAPELES AL CONSULTARPAPEL");
            Papeles papel = null;
            return papel;
        }
    }

    public List<Papeles> consultarPapelesEmpresa(BigInteger secEmpresa) {
        try {
            Query query = em.createQuery("SELECT papelillo FROM Papeles papelillo WHERE papelillo.empresa.secuencia = :secuenciaEmpr ORDER BY papelillo.codigo ASC");
            query.setParameter("secuenciaEmpr", secEmpresa);
            List<Papeles> centrosCostos = query.getResultList();
            return centrosCostos;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAPAPELES AL CONSULTARPAPELESEMPRESA ERROR " + e);
            return null;
        }
    }

    public BigInteger contarVigenciasCargosPapel(BigInteger secuencia) {
        try {
            String sqlQuery = "SELECT COUNT(*)FROM vigenciascargos WHERE papel = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            System.out.println("PERSISTENCIAPAPELES CONTARVIGENCIASCARGOSPAPEL CONTARDOR" + query.getSingleResult());
            return new BigInteger(query.getSingleResult().toString());

        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIAPAPELES CONTARVIGENCIASCARGOSPAPEL ERROR : " + e);
            return null;
        }
    }

}
