/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Persistencia;

import Entidades.EersPrestamosDtos;
import InterfacePersistencia.PersistenciaEersPrestamosDtosInterface;
import java.math.BigInteger;
import java.util.ArrayList;
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
public class PersistenciaEersPrestamosDtos implements PersistenciaEersPrestamosDtosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(EersPrestamosDtos eersPrestamosDtos) {
        try {
            em.merge(eersPrestamosDtos);
        } catch (Exception e) {
            System.out.println("El eersPrestamosDtos no exite o esta reservada por lo cual no puede ser modificada (eersPrestamosDtos)");
        }
    }

    @Override
    public void editar(EersPrestamosDtos eersPrestamosDtos) {
        try {
            em.merge(eersPrestamosDtos);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el eersPrestamosDtos");
        }
    }

    @Override
    public void borrar(EersPrestamosDtos eersPrestamosDtos) {
        try {
            em.remove(em.merge(eersPrestamosDtos));
        } catch (Exception e) {
            System.out.println("El eersPrestamosDtos no se ha podido eliminar");
        }
    }

    @Override
    public List<EersPrestamosDtos> eersPrestamosDtosEmpleado(BigInteger secuenciaEersPrestamo) {
        try {
            Query query = em.createQuery("select e from EersPrestamosDtos e where e.eerprestamo.secuencia = :secuenciaEersPrestamo ");
            query.setParameter("secuenciaEersPrestamo", secuenciaEersPrestamo);
            List<EersPrestamosDtos> eersPrestamosDtos = query.getResultList();
            List<EersPrestamosDtos> eersPrestamosDtosResult = new ArrayList<EersPrestamosDtos>(eersPrestamosDtos);
            return eersPrestamosDtosResult;
        } catch (Exception e) {
            System.out.println("Error: (eersPrestamosDtos)" + e);
            return null;
        }
    }
}
