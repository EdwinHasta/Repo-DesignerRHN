/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
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
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'NovedadesSistema'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaNovedadesSistema implements PersistenciaNovedadesSistemaInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(NovedadesSistema novedades) {
        try {
            em.merge(novedades);
        } catch (PersistenceException ex) {
            System.out.println("Error PersistenciaNovedades.crear");
        }
    }
    
    @Override
    public void editar(NovedadesSistema novedades) {
        em.merge(novedades);
    }

    @Override
    public void borrar(NovedadesSistema novedades) {
        em.remove(em.merge(novedades));
    }

    @Override
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
