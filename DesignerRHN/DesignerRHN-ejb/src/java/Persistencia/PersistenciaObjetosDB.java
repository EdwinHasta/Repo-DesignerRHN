/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.ObjetosDB;
import InterfacePersistencia.PersistenciaObjetosDBInterface;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'ObjetosDB'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaObjetosDB implements PersistenciaObjetosDBInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public ObjetosDB obtenerObjetoTabla(String nombreTabla) {
        try {
            Query query = em.createQuery("SELECT COUNT(obj) FROM ObjetosDB obj WHERE obj.nombre = :nombreTabla");
            query.setParameter("nombreTabla", nombreTabla);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query query2 = em.createQuery("SELECT obj FROM ObjetosDB obj WHERE obj.nombre = :nombreTabla");
                query2.setParameter("nombreTabla", nombreTabla);
                ObjetosDB objetosDB = (ObjetosDB) query2.getSingleResult();
                return objetosDB;
            } else {
                System.out.println("No existe la tabla en objetosDB");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Exepcion en verificarRastroTabla " + e);
            return null;
        }
    }
}
