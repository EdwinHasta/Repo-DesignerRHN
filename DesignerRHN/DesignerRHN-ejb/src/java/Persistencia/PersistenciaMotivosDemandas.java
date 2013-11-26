/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.MotivosDemandas;
import InterfacePersistencia.PersistenciaMotivosDemandasInterface;
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
public class PersistenciaMotivosDemandas implements PersistenciaMotivosDemandasInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(MotivosDemandas motivosDemandas) {
        try {
            em.persist(motivosDemandas);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaMotivosDemandas : " + e.toString());
        }
    }

    @Override
    public void editar(MotivosDemandas motivosDemandas) {
        try {
            em.merge(motivosDemandas);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaMotivosDemandas : " + e.toString());
        }
    }

    @Override
    public void borrar(MotivosDemandas motivosDemandas) {
        try {
            em.remove(em.merge(motivosDemandas));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaMotivosDemandas : " + e.toString());
        }
    }

    @Override
    public List<MotivosDemandas> buscarMotivosDemandas() {
        try {
            Query query = em.createQuery("SELECT md FROM MotivosDemandas md ORDER BY md.secuencia");
            List<MotivosDemandas> motivosDemandas = query.getResultList();
            return motivosDemandas;
        } catch (Exception e) {
            System.out.println("Error PersistenciaMotivosDemandas.buscarMotivosDemandas" + e);
            return null;
        }

    }
}
