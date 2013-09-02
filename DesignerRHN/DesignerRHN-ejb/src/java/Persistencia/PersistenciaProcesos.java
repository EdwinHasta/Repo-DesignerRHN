/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Procesos;
import InterfacePersistencia.PersistenciaProcesosInterface;
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

public class PersistenciaProcesos implements PersistenciaProcesosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Procesos procesos) {
        try {
            em.persist(procesos);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaProcesos");
        }
    }

    @Override
    public void editar(Procesos procesos) {
        try {
            em.merge(procesos);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaProcesos");
        }
    }

    @Override
    public void borrar(Procesos procesos) {
        try {
            em.remove(em.merge(procesos));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaProcesos");
        }
    }

    @Override
    public Procesos buscarProceso(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            return em.find(Procesos.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarProceso PersistenciaProcesos");
            return null;
        }

    }

    @Override
    public List<Procesos> buscarProcesos() {
        try {
            List<Procesos> procesos = (List<Procesos>) em.createNamedQuery("Procesos.findAll").getResultList();
            return procesos;
        } catch (Exception e) {
            System.out.println("Error buscarProcesos");
            return null;
        }
    }

    @Override
    public Procesos buscarProcesosSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT t FROM Procesos t WHERE t.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            Procesos procesos = (Procesos) query.getSingleResult();
            return procesos;
        } catch (Exception e) {
            System.out.println("Error buscarProcesosSecuencia");
            Procesos procesos = null;
            return procesos;
        }
    }

}
