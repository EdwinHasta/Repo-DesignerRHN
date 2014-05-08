/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.ExtrasRecargos;
import InterfacePersistencia.PersistenciaExtrasRecargosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'ExtrasRecargos' 
 * de la base de datos.
 * @author Andres Pineda.
 */
@Stateless
public class PersistenciaExtrasRecargos implements PersistenciaExtrasRecargosInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    @Override
    public void crear(EntityManager em,ExtrasRecargos extrasRecargos) {
        em.persist(extrasRecargos);
    }

    @Override
    public void editar(EntityManager em,ExtrasRecargos extrasRecargos) {
        em.merge(extrasRecargos);
    }

    @Override
    public void borrar(EntityManager em,ExtrasRecargos extrasRecargos) {
        em.remove(em.merge(extrasRecargos));
    }

    @Override
    public ExtrasRecargos buscarExtraRecargo(EntityManager em,BigInteger secuencia) {
        try {
            return em.find(ExtrasRecargos.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error PersistenciaExtrasRecargos buscarExtraRecargo : " + e.toString());
            return null;
        }
    }

    @Override
    public List<ExtrasRecargos> buscarExtrasRecargos(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT er FROM ExtrasRecargos er ORDER BY er.codigo ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<ExtrasRecargos> extrasRecargos = query.getResultList();
            return extrasRecargos;
        } catch (Exception e) {
            System.out.println("Error PersistenciaExtrasRecargos buscarExtrasRecargos : " + e.toString());
            return null;
        }
    }
}
