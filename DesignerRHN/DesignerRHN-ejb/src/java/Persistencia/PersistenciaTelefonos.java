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
/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'Telefonos'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaTelefonos implements PersistenciaTelefonosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Telefonos telefonos) {
        try {
            Telefonos t = em.merge(telefonos);
            
        } catch (Exception e) {
            System.out.println("Error en PersistenciaTelefonos.crear: " + e);
        }
    }

    @Override
    public void editar(Telefonos telefonos) {
        em.merge(telefonos);
    }

    @Override
    public void borrar(Telefonos telefonos) {
        em.remove(em.merge(telefonos));
    }

    @Override
    public Telefonos buscarTelefono(BigInteger secuencia) {
        return em.find(Telefonos.class, secuencia);
    }

    @Override
    public List<Telefonos> buscarTelefonos() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Telefonos.class));
        return em.createQuery(cq).getResultList();
    } 
 
    @Override
    public List<Telefonos> telefonosPersona(BigInteger secuenciaPersona) {
        try {
            Query query = em.createQuery("SELECT t "
                                       + "FROM Telefonos t "
                                       + "WHERE t.persona.secuencia = :secuenciaPersona "
                                       + "ORDER BY t.fechavigencia DESC");
            query.setParameter("secuenciaPersona", secuenciaPersona);
            List<Telefonos> listaTelefonos = query.getResultList();
            return listaTelefonos;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTelefonos.telefonoPersona" + e);
            return null;
        }
    }
}
