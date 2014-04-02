/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import Entidades.VigenciasRetenciones;
import InterfacePersistencia.PersistenciaVigenciasRetencionesInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaVigenciasRetenciones implements PersistenciaVigenciasRetencionesInterface{

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(VigenciasRetenciones vretenciones) {
        em.persist(vretenciones);
    }

    @Override
    public void editar(VigenciasRetenciones vretenciones) {
        em.merge(vretenciones);
    }

    @Override
    public void borrar(VigenciasRetenciones vretenciones) {
        em.remove(em.merge(vretenciones));
    }
    
    @Override
    public List<VigenciasRetenciones> buscarVigenciasRetenciones() {
        List<VigenciasRetenciones> setsLista = (List<VigenciasRetenciones>) em.createNamedQuery("VigenciasRetenciones.findAll").getResultList();
        return setsLista;
    }
}
