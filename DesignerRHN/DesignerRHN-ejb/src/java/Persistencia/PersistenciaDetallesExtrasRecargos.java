/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.DetallesExtrasRecargos;
import InterfacePersistencia.PersistenciaDetallesExtrasRecargosInterface;
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
public class PersistenciaDetallesExtrasRecargos implements PersistenciaDetallesExtrasRecargosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(DetallesExtrasRecargos extrasRecargos) {
        em.persist(extrasRecargos);
    }

    @Override
    public void editar(DetallesExtrasRecargos extrasRecargos) {
        em.merge(extrasRecargos);
    }

    @Override
    public void borrar(DetallesExtrasRecargos extrasRecargos) {
        em.remove(em.merge(extrasRecargos));
    }

    @Override
    public DetallesExtrasRecargos buscarDetalleExtraRecargo(BigInteger secuencia) {
        try {
            return em.find(DetallesExtrasRecargos.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error PersistenciaDetallesExtrasRecargos buscarDetalleExtraRecargo : " + e.toString());
            return null;
        }
    }

    @Override
    public List<DetallesExtrasRecargos> buscaDetallesExtrasRecargos() {
        try {
            Query query = em.createQuery("SELECT der FROM DetallesExtrasRecargos der ORDER BY der.codigo DESC");
            List<DetallesExtrasRecargos> extrasRecargos = query.getResultList();
            return extrasRecargos;
        } catch (Exception e) {
            System.out.println("Error PersistenciaDetallesExtrasRecargos buscaDetallesExtrasRecargos : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<DetallesExtrasRecargos> buscaDetallesExtrasRecargosPorSecuenciaExtraRecargo(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT der FROM DetallesExtrasRecargos der WHERE der.extrarecargo.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            List<DetallesExtrasRecargos> extrasRecargos = query.getResultList();
            return extrasRecargos;
        } catch (Exception e) {
            System.out.println("Error PersistenciaDetallesExtrasRecargos buscaDetallesExtrasRecargosPorSecuenciaExtraRecargo : " + e.toString());
            return null;
        }
    }

}
