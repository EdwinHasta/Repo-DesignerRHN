/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
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
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'Soaccidentes'
 * de la base de datos.
 * @author Viktor
 */
@Stateless
public class PersistenciaSoaccidentes implements PersistenciaSoaccidentesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Soaccidentes soaccidentes) {
        try {
            em.merge(soaccidentes);
        } catch (PersistenceException ex) {
            System.out.println("Error PersistenciaSoaccidentes.crear");
        }
    }

    @Override
    public void editar(Soaccidentes soaccidentes) {
        em.merge(soaccidentes);
    }

    @Override
    public void borrar(Soaccidentes soaccidentes) {
        em.remove(em.merge(soaccidentes));
    }

    @Override
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
