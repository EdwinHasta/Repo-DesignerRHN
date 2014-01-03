/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.UbicacionesGeograficas;
import InterfacePersistencia.PersistenciaUbicacionesGeograficasInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'UbicacionesGeograficas'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaUbicacionesGeograficas implements PersistenciaUbicacionesGeograficasInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public List<UbicacionesGeograficas> buscarUbicacionesGeograficas() {
        try {
            Query query = em.createQuery("SELECT u FROM UbicacionesGeograficas u");
            List<UbicacionesGeograficas> ubicacionesGeograficas = query.getResultList();
            return ubicacionesGeograficas;
        } catch (Exception e) {
            System.out.println("Error en Persistencia Ubicaciones Geograficas " + e);
            return null;
        }
    }
}
