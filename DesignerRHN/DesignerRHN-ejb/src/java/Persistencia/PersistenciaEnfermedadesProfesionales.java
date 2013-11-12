/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.EnfermeadadesProfesionales;
import InterfacePersistencia.PersistenciaEnfermedadesProfesionalesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Viktor
 */
@Stateless
public class PersistenciaEnfermedadesProfesionales implements PersistenciaEnfermedadesProfesionalesInterface{
    
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(EnfermeadadesProfesionales enfermedadesProfesionales) {
        em.persist(enfermedadesProfesionales);
    }

    @Override
    public void editar(EnfermeadadesProfesionales enfermedadesProfesionales) {
        em.merge(enfermedadesProfesionales);
    }

    @Override
    public void borrar(EnfermeadadesProfesionales enfermedadesProfesionales) {
        em.remove(em.merge(enfermedadesProfesionales));
    }

    @Override
    public EnfermeadadesProfesionales buscarEnfermedadesProfesionales(BigInteger secuencia) {
        try {
            return em.find(EnfermeadadesProfesionales.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error en la persistenciaEnfermedadesProfesionales ERROR : " + e);
            return null;
        }
    }
    
    public List<EnfermeadadesProfesionales> buscarEPPorEmpleado(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT ep FROM EnfermeadadesProfesionales ep WHERE ep.empleado.secuencia = :secuenciaEmpl ORDER BY ep.fechanotificacion DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);

            List<EnfermeadadesProfesionales> enfermedadesProfesionales = query.getResultList();
            return enfermedadesProfesionales;
        } catch (Exception e) {
            System.out.println("Error en PersistenciaEnfermedadesProfesionales Por Empleados ERROR" + e);
            return null;
        }
    }
    
}
