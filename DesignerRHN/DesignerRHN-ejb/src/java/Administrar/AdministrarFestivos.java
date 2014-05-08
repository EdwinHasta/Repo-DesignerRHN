/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Festivos;
import Entidades.Paises;
import InterfaceAdministrar.AdministrarFestivosInterface;
import InterfacePersistencia.PersistenciaFestivosInterface;
import InterfacePersistencia.PersistenciaPaisesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarFestivos implements AdministrarFestivosInterface {

    @EJB
    PersistenciaPaisesInterface persistenciaPaises;
    @EJB
    PersistenciaFestivosInterface persistenciaFestivos;

    public void modificarFestivos(List<Festivos> listaFestivos) {
        for (int i = 0; i < listaFestivos.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaFestivos.editar(listaFestivos.get(i));
        }
    }

    public void borrarFestivos(List<Festivos> listaFestivos) {
        for (int i = 0; i < listaFestivos.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaFestivos.borrar(listaFestivos.get(i));
        }
    }

    public void crearFestivos(List<Festivos> listaFestivos) {
        for (int i = 0; i < listaFestivos.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaFestivos.crear(listaFestivos.get(i));
        }
    }

    public List<Festivos> consultarFestivosPais(BigInteger secPais) {
        List<Festivos> listFestivos;
        listFestivos = persistenciaFestivos.consultarFestivosPais(secPais);
        return listFestivos;
    }

    public List<Paises> consultarLOVPaises() {
        List<Paises> listMotivosCambiosCargos;
        listMotivosCambiosCargos = persistenciaPaises.consultarPaises();
        return listMotivosCambiosCargos;
    }

}
