package Persistencia;

import Entidades.VWActualesContratos;
import InterfacePersistencia.PersistenciaVWActualesContratosInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVWActualesContratos implements PersistenciaVWActualesContratosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public VWActualesContratos buscarContrato(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT vw FROM VWActualesContratos vw WHERE vw.empleado.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            VWActualesContratos actualesContratos = (VWActualesContratos) query.getSingleResult();
            return actualesContratos;
        } catch (Exception e) {
            VWActualesContratos actualesContratos =  null;
            return actualesContratos;
        }
    }
}
