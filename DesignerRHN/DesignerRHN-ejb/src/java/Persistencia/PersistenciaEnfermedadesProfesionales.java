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
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla
 * 'EnfermedadesProfesionales' de la base de datos.
 *
 * @author Viktor
 */
@Stateless
public class PersistenciaEnfermedadesProfesionales implements PersistenciaEnfermedadesProfesionalesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;*/
    @Override
    public void crear(EntityManager em, EnfermeadadesProfesionales enfermedadesProfesionales) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(enfermedadesProfesionales);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaEnfermedadesProfesionales.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, EnfermeadadesProfesionales enfermedadesProfesionales) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(enfermedadesProfesionales);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaEnfermedadesProfesionales.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, EnfermeadadesProfesionales enfermedadesProfesionales) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.remove(em.merge(enfermedadesProfesionales));
            tx.commit();

        } catch (Exception e) {
            try {
                if (tx.isActive()) {
                    tx.rollback();
                }
            } catch (Exception ex) {
                System.out.println("Error PersistenciaEnfermedadesProfesionales.borrar: " + e);
            }
        }
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
            String sqlQuery = ("SELECT ep.fechanotificacion,d.descripcion ,d.codigo FROM EnfermeadadesProfesionales ep, Diagnosticoscategorias d, Empleados e WHERE ep.empleado=e.secuencia AND e.secuencia = ? ORDER BY ep.fechanotificacion DESC");
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secEmpleado);
            List<EnfermeadadesProfesionales> enfermedadesProfesionales = query.getResultList();
            return enfermedadesProfesionales;
        } catch (Exception e) {
            System.out.println("Error: (buscarEPPorEmpleado)" + e);
            return null;
        }
        
        
    }
}
