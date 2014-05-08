/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Telefonos;
import InterfacePersistencia.PersistenciaTelefonosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'Telefonos'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaTelefonos implements PersistenciaTelefonosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
*/
    @Override
    public void crear(EntityManager em, Telefonos telefonos) {
        try {
            Telefonos t = em.merge(telefonos);
            
        } catch (Exception e) {
            System.out.println("Error en PersistenciaTelefonos.crear: " + e);
        }
    }

    @Override
    public void editar(EntityManager em, Telefonos telefonos) {
        em.merge(telefonos);
    }

    @Override
    public void borrar(EntityManager em, Telefonos telefonos) {
        em.remove(em.merge(telefonos));
    }

    @Override
    public Telefonos buscarTelefono(EntityManager em, BigInteger secuencia) {
        return em.find(Telefonos.class, secuencia);
    }

    @Override
    public List<Telefonos> buscarTelefonos(EntityManager em) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Telefonos.class));
        return em.createQuery(cq).getResultList();
    } 
 
    @Override
    public List<Telefonos> telefonosPersona(EntityManager em, BigInteger secuenciaPersona) {
        try {
            Query query = em.createQuery("SELECT t "
                                       + "FROM Telefonos t "
                                       + "WHERE t.persona.secuencia = :secuenciaPersona "
                                       + "ORDER BY t.fechavigencia DESC");
            query.setParameter("secuenciaPersona", secuenciaPersona);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Telefonos> listaTelefonos = query.getResultList();
            return listaTelefonos;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTelefonos.telefonoPersona" + e);
            return null;
        }
    }
}
