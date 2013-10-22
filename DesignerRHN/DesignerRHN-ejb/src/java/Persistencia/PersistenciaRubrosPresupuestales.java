/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Rubrospresupuestales;
import InterfacePersistencia.PersistenciaRubrosPresupuestalesInterface;
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
public class PersistenciaRubrosPresupuestales implements PersistenciaRubrosPresupuestalesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     */
    @Override
    public void crear(Rubrospresupuestales rubrospresupuestales) {
        try {
            em.persist(rubrospresupuestales);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaCuentas : " + e.toString());
        }
    }

    /*
     */
    @Override
    public void editar(Rubrospresupuestales rubrospresupuestales) {
        try {
            em.merge(rubrospresupuestales);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaCuentas : " + e.toString());
        }
    }

    /*
     */
    @Override
    public void borrar(Rubrospresupuestales rubrospresupuestales) {
        try {
            em.remove(em.merge(rubrospresupuestales));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaCuentas : " + e.toString());
        }
    }

    /*
     */
    @Override
    public Rubrospresupuestales buscarRubro(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            return em.find(Rubrospresupuestales.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarCuenta PersistenciaCuentas : " + e.toString());
            return null;
        }

    }

    /*
     */
    @Override
    public List<Rubrospresupuestales> buscarRubros() {
        try {
            List<Rubrospresupuestales> rubrospresupuestales = (List<Rubrospresupuestales>) em.createNamedQuery("Rubrospresupuestales.findAll").getResultList();
            return rubrospresupuestales;
        } catch (Exception e) {
            System.out.println("Error buscarCuentas PersistenciaCuentas : " + e.toString());
            return null;
        }
    }

    @Override
    public Rubrospresupuestales buscarRubrosSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT c FROM Rubrospresupuestales c WHERE c.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            Rubrospresupuestales cuentas = (Rubrospresupuestales) query.getSingleResult();
            return cuentas;
        } catch (Exception e) {
            System.out.println("Error buscarCuentasSecuencia PersistenciaCuentas : " + e.toString());
            Rubrospresupuestales cuentas = null;
            return cuentas;
        }
    }
}
