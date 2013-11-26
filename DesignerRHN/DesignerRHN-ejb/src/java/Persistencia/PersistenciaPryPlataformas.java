/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.PryPlataformas;
import InterfacePersistencia.PersistenciaPryPlataformasInterface;
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
public class PersistenciaPryPlataformas implements PersistenciaPryPlataformasInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear Proyectos
     */
    @Override
    public void crear(PryPlataformas plataformas) {
        try {
            em.persist(plataformas);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaPryPlataformas : " + e.toString());
        }
    }

    /*
     *Editar Proyectos
     */
    @Override
    public void editar(PryPlataformas plataformas) {
        try {
            em.merge(plataformas);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaPryPlataformas : " + e.toString());
        }
    }

    /*
     *Borrar Proyectos
     */
    @Override
    public void borrar(PryPlataformas plataformas) {
        try {
            em.remove(em.merge(plataformas));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaPryPlataformas : " + e.toString());
        }
    }

    /*
     *Encontrar un Proyecto
     */
    @Override
    public PryPlataformas buscarPryPlataforma(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            return em.find(PryPlataformas.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarPryPlataforma PersistenciaPryPlataformas : " + e.toString());
            return null;
        }

    }

    /*
     *Encontrar todas los proyectos
     */
    @Override
    public List<PryPlataformas> buscarPryPlataformas() {
        try {
            List<PryPlataformas> plataformas = (List<PryPlataformas>) em.createNamedQuery("PryPlataformas.findAll").getResultList();
            return plataformas;
        } catch (Exception e) {
            System.out.println("Error buscarPryPlataformas PersistenciaPryPlataformas : " + e.toString());
            return null;
        }
    }

    @Override
    public PryPlataformas buscarPryPlataformaSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT p FROM PryPlataformas p WHERE p.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            PryPlataformas plataformas = (PryPlataformas) query.getSingleResult();
            return plataformas;
        } catch (Exception e) {
            System.out.println("Error buscarPryPlataformaSecuencia PersistenciaPryPlataformas : " + e.toString());
            PryPlataformas plataformas = null;
            return plataformas;
        }

    }
}
