/**
 * Documentación a cargo de Andres Pineda
 */
package Persistencia;

import Entidades.Categorias;
import InterfacePersistencia.PersistenciaCategoriasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'Categorias' de la
 * base de datos.
 *
 * @author Andres Pineda
 */
@Stateless
public class PersistenciaCategorias implements PersistenciaCategoriasInterface{

     /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Categorias categorias) {
        try {
            em.persist(categorias);
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada (Categorias)");
        }
    }

    @Override
    public void editar(Categorias categorias) {
        try {
            em.merge(categorias);
        } catch (Exception e) {
            System.out.println("No se pudo modificar la Categoria");
        }
    }

    @Override
    public void borrar(Categorias categorias) {
        try {
            em.remove(em.merge(categorias));
        } catch (Exception e) {
            System.out.println("No se pudo borrar la Categoria");
        }
    }

    @Override
    public List<Categorias> buscarCategorias() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Categorias.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public Categorias buscarCategoriaSecuencia(BigInteger secCategoria) {
        try {
            Query query = em.createNamedQuery("SELECT c FROM Categorias c WHERE c.secuencia=:secuencia");
            query.setParameter("secuencia", secCategoria);
            Categorias categorias = (Categorias) query.getSingleResult();
            return categorias;
        } catch (Exception e) {
            return null;
        }
    }
}
