/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.ExtrasRecargos;
import InterfacePersistencia.PersistenciaExtrasRecargosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author PROYECTO01
 */
@Stateless
public class PersistenciaExtrasRecargos implements PersistenciaExtrasRecargosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(ExtrasRecargos extrasRecargos) {
        em.persist(extrasRecargos);
    }

    @Override
    public void editar(ExtrasRecargos extrasRecargos) {
        em.merge(extrasRecargos);
    }

    @Override
    public void borrar(ExtrasRecargos extrasRecargos) {
        em.remove(em.merge(extrasRecargos));
    }

    @Override
    public ExtrasRecargos buscarExtraRecargo(BigInteger secuencia) {
        try {
            return em.find(ExtrasRecargos.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error PersistenciaExtrasRecargos buscarExtraRecargo : " + e.toString());
            return null;
        }
    }

    @Override
    public List<ExtrasRecargos> buscarExtrasRecargos() {
        try {
            Query query = em.createQuery("SELECT er FROM ExtrasRecargos er ORDER BY er.codigo ASC");
            List<ExtrasRecargos> extrasRecargos = query.getResultList();
            return extrasRecargos;
        } catch (Exception e) {
            System.out.println("Error PersistenciaExtrasRecargos buscarExtrasRecargos : " + e.toString());
            return null;
        }
    }

}
