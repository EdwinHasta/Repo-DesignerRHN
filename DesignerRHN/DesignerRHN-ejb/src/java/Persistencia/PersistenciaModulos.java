/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Modulos;
import InterfacePersistencia.PersistenciaModulosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'Modulos'
 * de la base de datos.
 * @author -Felipphe- Felipe Triviño
 */
@Stateless
public class PersistenciaModulos implements PersistenciaModulosInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Modulos modulos) {
        em.persist(modulos);
    }

    @Override
    public void editar(Modulos modulos) {
        em.merge(modulos);
    }

    @Override
    public void borrar(Modulos modulos) {
        em.remove(em.merge(modulos));
    }

    @Override
    public Modulos buscarModulos(BigInteger secuencia) {
        return em.find(Modulos.class, secuencia);
    }

    @Override
    public List<Modulos> buscarModulos() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Modulos.class));
        return em.createQuery(cq).getResultList();
    }

}
