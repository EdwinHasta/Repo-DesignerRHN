/**
 * Documentación a cargo de Andres Pineda
 */
package Persistencia;

import Entidades.SubCategorias;
import InterfacePersistencia.PersistenciaSubCategoriasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'SubCategorias' de la
 * base de datos.
 *
 * @author Andres Pineda
 */
@Stateless
public class PersistenciaSubCategorias implements PersistenciaSubCategoriasInterface{

      /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(SubCategorias subCategorias) {
        try {
            em.persist(subCategorias);
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada (SubCategoria)");
        }
    }

    @Override
    public void editar(SubCategorias subCategorias) {
        try {
            em.merge(subCategorias);
        } catch (Exception e) {
            System.out.println("No se pudo modificar la SubCategoria");
        }
    }

    @Override
    public void borrar(SubCategorias subCategorias) {
        try {
            em.remove(em.merge(subCategorias));
        } catch (Exception e) {
            System.out.println("No se pudo borrar la SubCategoria");
        }
    }

    @Override
    public List<SubCategorias> buscarSubCategorias() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(SubCategorias.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public SubCategorias buscarSubCategoriaSecuencia(BigInteger secSubCategoria) {
        try {
            Query query = em.createNamedQuery("SELECT sc FROM SubCategorias sc WHERE sc.secuencia=:secuencia");
            query.setParameter("secuencia", secSubCategoria);
            SubCategorias subCategorias = (SubCategorias) query.getSingleResult();
            return subCategorias;
        } catch (Exception e) {
            return null;
        }
    }
}
