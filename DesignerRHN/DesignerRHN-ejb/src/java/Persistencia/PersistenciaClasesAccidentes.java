/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaClasesAccidentesInterface;
import Entidades.ClasesAccidentes;
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
public class PersistenciaClasesAccidentes implements PersistenciaClasesAccidentesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(ClasesAccidentes clasesAccidentes) {
        em.persist(clasesAccidentes);
    }

    public void editar(ClasesAccidentes clasesAccidentes) {
        em.merge(clasesAccidentes);
    }

    public void borrar(ClasesAccidentes clasesAccidentes) {
        em.remove(em.merge(clasesAccidentes));
    }

    public ClasesAccidentes buscarClaseAccidente(BigInteger secuenciaCA) {
        try {
            return em.find(ClasesAccidentes.class, secuenciaCA);
        } catch (Exception e) {
            return null;
        }
    }

    public List<ClasesAccidentes> buscarClasesAccidentes() {
        try {
            Query query = em.createQuery("SELECT l FROM ClasesAccidentes  l ORDER BY l.codigo ASC ");
            List<ClasesAccidentes> listClasesAccidentes = query.getResultList();
            return listClasesAccidentes;
        } catch (Exception e) {
            System.err.println("ERROR PERSISTENCIACLASESACCIDENTES BUSCARCLASESACCIDENTES ERROR : " + e);
            return null;
        }

    }

    public BigInteger contadorSoAccidentesMedicos(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM soaccidentesmedicos WHERE claseaccidente =?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.err.println("Contador PERSISTENCIACLASESACCIDENTES  contadorSoAccidentesMedicos  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error PERSISTENCIACLASESACCIDENTES   contadorSoAccidentesMedicos. " + e);
            return retorno;
        }
    }
}
