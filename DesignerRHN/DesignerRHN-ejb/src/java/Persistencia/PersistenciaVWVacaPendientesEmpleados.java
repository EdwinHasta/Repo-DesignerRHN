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
        List<VWVacaPendientesEmpleados> listaVacaPendientesEmpleados = null;
        try {
            String script ="SELECT vwv FROM VWVacaPendientesEmpleados vwv WHERE vwv.empleado = :empleado AND vwv.diaspendientes > 0";
            Query query = em.createQuery(script).setParameter("empleado", sec);
            listaVacaPendientesEmpleados = query.getResultList();
        } catch (Exception e) {
            System.err.println("PersistenciaVWVacaPendientesEmpleados.buscarVacaPendientesEmpleados.");
            System.out.println(e);
        } finally {
            return listaVacaPendientesEmpleados;
        }
    }

    @Override
    public List<VWVacaPendientesEmpleados> vacaEmpleadoDisfrutadas(BigInteger sec) {
        List<VWVacaPendientesEmpleados> listaVacaPendientesEmpleados = null;
        try {
            String script ="SELECT vwv FROM VWVacaPendientesEmpleados vwv WHERE vwv.empleado = :empleado AND vwv.diaspendientes <= 0";
            Query query = em.createQuery(script).setParameter("empleado", sec);
            listaVacaPendientesEmpleados = query.getResultList();
        } catch (Exception e) {
            System.err.println("PersistenciaVWVacaPendientesEmpleados.buscarVacaPendientesEmpleadosDisfrutadas");
            System.out.println(e);
        } finally {
            return listaVacaPendientesEmpleados;
        }
    }

    @Override
    public List<VWVacaPendientesEmpleados> buscarVacaPendientesEmpleados(BigInteger secuenciaEmpleado) {
        List<VWVacaPendientesEmpleados> listaVacaPendientesEmpleados = null;
        try {
            listaVacaPendientesEmpleados = em.createNamedQuery("VWVacaPendientesEmpleados.findByEmpleado").setParameter("empleado", secuenciaEmpleado).getResultList();
        } catch (Exception e) {
            System.err.println("PersistenciaVWVacaPendientesEmpleados.buscarVacaPendientesEmpleados.");
            System.out.println(e);
        } finally {
            return listaVacaPendientesEmpleados;
        }
    }
}
