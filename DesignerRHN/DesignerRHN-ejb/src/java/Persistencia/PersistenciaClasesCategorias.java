/**
 * Documentación a cargo de Andres Pineda
 */
package Persistencia;

import Entidades.ClasesCategorias;
import InterfacePersistencia.PersistenciaClasesCategoriasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'ClasesCategorias' de la
 * base de datos.
 *
 * @author Andres Pineda
 */
@Stateless
public class PersistenciaClasesCategorias implements PersistenciaClasesCategoriasInterface{

     /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(ClasesCategorias clasesCategorias) {
        try {
            em.persist(clasesCategorias);
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada (ClasesCategorias)");
        }
    }

    @Override
    public void editar(ClasesCategorias clasesCategorias) {
        try {
            em.merge(clasesCategorias);
        } catch (Exception e) {
            System.out.println("No se pudo modificar la ClaseCategoria");
        }
    }

    @Override
    public void borrar(ClasesCategorias clasesCategorias) {
        try {
            em.remove(em.merge(clasesCategorias));
        } catch (Exception e) {
            System.out.println("No se pudo borrar la ClaseCategoria");
        }
    }

    @Override
    public List<ClasesCategorias> buscarClasesCategorias() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(ClasesCategorias.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public ClasesCategorias buscarClaseCategoriaSecuencia(BigInteger secClaseCategoria) {
        try {
            Query query = em.createNamedQuery("SELECT cc FROM ClasesCategorias cc WHERE cc.secuencia=:secuencia");
            query.setParameter("secuencia", secClaseCategoria);
            ClasesCategorias clasesCategorias = (ClasesCategorias) query.getSingleResult();
            return clasesCategorias;
        } catch (Exception e) {
            return null;
        }
    }
}
