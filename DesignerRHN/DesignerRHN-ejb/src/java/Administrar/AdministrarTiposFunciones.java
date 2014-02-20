/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.TiposFunciones;
import InterfaceAdministrar.AdministrarTiposFuncionesInterface;
import InterfacePersistencia.PersistenciaTiposFuncionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposFunciones implements AdministrarTiposFuncionesInterface {

    @EJB
    PersistenciaTiposFuncionesInterface persistenciaTiposFunciones;

    public List<TiposFunciones> buscarTiposFunciones(BigInteger secuenciaOperando, String tipoOperando) {
        List<TiposFunciones> listaTiposFunciones;
        listaTiposFunciones = persistenciaTiposFunciones.tiposFunciones(secuenciaOperando, tipoOperando);
        return listaTiposFunciones;
    }

    @Override
    public void borrarTiposFunciones(TiposFunciones tiposFunciones) {
        persistenciaTiposFunciones.borrar(tiposFunciones);
    }

    @Override
    public void crearTiposFunciones(TiposFunciones tiposFunciones) {
        persistenciaTiposFunciones.crear(tiposFunciones);
    }

    @Override
    public void modificarTiposFunciones(List<TiposFunciones> listaTiposFuncionesModificar) {
        for (int i = 0; i < listaTiposFuncionesModificar.size(); i++) {
            System.out.println("Modificando...");
            if (listaTiposFuncionesModificar.get(i).getFechafinal()== null) {
                listaTiposFuncionesModificar.get(i).setFechafinal(null);
            }
            if (listaTiposFuncionesModificar.get(i).getNombreobjeto()== null) {
                listaTiposFuncionesModificar.get(i).setNombreobjeto(null);
            }
            persistenciaTiposFunciones.editar(listaTiposFuncionesModificar.get(i));
        }
    }
}
