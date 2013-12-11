/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.MotivosReemplazos;
import InterfacePersistencia.PersistenciaMotivosReemplazosInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'MotivosReemplazos'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaMotivosReemplazos implements PersistenciaMotivosReemplazosInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
        public List<MotivosReemplazos> motivosReemplazos() {
        try {
            Query query = em.createQuery("SELECT mR FROM MotivosReemplazos mR ORDER BY mR.codigo");
            List<MotivosReemplazos> motivosReemplazos = query.getResultList();
            return motivosReemplazos;
        } catch (Exception e) {
            return null;
        }
    }
}
