/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author user
 */
@Stateless
public class PersistenciaIdiomas implements PersistenciaIdiomasInterface {

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
    public Idiomas buscarIdioma(Object id) {
        try {
            BigInteger in;
            in = (BigInteger) id;
            return em.find(Idiomas.class, in);
        } catch (Exception e) {
            System.out.println("Error buscarIdioma PersistenciaIdiomas : " + e.toString());
            return null;
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
