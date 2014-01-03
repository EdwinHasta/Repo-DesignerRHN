/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Enfermedades;
import InterfacePersistencia.PersistenciaEnfermedadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Enfermedades'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaEnfermedades implements PersistenciaEnfermedadesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Enfermedades enfermedades) {
        em.persist(enfermedades);
    }

    @Override
    public void editar(Enfermedades enfermedades) {
        em.merge(enfermedades);
    }

    @Override
    public void borrar(Enfermedades enfermedades) {
        em.remove(em.merge(enfermedades));
    }

    @Override
    public Enfermedades buscarEnfermedad(BigInteger secuencia) {
        try {
            return em.find(Enfermedades.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error en la persistenciaEnfermedadesERROR : " + e);
            return null;
        }
    }

    @Override
    public List<Enfermedades> buscarEnfermedades() {
        try {
            Query query = em.createQuery("SELECT e FROM Enfermedades e ORDER BY e.codigo DESC");
            List<Enfermedades> enfermedades = query.getResultList();
            return enfermedades;
        } catch (Exception e) {
            System.out.println("Error en PersistenciaEnfermedadesProfesionales Por Empleados ERROR" + e);
            return null;
        }
    }
}

