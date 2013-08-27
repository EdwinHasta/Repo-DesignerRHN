package Persistencia;

import Entidades.VWActualesTiposContratos;
import InterfacePersistencia.PersistenciaVWActualesTiposContratosInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVWActualesTiposCargos implements PersistenciaVWActualesTiposContratosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public VWActualesTiposContratos buscarTiposContratosEmpleado(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT vw FROM VWActualesTiposContratos vw WHERE vw.empleado.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            VWActualesTiposContratos actualesTiposContratos = (VWActualesTiposContratos) query.getSingleResult();
            return actualesTiposContratos;
        } catch (Exception e) {
            VWActualesTiposContratos actualesTiposContratos = null;
            return actualesTiposContratos;
        }

    }
}
