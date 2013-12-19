/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VWActualesContratos;
import InterfacePersistencia.PersistenciaVWActualesContratosInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la vista 'VWActualesContratos'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVWActualesContratos implements PersistenciaVWActualesContratosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public VWActualesContratos buscarContrato(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT vw FROM VWActualesContratos vw WHERE vw.empleado.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            VWActualesContratos actualesContratos = (VWActualesContratos) query.getSingleResult();
            return actualesContratos;
        } catch (Exception e) {
            VWActualesContratos actualesContratos =  null;
            return actualesContratos;
        }
    }
}
