/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Idiomas;
import InterfacePersistencia.PersistenciaIdiomasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'Idiomas' de la base
 * de datos.
 *
 * @author betelgeuse
 */
@Stateless
public class PersistenciaIdiomas implements PersistenciaIdiomasInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    @Override
    public void crear(EntityManager em, Idiomas idiomas) {
        try {
            em.persist(idiomas);
        } catch (Exception e) {
            System.out.println("Error creando Idiomas PersistenciaIdiomas");
        }
    }

    @Override
    public void editar(EntityManager em, Idiomas idiomas) {
        try {
            em.merge(idiomas);
        } catch (Exception e) {
            System.out.println("Error editando Idiomas PersistenciaIdiomas");
        }
    }

    @Override
    public void borrar(EntityManager em, Idiomas idiomas) {
        try {
            em.remove(em.merge(idiomas));
        } catch (Exception e) {
            System.out.println("Error borrando Idiomas PersistenciaIdiomas");
        }
    }

    public Idiomas buscarIdioma(EntityManager em, BigInteger secuenciaI) {
        try {
            return em.find(Idiomas.class, secuenciaI);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Idiomas> buscarIdiomas(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT i FROM Idiomas i");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Idiomas> idioma = (List<Idiomas>) query.getResultList();
            return idioma;
        } catch (Exception e) {
            System.out.println("Error buscarIdiomas PersistenciaIdiomas : " + e.toString());
            return null;
        }
    }

    public BigInteger contadorIdiomasPersonas(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM idiomaspersonas WHERE idioma = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.err.println("Contador PersistenciaIdiomas contadorIdiomasPersonas  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PersistenciaIdiomas contadorIdiomasPersonas. " + e);
            return retorno;
        }
    }
}
