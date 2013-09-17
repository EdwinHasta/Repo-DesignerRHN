package Administrar;

import Entidades.ObjetosDB;
import Entidades.Rastros;
import Entidades.RastrosValores;
import InterfaceAdministrar.AdministrarRastrosInterface;
import InterfacePersistencia.PersistenciaObjetosDBInterface;
import InterfacePersistencia.PersistenciaRastrosInterface;
import InterfacePersistencia.PersistenciaRastrosTablasInterface;
import InterfacePersistencia.PersistenciaRastrosValoresInterface;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarRastros implements AdministrarRastrosInterface {

    @EJB
    PersistenciaObjetosDBInterface persistenciaObjetosDB;
    @EJB
    PersistenciaRastrosTablasInterface persistenciaRastrosTablas;
    @EJB
    PersistenciaRastrosInterface persistenciaRastros;
    @EJB
    PersistenciaRastrosValoresInterface persistenciaRastrosValores;

    //1 = EL NOMBRE DE LA TABLA NO EXISTE EN OBJETOSDB
    //2 = RASTROS DETECTADOS PARA EL REGISTRO
    //3 = EL REGISTRO DE LA TABLA NO TIENE RASTROS
    //4 = LA TABLA NO EXISTE EN RASTROS TABLAS PERO TIENE REGISTROS EN RASTROS.
    //5 = LA TABLA NO EXISTE EN RASTROS TABLAS Y NO TIENE REGISTROS EN RASTROS.
    @Override
    public int obtenerTabla(BigInteger secRegistro, String nombreTabla) {
        ObjetosDB objetoDB = persistenciaObjetosDB.obtenerObjetoTabla(nombreTabla);
        if (objetoDB != null) {
            if (persistenciaRastrosTablas.verificarRastroTabla(objetoDB.getSecuencia())) {
                if (persistenciaRastros.verificarRastroRegistroTabla(secRegistro, nombreTabla)) {
                    return 2;
                } else {
                    return 3;
                }
            } else {
                if (persistenciaRastros.verificarRastroRegistroTabla(secRegistro, nombreTabla)) {
                    return 4;
                } else {
                    return 5;
                }
            }
        } else {
            return 1;
        }
    }

    @Override
    public boolean verificarHistoricosTabla(String nombreTabla) {
        if (persistenciaRastros.verificarRastroHistoricoTabla(nombreTabla)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Rastros> rastrosTabla(BigInteger secRegistro, String nombreTabla) {
        return persistenciaRastros.rastrosTabla(secRegistro, nombreTabla);
    }

    @Override
    public List<Rastros> rastrosTablaHistoricos(String nombreTabla) {
        return persistenciaRastros.rastrosTablaHistoricos(nombreTabla);
    }

    @Override
    public List<Rastros> rastrosTablaHistoricosEliminados(String nombreTabla) {
        return persistenciaRastros.rastrosTablaHistoricosEliminados(nombreTabla);
    }
    
    @Override
    public List<Rastros> rastrosTablaHistoricosEliminadosEmpleado(String nombreTabla) {
        return persistenciaRastros.rastrosTablaHistoricosEliminadosEmpleados(nombreTabla);
    }

    @Override
    public List<Rastros> rastrosTablaFecha(Date fechaRegistro, String nombreTabla) {
        return persistenciaRastros.rastrosTablaFecha(fechaRegistro, nombreTabla);
    }

    @Override
    public List<RastrosValores> valorRastro(BigInteger secRastro) {
        return persistenciaRastrosValores.rastroValores(secRastro);
    }
    
    @Override
    public boolean existenciaEmpleadoTabla(String nombreTabla){
        return persistenciaRastros.verificarEmpleadoTabla(nombreTabla);
    }
    
}
