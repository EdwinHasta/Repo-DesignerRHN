/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.VWActualesUbicaciones;
import InterfacePersistencia.PersistenciaVWActualesUbicacionesInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la vista 'VWActualesUbicaciones'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVWActualesUbicaciones implements PersistenciaVWActualesUbicacionesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public VWActualesUbicaciones buscarUbicacion(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT vw FROM VWActualesUbicaciones vw WHERE vw.empleado.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            VWActualesUbicaciones vWActualesUbicaciones = (VWActualesUbicaciones) query.getSingleResult();
            return vWActualesUbicaciones;
        } catch (Exception e) {
            VWActualesUbicaciones vWActualesUbicaciones = null;
            return vWActualesUbicaciones;
        }
    }
}
