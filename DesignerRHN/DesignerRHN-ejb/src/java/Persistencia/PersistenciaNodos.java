/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Nodos;
import InterfacePersistencia.PersistenciaNodosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author PROYECTO01
 */
@Stateless
public class PersistenciaNodos implements PersistenciaNodosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Nodos nodos) {
        try {
            em.merge(nodos);
        } catch (PersistenceException ex) {
            System.out.println("Error PersistenciaNovedades.crear");
        }
    }

    @Override
    public void editar(Nodos nodos) {
        em.merge(nodos);
    }

    @Override
    public void borrar(Nodos nodos) {
        em.remove(em.merge(nodos));
    }

    public Nodos buscarNodoSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT n FROM Nodos n WHERE n.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            Nodos nodos = (Nodos) query.getSingleResult();
            return nodos;
        } catch (Exception e) {
            System.out.println("Error buscarNodoSecuencia PersistenciaNodos : " + e.toString());
            Nodos nodos = null;
            return nodos;
        }
    }

    @Override
    public List<Nodos> listNodos() {
        try {
            Query query = em.createQuery("SELECT n FROM Nodos n");
            List<Nodos> nodos = (List<Nodos>) query.getResultList();
            return nodos;
        } catch (Exception e) {
            System.out.println("Error listNodos PersistenciaNodos : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Nodos> buscarNodosPorSecuenciaHistoriaFormula(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT n FROM Nodos n WHERE n.historiaformula.secuencia=:secuenciaHF ORDER BY n.posicion ASC");
            query.setParameter("secuenciaHF", secuencia);
            List<Nodos> nodos = query.getResultList();
            return nodos;
        } catch (Exception e) {
            System.out.println("Error buscarNodosPorSecuenciaHistoriaFormula PersistenciaNodos : " + e.toString());
            List<Nodos> nodos = null;
            return nodos;
        }
    }
}
