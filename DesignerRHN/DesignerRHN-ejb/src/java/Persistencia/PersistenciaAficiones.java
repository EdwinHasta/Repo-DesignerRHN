/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Aficiones;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import InterfacePersistencia.PersistenciaAficionesInterface;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
/**
 * Clase Stateless
 * Clase encargada de realizar operaciones sobre la tabla 'Aficiones' de la base de datos
 * @author betelgeuse
 */
@Stateless
public class PersistenciaAficiones implements PersistenciaAficionesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    /**
     * Atributo Aficiones que representa la afición con el máximo código.
     */
    Aficiones maximo = new Aficiones();
  
    @Override
    public void crear(Aficiones aficiones) {
        try {
            em.merge(aficiones);
        } catch (PersistenceException ex) {
            Logger.getLogger(Aficiones.class.getName()).log(Level.SEVERE, null, ex);
            throw new EntityExistsException(ex);
        }
    }
  
    @Override
    public void editar(Aficiones aficiones) {
        em.merge(aficiones);
    }

    @Override
    public void borrar(Aficiones aficiones) {
        em.remove(em.merge(aficiones));
    }

    @Override
    public Aficiones buscarAficion(BigInteger secuencia) {
        return em.find(Aficiones.class, secuencia);
    }

    @Override
    public List<Aficiones> buscarAficiones() {
        try {
            Query query = em.createQuery("SELECT af FROM Aficiones af ORDER BY af.codigo");
            List<Aficiones> aficiones = (List<Aficiones>) query.getResultList();
            return aficiones;
        } catch (Exception e) {
            List<Aficiones> aficiones = null;
            return aficiones;
        }
    }

    @Override
    public short maximoCodigoAficiones() {
        Short max;
        Query query = em.createQuery("SELECT af FROM Aficiones af "
                + "WHERE af.codigo=(SELECT MAX(afi.codigo) FROM Aficiones afi)");
        maximo = (Aficiones) query.getSingleResult();
        max = maximo.getCodigo();
        return max;
    }

    @Override
    public Aficiones buscarAficionCodigo(Short cod) {
        Query query = em.createQuery("SELECT af FROM Aficiones af WHERE af.codigo=:cod");
        query.setParameter("cod", cod);
        Aficiones aficiones = (Aficiones) query.getSingleResult();
        return aficiones;
    }
}
