/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'NovedadesEmpleados'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaNovedadesEmpleados {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
}
