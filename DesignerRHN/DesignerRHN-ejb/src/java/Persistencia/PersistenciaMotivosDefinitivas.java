/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Motivosdefinitivas;
import InterfacePersistencia.PersistenciaMotivosDefinitivasInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'MotivosDefinitivas'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaMotivosDefinitivas implements PersistenciaMotivosDefinitivasInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
  
    @Override
    public void crear(Motivosdefinitivas motivosDefinitivas) {
        em.persist(motivosDefinitivas);
    }

    @Override
    public void editar(Motivosdefinitivas motivosDefinitivas) {
        em.merge(motivosDefinitivas);
    }

    @Override
    public void borrar(Motivosdefinitivas motivosDefinitivas) {
        em.remove(em.merge(motivosDefinitivas));
    }

    @Override
    public List<Motivosdefinitivas> buscarMotivosDefinitivas() {
        try {
            Query query = em.createQuery("SELECT md FROM Motivosdefinitivas md ORDER BY md.codigo");

            List<Motivosdefinitivas> motivoD = query.getResultList();
            return motivoD;
        } catch (Exception e) {
            System.out.println("Error PersistenciaMotivosDefinitivfas.buscarMotivosDefinitivas" + e);
            return null;
        }
    }
}
