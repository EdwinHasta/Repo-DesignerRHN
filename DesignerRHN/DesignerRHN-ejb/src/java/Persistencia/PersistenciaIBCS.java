/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import InterfacePersistencia.PersistenciaIBCSInterface;
import Entidades.Ibcs;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'IBCS'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaIBCS implements PersistenciaIBCSInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Ibcs ibcs) {
        em.persist(ibcs);
    }

    @Override
    public void editar(Ibcs ibcs) {
        em.merge(ibcs);
    }

    @Override
    public void borrar(Ibcs ibcs) {
        em.remove(em.merge(ibcs));
    }

    @Override
    public Ibcs buscarIbcs(BigInteger secuencia) {
        try {
            return em.find(Ibcs.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error en la persistenciaIBCS formas pagos ERROR : " + e);
            return null;
        }
    }

    @Override
    public List<Ibcs> buscarIbcsPorEmpleado(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT ib FROM Ibcs ib WHERE ib.empleado.secuencia = :secuenciaEmpl ORDER BY ib.fechainicial DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);

            List<Ibcs> ibcs = query.getResultList();
            return ibcs;
        } catch (Exception e) {
            System.out.println("Error en PersistenciaIBCS Por Empleados ERROR" + e);
            return null;
        }
    }
}
