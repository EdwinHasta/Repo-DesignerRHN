/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.TiposReemplazos;
import InterfacePersistencia.PersistenciaTiposReemplazosInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'TiposReemaplazos'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless

public class PersistenciaTiposReemaplazos implements PersistenciaTiposReemplazosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
     
    @Override
        public List<TiposReemplazos> tiposReemplazos() {
        try {
            Query query = em.createQuery("SELECT tR FROM TiposReemplazos tR ORDER BY tr.codigo");
            List<TiposReemplazos> tiposReemplazos = query.getResultList();
            return tiposReemplazos;
        } catch (Exception e) {
            return null;
        }
    }
}
