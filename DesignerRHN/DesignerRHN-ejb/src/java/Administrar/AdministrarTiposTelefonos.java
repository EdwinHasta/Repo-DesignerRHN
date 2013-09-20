/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.TiposTelefonos;
import InterfaceAdministrar.AdministrarTiposTelefonosInterface;
import InterfacePersistencia.PersistenciaTiposTelefonosInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarTiposTelefonos implements AdministrarTiposTelefonosInterface{

    @EJB
    PersistenciaTiposTelefonosInterface persistenciaTiposTelefonos;
    private TiposTelefonos tt;

    @Override
    public List<TiposTelefonos> tiposTelefonos() {
        List<TiposTelefonos> listaTiposTelefonos;
        listaTiposTelefonos = persistenciaTiposTelefonos.tiposTelefonos();
        return listaTiposTelefonos;
    }

    @Override
    public void modificarTipoTelefono(List<TiposTelefonos> listaTiposTelefonosModificar) {
        for (int i = 0; i < listaTiposTelefonosModificar.size(); i++) {
            System.out.println("Modificando...");
            if (listaTiposTelefonosModificar.get(i).getSecuencia() == null) {
                tt = listaTiposTelefonosModificar.get(i);
            } else {
                tt = listaTiposTelefonosModificar.get(i);
            }
            persistenciaTiposTelefonos.editar(tt);
        }
    }

    @Override
    public void borrarTipoTelefono(TiposTelefonos tipoTelefono) {
        persistenciaTiposTelefonos.borrar(tipoTelefono);
    }

    @Override
    public void crearTipoTelefono(TiposTelefonos tipoTelefono) {
        persistenciaTiposTelefonos.crear(tipoTelefono);
    }
}
