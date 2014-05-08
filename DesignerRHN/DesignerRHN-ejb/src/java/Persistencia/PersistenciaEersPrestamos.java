/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.EersPrestamos;
import InterfacePersistencia.PersistenciaEersPrestamosInterface;
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
public class PersistenciaEersPrestamos implements PersistenciaEersPrestamosInterface {

    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    @Override
    public void crear(EntityManager em,EersPrestamos eersPrestamos) {
        try {
            em.persist(eersPrestamos);
        } catch (Exception e) {
            System.out.println("El eersPrestamos no exite o esta reservada por lo cual no puede ser modificada (eersPrestamos)");
        }
    }

    @Override
    public void editar(EntityManager em,EersPrestamos eersPrestamos) {
        try {
            System.out.println("eersPrestamos : "+eersPrestamos);
            System.out.println("eersPrestamos secuencia : "+eersPrestamos.getSecuencia());
            em.merge(eersPrestamos);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el eersPrestamos : " + e.toString());
        }
    }

    @Override
    public void borrar(EntityManager em,EersPrestamos eersPrestamos) {
        try {
            em.remove(em.merge(eersPrestamos));
        } catch (Exception e) {
            System.out.println("El eersPrestamos no se ha podido eliminar");
        }
    }

    @Override
    public List<EersPrestamos> eersPrestamosEmpleado(EntityManager em,BigInteger secuenciaEmpleado) {
        try {
            Query query = em.createQuery("select e FROM EersPrestamos e where e.tipoeer ='EMBARGO'AND EXISTS (SELECT em FROM Empleados em WHERE em.secuencia = e.empleado.secuencia and e.empleado.secuencia = :secuenciaEmpleado)");
            query.setParameter("secuenciaEmpleado", secuenciaEmpleado);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<EersPrestamos> eersPrestamos = query.getResultList();
            List<EersPrestamos> eersPrestamosResult = new ArrayList<EersPrestamos>(eersPrestamos);
            return eersPrestamosResult;
        } catch (Exception e) {
            System.out.println("Error: (eersPrestamos)" + e);
            return null;
        }
    }
}
