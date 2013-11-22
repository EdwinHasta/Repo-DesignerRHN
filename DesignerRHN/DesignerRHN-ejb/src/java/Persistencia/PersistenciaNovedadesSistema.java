/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.NovedadesSistema;
import InterfacePersistencia.PersistenciaNovedadesSistemaInterface;
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
public class PersistenciaNovedadesSistema implements PersistenciaNovedadesSistemaInterface{

   @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(NovedadesSistema novedades) {
        try {
//            System.out.println("Persona: " + vigenciasFormales.getPersona().getNombreCompleto());
            em.merge(novedades);
        } catch (PersistenceException ex) {
            System.out.println("Error PersistenciaNovedades.crear");
        }
    }

    // Editar Novedades. 
    @Override
    public void editar(NovedadesSistema novedades) {
        em.merge(novedades);
    }

    /*
     *Borrar Novedades.
     */
    @Override
    public void borrar(NovedadesSistema novedades) {
        em.remove(em.merge(novedades));
    }
    
    //Trae las Novedades del Concepto seleccionado

    public List<NovedadesSistema> novedadesEmpleado(BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("SELECT n FROM NovedadesSistema n WHERE n.tipo = 'DEFINITIVA' and n.empleado.secuencia = :secuenciaEmpleado ORDER BY n.fechainicialdisfrute DESC");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            List<NovedadesSistema> novedadesSistema = query.getResultList();
            return novedadesSistema;
        } catch (Exception e) {
            System.out.println("Error: (novedadesEmpleado)" + e);
            return null;
        }
    }
}
