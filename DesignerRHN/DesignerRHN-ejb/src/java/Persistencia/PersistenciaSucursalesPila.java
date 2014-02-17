/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaSucursalesPilaInterface;
import Entidades.SucursalesPila;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaSucursalesPila implements PersistenciaSucursalesPilaInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public List<SucursalesPila> consultarSucursalesPila() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(SucursalesPila.class));
        return em.createQuery(cq).getResultList();
    }
    
    
    public List<SucursalesPila> consultarSucursalesPilaPorEmpresa(BigInteger secEmpresa) {
        try {
            Query query = em.createQuery("SELECT cce FROM SucursalesPila cce WHERE cce.empresa.secuencia = :secuenciaEmpr ORDER BY cce.codigo ASC");
            query.setParameter("secuenciaEmpr", secEmpresa);
            List<SucursalesPila> centrosCostos = query.getResultList();
            return centrosCostos;
        } catch (Exception e) {
            System.out.println("Error en Persistencia PersistenciaSucursalesPila buscarSucursalesPilaPorEmpresa  ERROR : " + e);
            return null;
        }
    }

}
