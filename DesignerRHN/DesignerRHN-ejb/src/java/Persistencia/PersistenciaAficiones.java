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

@Stateless
public class PersistenciaAficiones implements PersistenciaAficionesInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    Aficiones maximo = new Aficiones();
    /*
     * Crear afición.
     */

    public void crear(Aficiones aficiones) {
        try {
            em.merge(aficiones);
        } catch (PersistenceException ex) {
            Logger.getLogger(Aficiones.class.getName()).log(Level.SEVERE, null, ex);
            throw new EntityExistsException(ex);
        }
    }
        /*
         *Editar afición. 
         */
    

    public void editar(Aficiones aficiones) {
        em.merge(aficiones);
    }

    /*
     *Borrar afición.
     */
    public void borrar(Aficiones aficiones) {
        em.remove(em.merge(aficiones));
    }

    /*
     *Encontrar una afición. 
     */
    public Aficiones buscarAficion(BigInteger id) {
        return em.find(Aficiones.class, id);
    }

    /*
     *Encontrar todas las aficiónes.
     */
    public List<Aficiones> buscarAficiones() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Aficiones.class));
        return em.createQuery(cq).getResultList();
    }

//    public Aficiones buscarAf(BigDecimal cod) {
    public List<Aficiones> buscarAf() {

        try {
//            Query query = em.createQuery("SELECT af FROM Aficiones af WHERE af.codigo=:cod");
            Query query = em.createQuery("SELECT af FROM Aficiones af ORDER BY af.codigo");
//            query.setParameter("cod", cod);
//            Aficiones aficiones = (Aficiones) query.getSingleResult();
            List<Aficiones> aficiones = (List<Aficiones>) query.getResultList();

            return aficiones;
        } catch (Exception e) {
//            Aficiones aficiones = null;
            List<Aficiones> aficiones = null;
            return aficiones;
        }
    }

    public short maximoCodigoAficiones() {
        Short max = 0;
        Query query = em.createQuery("SELECT af FROM Aficiones af "
                + "WHERE af.codigo=(SELECT MAX(afi.codigo) FROM Aficiones afi)");
        maximo = (Aficiones) query.getSingleResult();
        max = maximo.getCodigo();
        return max;
    }

    public Aficiones buscarAficionCodigo(Short cod) {
        Query query = em.createQuery("SELECT af FROM Aficiones af WHERE af.codigo=:cod");
        query.setParameter("cod", cod);
        Aficiones aficiones = (Aficiones) query.getSingleResult();
        return aficiones;
    }
}
