package Persistencia;

import Entidades.VWVacaPendientesEmpleados;
import InterfacePersistencia.PersistenciaVWVacaPendientesEmpleadosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaVWVacaPendientesEmpleados implements PersistenciaVWVacaPendientesEmpleadosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(VWVacaPendientesEmpleados vacaP) {
        try {
            em.persist(vacaP);
        } catch (Exception e) {
            System.out.println("Error creando VWVacaPendientesEmpleados : "+e.toString());
        }
    }


    @Override
    public void editar(VWVacaPendientesEmpleados vacaP) {
        try {
            em.merge(vacaP);
        } catch (Exception e) {
            System.out.println("Error editando VWVacaPendientesEmpleados : "+e.toString());
        }
    }


    @Override
    public void borrar(VWVacaPendientesEmpleados vacaP) {
        try {
            em.remove(em.merge(vacaP));
        } catch (Exception e) {
            System.out.println("Error borrando VWVacaPendientesEmpleados : "+e.toString());
        }
    }

    @Override
    public List<VWVacaPendientesEmpleados> vacaEmpleadoPendientes(BigInteger sec) {
        try {
            Query query = em.createQuery("SELECT v FROM VWVacaPendientesEmpleados v WHERE v.empleado.secuencia =:secuencia AND v.diaspendientes>=1 ORDER BY v.inicialCausacion DESC");
            query.setParameter("secuencia", sec);
            List<VWVacaPendientesEmpleados> vacapendientes = query.getResultList();
            return vacapendientes;
        } catch (Exception e) {
            System.out.println("Error en vacaEmpleadoMayorCero : " + e.toString());
            return null;
        }
    }

    @Override
    public List<VWVacaPendientesEmpleados> vacaEmpleadoDisfrutadas(BigInteger sec) {
        try {
            Query query = em.createQuery("SELECT v FROM VWVacaPendientesEmpleados v WHERE v.empleado.secuencia =:secuencia AND v.diaspendientes<=0 ORDER BY v.inicialCausacion DESC");
            query.setParameter("secuencia", sec);
            List<VWVacaPendientesEmpleados> vacapendientes = query.getResultList();
            return vacapendientes;
        } catch (Exception e) {
            System.out.println("Error en vacaEmpleadoIgualCero : " + e.toString());
            return null;
        }
    }
}
