/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import Entidades.Retenciones;
import InterfacePersistencia.PersistenciaRetencionesInterface;
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
public class PersistenciaRetenciones implements PersistenciaRetencionesInterface{
    
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Retenciones retenciones) {
        em.persist(retenciones);
    }

    @Override
    public void editar(Retenciones retenciones) {
        em.merge(retenciones);
    }

    @Override
    public void borrar(Retenciones retenciones) {
        em.remove(em.merge(retenciones));
    }
    
    @Override
    public List<Retenciones> buscarRetenciones() {
        List<Retenciones> setsLista = (List<Retenciones>) em.createNamedQuery("Retenciones.findAll").getResultList();
        return setsLista;
    }

    @Override
    public List<Retenciones> buscarRetencionesVig(BigInteger secRetencion){
        try {
            Query query = em.createQuery("SELECT r FROM Retenciones r WHERE r.vigencia.secuencia = :secRetencion");
            query.setParameter("secRetencion", secRetencion);
            
            List<Retenciones> retenciones = query.getResultList();
            return retenciones;
        } catch (Exception e) {
            System.out.println("Error en Persistencia Sets " + e);
            return null;
        }
    }
}