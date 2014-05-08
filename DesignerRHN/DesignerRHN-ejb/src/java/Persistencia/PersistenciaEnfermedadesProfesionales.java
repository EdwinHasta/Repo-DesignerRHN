/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
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
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'EnfermedadesProfesionales'
 * de la base de datos.
 * @author Viktor
 */
@Stateless
public class PersistenciaEnfermedadesProfesionales implements PersistenciaEnfermedadesProfesionalesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    @Override
    public void crear(EntityManager em, EnfermeadadesProfesionales enfermedadesProfesionales) {
        em.persist(enfermedadesProfesionales);
    }

    @Override
    public void editar(EntityManager em, EnfermeadadesProfesionales enfermedadesProfesionales) {
        em.merge(enfermedadesProfesionales);
    }

    @Override
    public void borrar(EntityManager em, EnfermeadadesProfesionales enfermedadesProfesionales) {
        em.remove(em.merge(enfermedadesProfesionales));
    }

    @Override
    public EnfermeadadesProfesionales buscarEnfermedadesProfesionales(EntityManager em, BigInteger secuencia) {
        try {
            return em.find(EnfermeadadesProfesionales.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error en la persistenciaEnfermedadesProfesionales ERROR : " + e);
            return null;
        }
    }
    
    @Override
    public List<EnfermeadadesProfesionales> buscarEPPorEmpleado(EntityManager em, BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT ep.fechanotificacion,d.descripcion,d.codigo FROM EnfermeadadesProfesionales ep, Diagnosticoscategorias d WHERE ep.empleado.secuencia = :secuenciaEmpl ORDER BY ep.fechanotificacion DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<EnfermeadadesProfesionales> enfermedadesProfesionales = query.getResultList();
            return enfermedadesProfesionales;
        } catch (Exception e) {
            System.out.println("Error en PersistenciaEnfermedadesProfesionales Por Empleados ERROR" + e);
            return null;
        }
    }
}
