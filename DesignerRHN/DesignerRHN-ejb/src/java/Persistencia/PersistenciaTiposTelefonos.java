/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.TiposTelefonos;
import InterfacePersistencia.PersistenciaTiposTelefonosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'TiposTelefonos' de la
 * base de datos.
 *
 * @author betelgeuse
 */
@Stateful
public class PersistenciaTiposTelefonos implements PersistenciaTiposTelefonosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    /*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;
     */
    @Override
    public void crear(EntityManager em, TiposTelefonos tiposTelefonos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(tiposTelefonos);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposTelefonos.crear: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void editar(EntityManager em, TiposTelefonos tiposTelefonos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(tiposTelefonos);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposTelefonos.editar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public void borrar(EntityManager em, TiposTelefonos tiposTelefonos) {
        em.clear();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(tiposTelefonos);
            tx.commit();
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposTelefonos.borrar: " + e);
            if (tx.isActive()) {
                tx.rollback();
            }
        }
    }

    @Override
    public TiposTelefonos buscarTipoTelefonos(EntityManager em, BigInteger secuencia) {
        try {
            BigInteger sec = new BigInteger(secuencia.toString());
            return em.find(TiposTelefonos.class, sec);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<TiposTelefonos> tiposTelefonos(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT tt FROM TiposTelefonos tt ORDER BY tt.codigo ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<TiposTelefonos> listaTiposTelefonos = query.getResultList();
            return listaTiposTelefonos;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposTelefonos.tiposTelefonos" + e);
            return null;
        }
    }
}
