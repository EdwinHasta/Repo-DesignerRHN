/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.Declarantes;
import InterfacePersistencia.PersistenciaDeclarantesInterface;
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
public class PersistenciaDeclarantes implements PersistenciaDeclarantesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Declarantes declarantes) {
        em.persist(declarantes);
    }

    @Override
    public void editar(Declarantes declarantes) {
        em.merge(declarantes);
    }

    @Override
    public void borrar(Declarantes declarantes) {
        em.remove(em.merge(declarantes));
    }

    @Override
    public List<Declarantes> buscarDeclarantes() {
        List<Declarantes> declarantesLista = (List<Declarantes>) em.createNamedQuery("Declarantes.findAll")
                .getResultList();
        return declarantesLista;
    }

    @Override
    public Declarantes buscarDeclaranteSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT d FROM Declarantes d WHERE d.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            Declarantes declarantes = (Declarantes) query.getSingleResult();
            return declarantes;
        } catch (Exception e) {
            Declarantes declarantes = null;
            return declarantes;
        }
    }

    @Override
    public List<Declarantes> buscarDeclarantesPersona(BigInteger secPersona) {
        try {
            Query query = em.createQuery("SELECT d FROM Declarantes d WHERE d.persona.secuencia = :secPersona ORDER BY d.fechainicial DESC");
            query.setParameter("secPersona", secPersona);
            List<Declarantes> declarantesE = query.getResultList();
            return declarantesE;
        } catch (Exception e) {
            System.out.println("Error en Persistencia Declarantes " + e);
            return null;
        }
    }
}
