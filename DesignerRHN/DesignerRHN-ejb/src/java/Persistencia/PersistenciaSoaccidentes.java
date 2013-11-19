/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Soaccidentes;
import InterfacePersistencia.PersistenciaSoaccidentesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author Viktor
 */
@Stateless
public class PersistenciaSoaccidentes implements PersistenciaSoaccidentesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Soaccidentes soaccidentes) {
        try {
//            System.out.println("Persona: " + vigenciasFormales.getPersona().getNombreCompleto());
            em.merge(soaccidentes);
        } catch (PersistenceException ex) {
            System.out.println("Error PersistenciaSoaccidentes.crear");
        }
    }

    // Editar Ausentismos. 
    @Override
    public void editar(Soaccidentes soaccidentes) {
        em.merge(soaccidentes);
    }

    /*
     *Borrar Ausentismos.
     */
    @Override
    public void borrar(Soaccidentes soaccidentes) {
        em.remove(em.merge(soaccidentes));
    }

    //Trae los Accidentes de un Empleado
    public List<Soaccidentes> accidentesEmpleado(BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT soa FROM Soaccidentes soa WHERE soa.empleado.secuencia = :secuenciaEmpleado");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            List<Soaccidentes> todosAccidentes = query.getResultList();
            return todosAccidentes;
        } catch (Exception e) {
            System.out.println("Error: (todasNovedades)" + e);
            return null;
        }
    }
}
