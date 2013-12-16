/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.TiposEducaciones;
import InterfacePersistencia.PersistenciaTiposEducacionesInterface;
import java.util.List;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.persistence.Query;
/**
 * Interface encargada de determinar las operaciones que se realizan sobre la tabla 'TiposEducaciones' 
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaTiposEducaciones implements PersistenciaTiposEducacionesInterface{
@PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @Override
    public List<TiposEducaciones> tiposEducaciones() {
        try {
            Query query = em.createQuery("SELECT tE FROM TiposEducaciones tE ORDER BY tE.nombre");
            List<TiposEducaciones> tiposEducaciones = query.getResultList();
            return tiposEducaciones;
        } catch (Exception e) {
            return null;
        }
    }   
}
