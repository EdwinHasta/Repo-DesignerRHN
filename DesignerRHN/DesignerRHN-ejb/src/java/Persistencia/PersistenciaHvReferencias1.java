/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaHvReferencias1Interface;
import Entidades.HVHojasDeVida;
import Entidades.HvReferencias;
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
public class PersistenciaHvReferencias1 implements PersistenciaHvReferencias1Interface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public void crear(HvReferencias hvReferencias) {
        em.persist(hvReferencias);
    }

    public void editar(HvReferencias tiposExamenes) {
        em.merge(tiposExamenes);
    }

    public void borrar(HvReferencias hvReferencias) {
        em.remove(em.merge(hvReferencias));
    }

    public HvReferencias buscarHvReferencia1(BigInteger secuenciaHvReferencias) {
        try {
            return em.find(HvReferencias.class, secuenciaHvReferencias);
        } catch (Exception e) {
            return null;
        }
    }

    public List<HvReferencias> buscarHvReferencias1() {
        Query query = em.createQuery("SELECT te FROM HvEntrevistas te ORDER BY te.fecha ASC ");
        List<HvReferencias> listHvReferencias = query.getResultList();
        return listHvReferencias;

    }

    public List<HvReferencias> buscarHvReferenciasPorEmpleado1(BigInteger secEmpleado) {
        try {
            Query query = em.createQuery("SELECT hr FROM HVHojasDeVida hv , HvReferencias hr , Empleados e WHERE hv.secuencia = hr.hojadevida.secuencia AND e.persona.secuencia= hv.persona.secuencia AND e.secuencia = :secuenciaEmpl AND hr.tipo='FAMILIARES' ORDER BY hr.nombrepersona ");
            query.setParameter("secuenciaEmpl", secEmpleado);

            List<HvReferencias> listHvReferencias = query.getResultList();
            return listHvReferencias;
        } catch (Exception e) {
            System.out.println("Error en Persistencia HvRefencias 1  " + e);
            return null;
        }
    }

    public List<HVHojasDeVida> buscarHvHojaDeVidaPorEmpleado(BigInteger secEmpleado) {
        try {
            System.err.println("secuencia empleado hoja de vida " + secEmpleado);
            Query query = em.createQuery("SELECT hv FROM HVHojasDeVida hv , HvReferencias hr , Empleados e WHERE hv.secuencia = hr.hojadevida.secuencia AND e.persona.secuencia= hv.persona.secuencia AND e.secuencia = :secuenciaEmpl");
            query.setParameter("secuenciaEmpl", secEmpleado);

            List<HVHojasDeVida> hvHojasDeVIda = query.getResultList();
            return hvHojasDeVIda;
        } catch (Exception e) {
            System.out.println("Error en Persistencia HvEntrevistas buscarHvHojaDeVidaPorEmpleado " + e);
            return null;
        }
    }
}
