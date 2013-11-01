/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.NormasLaborales;
import InterfacePersistencia.PersistenciaNormasLaboralesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 *
 * @author user
 */
@Stateless
@LocalBean
public class PersistenciaNormasLaborales implements PersistenciaNormasLaboralesInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(NormasLaborales normasLaborales) {
        em.persist(normasLaborales);
    }

    @Override
    public void editar(NormasLaborales normasLaborales) {
        em.merge(normasLaborales);
    }

    @Override
    public void borrar(NormasLaborales normasLaborales) {
        em.remove(em.merge(normasLaborales));
    }

    @Override
    public NormasLaborales buscarNormaLaboral(BigInteger secuenciaNL) {
        try {
            return em.find(NormasLaborales.class, secuenciaNL);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<NormasLaborales> buscarNormasLaborales() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(NormasLaborales.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public Long verificarBorradoNormasLaborales(BigInteger secuencia) {
        Long retorno = new Long(-1);
        try {
            Query query = em.createQuery("SELECT count(vn) FROM VigenciasNormasEmpleados vn WHERE vn.normalaboral.secuencia =:secNormaLaboral ");
            query.setParameter("secNormaLaboral", secuencia);
            retorno = (Long) query.getSingleResult();
            System.err.println("PersistenciaMotivosCambiosSueldos retorno ==" + retorno.intValue());

        } catch (Exception e) {
            System.err.println("ERROR EN PersistenciaMotivosCambiosSueldos verificarBorradoVigenciasSueldos ERROR :" + e);
        } finally {
            return retorno;
        }
    }
}