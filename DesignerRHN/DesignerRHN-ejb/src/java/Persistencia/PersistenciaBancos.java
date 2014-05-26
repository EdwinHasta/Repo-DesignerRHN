/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Bancos;
import InterfacePersistencia.PersistenciaBancosInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Bancos' de la base de
 * datos
 *
 * @author Andrés Pineda
 */
@Stateless
public class PersistenciaBancos implements PersistenciaBancosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
     private EntityManager em;*/
    @Override
    public void crear(EntityManager em, Bancos bancos) {
        try {
            em.getTransaction().begin();
            em.persist(bancos);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error creando bancos persistencia bancos");
        }
    }

    @Override
    public void editar(EntityManager em, Bancos bancos) {
        try {
            em.getTransaction().begin();
            em.merge(bancos);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error editando bancos persistencia bancos");
        }
    }

    @Override
    public void borrar(EntityManager em, Bancos bancos) {
        try {
            em.getTransaction().begin();
            em.remove(em.merge(bancos));
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error borrando bancos persistencia bancos");
        }
    }

    @Override
    public List<Bancos> buscarBancos(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT b FROM Bancos b");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Bancos> lista = query.getResultList();
            return lista;
        } catch (Exception e) {
            System.out.println("Error buscarBancos persistencia bancos : " + e.toString());
            return null;
        }
    }
}
