/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import Entidades.TiposJornadas;
import InterfacePersistencia.PersistenciaTiposJornadasInterface;
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
public class PersistenciaTiposJornadas implements PersistenciaTiposJornadasInterface{

    
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(TiposJornadas tiposJornadas) {
        em.persist(tiposJornadas);
    }

    @Override
    public void editar(TiposJornadas tiposJornadas) {
        em.merge(tiposJornadas);
    }

    @Override
    public void borrar(TiposJornadas tiposJornadas) {
        em.remove(em.merge(tiposJornadas));
    }

    @Override
    public TiposJornadas buscarTipoJornada(BigInteger secuencia) {
        try {
            return em.find(TiposJornadas.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposJornadas buscarTipoJornada : " + e.toString());
            return null;
        }
    }

    @Override
    public List<TiposJornadas> buscarTiposJornadas() {
        try {
            Query query = em.createQuery("SELECT tj FROM TiposJornadas tj ORDER BY tj.codigo DESC");
            List<TiposJornadas> tiposJornadas = query.getResultList();
            return tiposJornadas;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTiposJornadas buscarTiposJornadas : " + e.toString());
            return null;
        }
    }
}
