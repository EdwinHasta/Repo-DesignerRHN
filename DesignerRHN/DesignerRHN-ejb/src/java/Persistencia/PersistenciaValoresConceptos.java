/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import Entidades.ValoresConceptos;
import Entidades.ValoresConceptos;
import InterfacePersistencia.PersistenciaValoresConceptosInterface;
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
public class PersistenciaValoresConceptos implements PersistenciaValoresConceptosInterface {

     /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
*/

    public void crear(EntityManager em, ValoresConceptos valoresConceptos) {
        try {
            em.persist(valoresConceptos);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaValoresConceptos");
        }
    }

    public void editar(EntityManager em, ValoresConceptos valoresConceptos) {
        try {
            em.merge(valoresConceptos);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaValoresConceptos");
        }
    }

    public void borrar(EntityManager em, ValoresConceptos valoresConceptos) {
        try {
            em.remove(em.merge(valoresConceptos));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaValoresConceptos");
        }
    }

    public List<ValoresConceptos> consultarValoresConceptos(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT te FROM ValoresConceptos te");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<ValoresConceptos> valoresConceptos = query.getResultList();
            return valoresConceptos;
        } catch (Exception e) {
            System.out.println("Error consultarValoresConceptos");
            return null;
        }
    }

    public ValoresConceptos consultarValorConcepto(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT te FROM ValoresConceptos te WHERE te.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            ValoresConceptos valoresConceptos = (ValoresConceptos) query.getSingleResult();
            return valoresConceptos;
        } catch (Exception e) {
            System.out.println("Error consultarValoresConceptos");
            ValoresConceptos valoresConceptos = null;
            return valoresConceptos;
        }
    }

    public BigInteger consultarConceptoValorConcepto(EntityManager em, BigInteger concepto) {
        try {
            Query query = em.createQuery("SELECT COUNT(te) FROM ValoresConceptos te WHERE te.concepto.secuencia = :concepto");
            query.setParameter("concepto", concepto);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            BigInteger conceptosSoportes = new BigInteger(query.getSingleResult().toString());
            return conceptosSoportes;
        } catch (Exception e) {
            System.out.println("Error consultarValoresConceptos");
            ValoresConceptos conceptosSoportes = null;
            return null;
        }
    }
}
