/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.SectoresEvaluaciones;
import InterfaceAdministrar.AdministrarSectoresEvaluacionesInterface;
import InterfacePersistencia.PersistenciaSectoresEvaluacionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarSectoresEvaluaciones implements AdministrarSectoresEvaluacionesInterface {

    @EJB
    PersistenciaSectoresEvaluacionesInterface persistenciaSectoresEvaluaciones;

    @Override
    public void modificarSectoresEvaluaciones(List<SectoresEvaluaciones> listaSectoresEvaluaciones) {
        for (int i = 0; i < listaSectoresEvaluaciones.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaSectoresEvaluaciones.editar(listaSectoresEvaluaciones.get(i));
        }
    }

    @Override
    public void borrarSectoresEvaluaciones(List<SectoresEvaluaciones> listaSectoresEvaluaciones) {
        for (int i = 0; i < listaSectoresEvaluaciones.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaSectoresEvaluaciones.borrar(listaSectoresEvaluaciones.get(i));
        }
    }

    @Override
    public void crearSectoresEvaluaciones(List<SectoresEvaluaciones> listaSectoresEvaluaciones) {
        for (int i = 0; i < listaSectoresEvaluaciones.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaSectoresEvaluaciones.crear(listaSectoresEvaluaciones.get(i));
        }
    }

    @Override
    public List<SectoresEvaluaciones> consultarSectoresEvaluaciones() {
        List<SectoresEvaluaciones> listaSectoresEvaluaciones;
        listaSectoresEvaluaciones = persistenciaSectoresEvaluaciones.consultarSectoresEvaluaciones();
        return listaSectoresEvaluaciones;
    }

    @Override
    public SectoresEvaluaciones consultarSectorEvaluacion(BigInteger secSectoresEvaluaciones) {
        SectoresEvaluaciones soActosInseguros;
        soActosInseguros = persistenciaSectoresEvaluaciones.consultarSectorEvaluacion(secSectoresEvaluaciones);
        return soActosInseguros;
    }

}
