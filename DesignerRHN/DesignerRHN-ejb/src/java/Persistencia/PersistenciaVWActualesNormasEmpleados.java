package Persistencia;

import Entidades.VWActualesNormasEmpleados;
import InterfacePersistencia.PersistenciaVWActualesNormasEmpleadosInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVWActualesNormasEmpleados implements PersistenciaVWActualesNormasEmpleadosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public VWActualesNormasEmpleados buscarNormaLaboral(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT vw FROM VWActualesNormasEmpleados vw WHERE vw.empleado.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            VWActualesNormasEmpleados vwActualesNormasEmpleados = (VWActualesNormasEmpleados) query.getSingleResult();
            return vwActualesNormasEmpleados;

        } catch (Exception e) {
            VWActualesNormasEmpleados vwActualesNormasEmpleados = null;
            return vwActualesNormasEmpleados;
        }

    }
}
