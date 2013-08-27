package Persistencia;

import Entidades.Empleados;
import Entidades.VWActualesTiposTrabajadores;
import InterfacePersistencia.PersistenciaVWActualesTiposTrabajadoresInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVWActualesTiposTrabajadores implements PersistenciaVWActualesTiposTrabajadoresInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public VWActualesTiposTrabajadores buscarTipoTrabajador(BigInteger secuencia) {

        try {
            //Query query = em.createQuery("SELECT vw FROM VWActualesTiposTrabajadores vw WHERE vw.empleado.secuencia=:secuencia");
            //query.setParameter("secuencia", secuencia);
            //VWActualesTiposTrabajadores vwActualesTiposTrabajadores = (VWActualesTiposTrabajadores) query.getSingleResult();
            VWActualesTiposTrabajadores vwActualesTiposTrabajadores = (VWActualesTiposTrabajadores) em.createNamedQuery("VWActualesTiposTrabajadores.findByEmpleado")
                    .setParameter("empleado", secuencia)
                    .getSingleResult();
            return vwActualesTiposTrabajadores;
        } catch (Exception e) {
            VWActualesTiposTrabajadores vwActualesTiposTrabajadores = null;
            return vwActualesTiposTrabajadores;
        }

    }

    public List<VWActualesTiposTrabajadores> FiltrarTipoTrabajador(String p_tipo) {

        try {
            if (!p_tipo.isEmpty()) {
                //Query query = em.createQuery("SELECT vw FROM VWActualesTiposTrabajadores vw WHERE vw.tipoTrabajador.tipo=:p_tipo");
                //query.setParameter("p_tipo", p_tipo);
                //List<VWActualesTiposTrabajadores> vwActualesTiposTrabajadoresLista = (List<VWActualesTiposTrabajadores>) query.getResultList();
                //return vwActualesTiposTrabajadoresLista;
                List<VWActualesTiposTrabajadores> vwActualesTiposTrabajadoresLista = (List<VWActualesTiposTrabajadores>) em.createNamedQuery("VWActualesTiposTrabajadores.findByTipoTrabajador")
                        .setParameter("tipotrabajador", p_tipo)
                        .getResultList();
                return vwActualesTiposTrabajadoresLista;
            } else {
                System.out.println("Error en PersistenciaVWActualesTiposTrabajadores.FiltrarTipoTrabajador. "
                        + "No recibió el parametro");
                List<VWActualesTiposTrabajadores> vwActualesTiposTrabajadores = null;
                return vwActualesTiposTrabajadores;
            }
        } catch (Exception e) {
            List<VWActualesTiposTrabajadores> vwActualesTiposTrabajadores = null;
            return vwActualesTiposTrabajadores;
        }

    }

    public List<VWActualesTiposTrabajadores> busquedaRapidaTrabajadores() {

        try {
            //Query query = em.createQuery("SELECT vw FROM VWActualesTiposTrabajadores vw");
            //List<VWActualesTiposTrabajadores> vwActualesTiposTrabajadoresLista = (List<VWActualesTiposTrabajadores>) query.getResultList();
            List<VWActualesTiposTrabajadores> vwActualesTiposTrabajadoresLista = (List<VWActualesTiposTrabajadores>) em.createNamedQuery("VWActualesTiposTrabajadores.findAll")
                    .getResultList();
            return vwActualesTiposTrabajadoresLista;
        } catch (Exception e) {
            List<VWActualesTiposTrabajadores> vwActualesTiposTrabajadores = null;
            return vwActualesTiposTrabajadores;
        }

    }

    //VALIDACION ARCHIVO PLANO
    public boolean verificarTipoTrabajador(Empleados empleado) {
        try {
            Query query = em.createQuery("SELECT vw.tipoTrabajador.tipo FROM VWActualesTiposTrabajadores vw WHERE vw.empleado.secuencia= :secuencia");
            query.setParameter("secuencia", empleado.getSecuencia());
            String tipoEmpleado = (String) query.getSingleResult();
            if (tipoEmpleado.equalsIgnoreCase("ACTIVO")) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaVWActualesTiposTrabajadores.verificarTipoTrabajador");
            return false;
        }
    }

}
