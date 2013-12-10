/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Actividades;
import InterfacePersistencia.PersistenciaActividadesInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;



/**
 * Clase Stateless
 * Clase encargada de realizar operaciones sobre la tabla 'Actividades' de la base de datos
 * @author betelgeuse
 */
@Stateless
public class PersistenciaActividades implements PersistenciaActividadesInterface{

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
    @Override
    public void crear(Actividades actividades) {
        try{
        em.persist(actividades);
        } catch(Exception e){
            System.out.println("Error creando bancos PersistenciaActividades");
        }
    }

    @Override
    public void editar(Actividades actividades) {
        try {
        em.merge(actividades);
        } catch(Exception e){
            System.out.println("Error editando bancos PersistenciaActividades");
        }
    }

    @Override
    public void borrar(Actividades actividades) {
        try{
        em.remove(em.merge(actividades));
        } catch(Exception e){
            System.out.println("Error borrando bancos PersistenciaActividades");
        }
    }

    @Override
    public List<Actividades> buscarActividades() {
        try{
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Actividades.class));
        return em.createQuery(cq).getResultList();
        } catch(Exception e){
            System.out.println("Error buscarActividades PersistenciaActividades : "+e.toString());
            return null;
        }
    }

}
