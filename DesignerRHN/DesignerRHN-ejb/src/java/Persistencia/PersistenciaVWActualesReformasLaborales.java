package Persistencia;

import Entidades.VWActualesReformasLaborales;
import InterfacePersistencia.PersistenciaVWActualesReformasLaboralesInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVWActualesReformasLaborales implements PersistenciaVWActualesReformasLaboralesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public VWActualesReformasLaborales buscarReformaLaboral(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT vw FROM VWActualesReformasLaborales vw WHERE vw.empleado.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            VWActualesReformasLaborales vWActualesReformasLaborales = (VWActualesReformasLaborales) query.getSingleResult();
            return vWActualesReformasLaborales;
        } catch (Exception e) {
            VWActualesReformasLaborales vWActualesReformasLaborales = null;
            return vWActualesReformasLaborales;
        }
    }
}
