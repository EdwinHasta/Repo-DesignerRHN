package Administrar;

import Entidades.Empleados;
import InterfaceAdministrar.AdministarReportesInterface;
import InterfacePersistencia.EntityManagerGlobalInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import Reportes.IniciarReporte;
import Reportes.IniciarReporteInterface;
import java.math.BigInteger;
import java.sql.Connection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;

@Stateful
public class AdministarReportes implements AdministarReportesInterface {

    @EJB
    EntityManagerGlobalInterface entityManagerGlobal;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;

    IniciarReporteInterface reporte = new IniciarReporte();
    private Connection conexion;

    public void datosConexion() {
        if (entityManagerGlobal != null) {
            /*Map<String, Object> propiedadConexion = entityManagerGlobal.getEmf().getProperties();
             for (Map.Entry<String, Object> e : propiedadConexion.entrySet()) {
             System.out.println("[" + e.getKey() + "=" + e.getValue() + "]");
             if (e.getKey().equals("javax.persistence.jdbc.url")) {
             url = (String) e.getValue();
             } else if (e.getKey().equals("javax.persistence.jdbc.driver")) {
             driver = (String) e.getValue();
             } else if (e.getKey().equals("javax.persistence.jdbc.user")) {
             user = (String) e.getValue();
             } else if (e.getKey().equals("javax.persistence.jdbc.password")) {
             psw = (String) e.getValue();
             }
             }*/
            EntityManager em = entityManagerGlobal.getEmf().createEntityManager();
            em.getTransaction().begin();
            conexion = em.unwrap(java.sql.Connection.class);
            em.getTransaction().commit();
            em.close();
        }
    }

    public void generarReporte(BigInteger codigoEmpleado) {
        datosConexion();
        reporte.ejecutarReporte(conexion, codigoEmpleado);
    }
}
