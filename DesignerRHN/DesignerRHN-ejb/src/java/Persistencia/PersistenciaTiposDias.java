/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import Entidades.TiposDias;
import InterfacePersistencia.PersistenciaTiposDiasInterface;
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
public class PersistenciaTiposDias implements PersistenciaTiposDiasInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(TiposDias tiposDias) {
        em.persist(tiposDias);
    }

    @Override
    public void editar(TiposDias tiposDias) {
        em.merge(tiposDias);
    }

    @Override
    public void borrar(TiposDias tiposDias) {
        em.remove(em.merge(tiposDias));
    }

    @Override
    public TiposDias buscarTipoDia(BigInteger secuencia) {
        try {
            return em.find(TiposDias.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposDias buscarTipoDia : " + e.toString());
            return null;
        }
    }

    @Override
    public List<TiposDias> buscarTiposDias() {
        try {
            Query query = em.createQuery("SELECT td FROM TiposDias td ORDER BY td.codigo DESC");
            List<TiposDias> tiposDias = query.getResultList();
            return tiposDias;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposDias buscarTiposDias : " + e.toString());
            return null;
        }
    }
    
}
