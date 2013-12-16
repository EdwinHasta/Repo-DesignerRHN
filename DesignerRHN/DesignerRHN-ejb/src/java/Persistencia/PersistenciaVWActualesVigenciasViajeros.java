/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VWActualesVigenciasViajeros;
import InterfacePersistencia.PersistenciaVWActualesVigenciasViajerosInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la vista 'VWActualesVigenciasViajeros'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVWActualesVigenciasViajeros implements PersistenciaVWActualesVigenciasViajerosInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public VWActualesVigenciasViajeros buscarTipoViajero(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT vw FROM VWActualesVigenciasViajeros vw WHERE vw.empleado.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            VWActualesVigenciasViajeros vWActualesVigenciasViajeros = (VWActualesVigenciasViajeros) query.getSingleResult();
            return vWActualesVigenciasViajeros;
        } catch (Exception e) {
            VWActualesVigenciasViajeros vWActualesVigenciasViajeros = null;
            return vWActualesVigenciasViajeros;
        }
    }
}
