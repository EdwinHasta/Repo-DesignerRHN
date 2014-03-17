/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Festivos;
import InterfaceAdministrar.PersistenciaFestivosInterface;
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
public class PersistenciaFestivos implements PersistenciaFestivosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(Festivos festivos) {
        em.persist(festivos);
    }

    public void editar(Festivos festivos) {
        em.merge(festivos);
    }

    public void borrar(Festivos festivos) {
        em.remove(em.merge(festivos));
    }

    public List<Festivos> consultarFestivosPais(BigInteger secPais) {
        try {
            Query query = em.createQuery("SELECT fes FROM Festivos fes WHERE fes.pais.secuencia = :secuenciaPais");
            query.setParameter("secuenciaPais", secPais);

            List<Festivos> listFestivos = query.getResultList();
            return listFestivos;
        } catch (Exception e) {
            System.err.println("Error en PERSISTENCIAFESTIVOS CONSULTARFESTIVOSPAIS : " + e);
            return null;
        }
    }

    public Festivos consultarPais(BigInteger secPais) {
        try {
            return em.find(Festivos.class, secPais);
        } catch (Exception e) {
            return null;
        }
    }
}
