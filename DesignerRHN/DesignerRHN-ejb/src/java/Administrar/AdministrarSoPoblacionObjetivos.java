/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Administrar;

import Entidades.SoPoblacionObjetivos;
import InterfaceAdministrar.AdministrarSoPoblacionObjetivosInterface;
import InterfacePersistencia.PersistenciaSoPoblacionObjetivosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarSoPoblacionObjetivos implements AdministrarSoPoblacionObjetivosInterface {
 @EJB
    PersistenciaSoPoblacionObjetivosInterface persistenciaSoPoblacionObjetivos;

    public void modificarSoPoblacionObjetivos(List<SoPoblacionObjetivos> listSoPoblacionObjetivos) {
        for (int i = 0; i < listSoPoblacionObjetivos.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaSoPoblacionObjetivos.editar(listSoPoblacionObjetivos.get(i));
        }
    }

    public void borrarSoPoblacionObjetivos(List<SoPoblacionObjetivos> listSoPoblacionObjetivos) {
        for (int i = 0; i < listSoPoblacionObjetivos.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaSoPoblacionObjetivos.borrar(listSoPoblacionObjetivos.get(i));
        }
    }

    public void crearSoPoblacionObjetivos(List<SoPoblacionObjetivos> listSoPoblacionObjetivos) {
        for (int i = 0; i < listSoPoblacionObjetivos.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaSoPoblacionObjetivos.crear(listSoPoblacionObjetivos.get(i));
        }
    }

 @Override
    public List<SoPoblacionObjetivos> consultarSoPoblacionObjetivos() {
        List<SoPoblacionObjetivos> listSoPoblacionObjetivos;
        listSoPoblacionObjetivos = persistenciaSoPoblacionObjetivos.consultarSoPoblacionObjetivos();
        return listSoPoblacionObjetivos;
    }

    public SoPoblacionObjetivos consultarEvalCompetencia(BigInteger secSoPoblacionObjetivo) {
        SoPoblacionObjetivos evalCompetencias;
        evalCompetencias = persistenciaSoPoblacionObjetivos.buscarSoPoblacionObjetivo(secSoPoblacionObjetivo);
        return evalCompetencias;
    }

}
