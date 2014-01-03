/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Idiomas;
import InterfacePersistencia.PersistenciaIdiomasInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'Idiomas'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaIdiomas implements PersistenciaIdiomasInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Idiomas idiomas) {
        try {
            em.persist(idiomas);
        } catch (Exception e) {
            System.out.println("Error creando Idiomas PersistenciaIdiomas");
        }
    }

    @Override
    public void editar(Idiomas idiomas) {
        try {
            em.merge(idiomas);
        } catch (Exception e) {
            System.out.println("Error editando Idiomas PersistenciaIdiomas");
        }
    }

    @Override
    public void borrar(Idiomas idiomas) {
        try {
            em.remove(em.merge(idiomas));
        } catch (Exception e) {
            System.out.println("Error borrando Idiomas PersistenciaIdiomas");
        }
    }

    @Override
    public List<Idiomas> buscarIdiomas() {
        try {
            Query query = em.createQuery("SELECT i FROM Idiomas i");
            List<Idiomas> idioma = (List<Idiomas>) query.getResultList();
            return idioma;
        } catch (Exception e) {
            System.out.println("Error buscarIdiomas PersistenciaIdiomas : " + e.toString());
            return null;
        }
    }
}
