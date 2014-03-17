/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.VigenciasViajeros;
import InterfacePersistencia.PersistenciaVigenciasViajerosInterface;
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
public class PersistenciaVigenciasViajeros implements PersistenciaVigenciasViajerosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(VigenciasViajeros vigenciaViajero) {
        em.persist(vigenciaViajero);
    }

    public void editar(VigenciasViajeros vigenciaViajero) {
        em.merge(vigenciaViajero);
    }

    public void borrar(VigenciasViajeros vigenciaViajero) {
        em.remove(em.merge(vigenciaViajero));
    }

    public VigenciasViajeros consultarTipoExamen(BigInteger secuencia) {
        try {
            return em.find(VigenciasViajeros.class, secuencia);
        } catch (Exception e) {
            return null;
        }
    }

    public List<VigenciasViajeros> consultarVigenciasViajerosPorEmpleado(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT vrl FROM VigenciasViajeros vrl WHERE vrl.empleado.secuencia = :secuenciaEmpl ORDER BY vrl.fechavigencia DESC");
            query.setParameter("secuenciaEmpl", secEmpleado);
            List<VigenciasViajeros> vigenciasRefLab = query.getResultList();
            return vigenciasRefLab;
        } catch (Exception e) {
            System.out.println("Error en Persistencia Vigencias Reforma Laboral " + e);
            return null;
        }
    }

    public List<VigenciasViajeros> consultarVigenciasViajeros() {
        Query query = em.createQuery("SELECT te FROM VigenciasViajeros te ORDER BY te.fechavigencia ASC ");
        List<VigenciasViajeros> listMotivosDemandas = query.getResultList();
        return listMotivosDemandas;

    }
}