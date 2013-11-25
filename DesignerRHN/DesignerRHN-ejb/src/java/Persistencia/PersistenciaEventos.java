/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Eventos; 
import InterfacePersistencia.PersistenciaEventosInterface;
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
public class PersistenciaEventos implements PersistenciaEventosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Eventos eventos) {
        em.persist(eventos);
    }

    @Override
    public void editar(Eventos eventos) {
        em.merge(eventos);
    }

    @Override
    public void borrar(Eventos eventos) {
        em.remove(em.merge(eventos));
    }

    @Override
    public Eventos buscarEvento(BigInteger secuencia) {
        try {
            return em.find(Eventos.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error en la PersistenciaEventos : " + e);
            return null;
        }
    }

    @Override
    public List<Eventos> buscarEventos() {
        try {
            Query query = em.createQuery("SELECT e FROM Eventos e ORDER BY e.codigo DESC");
            List<Eventos> eventos = query.getResultList();
            return eventos;
        } catch (Exception e) {
            System.out.println("Error en PersistenciaEventos Por buscarEventos ERROR" + e);
            return null;
        }
    }

}
