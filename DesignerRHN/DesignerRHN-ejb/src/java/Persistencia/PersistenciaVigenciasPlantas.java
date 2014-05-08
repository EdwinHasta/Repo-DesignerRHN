/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import InterfacePersistencia.PersistenciaVigenciasPlantasInterface;
import Entidades.VigenciasPlantas;
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
public class PersistenciaVigenciasPlantas implements PersistenciaVigenciasPlantasInterface {

  /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
*/

    public void crear(EntityManager em, VigenciasPlantas vigenciasPlantas) {
        em.persist(vigenciasPlantas);
    }

    public void editar(EntityManager em, VigenciasPlantas vigenciasPlantas) {
        em.merge(vigenciasPlantas);
    }

    public void borrar(EntityManager em, VigenciasPlantas vigenciasPlantas) {
        em.remove(em.merge(vigenciasPlantas));
    }

    public VigenciasPlantas consultarVigenciaPlanta(EntityManager em, BigInteger secVigenciasPlantas) {
        try {
            return em.find(VigenciasPlantas.class, secVigenciasPlantas);
        } catch (Exception e) {
            return null;
        }
    }

    public List<VigenciasPlantas> consultarVigenciasPlantas(EntityManager em) {
        Query query = em.createQuery("SELECT te FROM VigenciasPlantas te ORDER BY te.codigo ASC ");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<VigenciasPlantas> listMotivosDemandas = query.getResultList();
        return listMotivosDemandas;

    }

      public BigInteger contarPlantasVigenciaPlanta(EntityManager em, BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM plantas WHERE vigencia = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaVigenciasPlantas contarPlantasVigenciaPlanta  " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PersistenciaVigenciasPlantas  contarPlantasVigenciaPlanta ERROR. " + e);
            return retorno;
        }
    }

    
}
