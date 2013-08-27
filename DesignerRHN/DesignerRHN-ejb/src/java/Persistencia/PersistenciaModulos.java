package Persistencia;

import Entidades.Modulos;
import InterfacePersistencia.PersistenciaModulosInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author -Felipphe-
 */
@Stateless
public class PersistenciaModulos implements PersistenciaModulosInterface{

     @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear Modulo.
     */
    public void crear(Modulos modulos) {
        em.persist(modulos);
    }

    /*
     *Editar modulo. 
     */
    public void editar(Modulos modulos) {
        em.merge(modulos);
    }

    /*
     *Borrar Modulo.
     */
    public void borrar(Modulos modulos) {
        em.remove(em.merge(modulos));
    }

    /*
     *Encontrar un Modulo. 
     */
    public Modulos buscarModulos(Object id) {
        return em.find(Modulos.class, id);
    }

    /*
     *Encontrar todos los Modulos. 
     */
    public List<Modulos> buscarModulos() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Modulos.class));
        return em.createQuery(cq).getResultList();
    }

}
