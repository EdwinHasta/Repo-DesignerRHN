/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.PryClientes;
import InterfacePersistencia.PersistenciaPryClientesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'PryClientes'
 * de la base de datos.
 * @author Viktor
 */
@Stateless
public class PersistenciaPryClientes implements PersistenciaPryClientesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
        public List<PryClientes> pryclientes() {
        try {
            Query query = em.createQuery("SELECT pc FROM PryClientes pc ORDER BY pc.nombre");
            List<PryClientes> pryclientes = query.getResultList();
            return pryclientes;
        } catch (Exception e) {
            return null;
        }
    }        
}
