/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import Entidades.VigenciasRetencionesMinimas;
import InterfacePersistencia.PersistenciaVigenciasRetencionesMinimasInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaVigenciasRetencionesMinimas implements PersistenciaVigenciasRetencionesMinimasInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(VigenciasRetencionesMinimas vretenciones) {
        em.persist(vretenciones);
    }

    @Override
    public void editar(VigenciasRetencionesMinimas vretenciones) {
        em.merge(vretenciones);
    }

    @Override
    public void borrar(VigenciasRetencionesMinimas vretenciones) {
        em.remove(em.merge(vretenciones));
    }
    
    @Override
    public List<VigenciasRetencionesMinimas> buscarVigenciasRetencionesMinimas() {
        List<VigenciasRetencionesMinimas> setsLista = (List<VigenciasRetencionesMinimas>) em.createNamedQuery("VigenciasRetencionesMinimas.findAll").getResultList();
        return setsLista;
    }
}
