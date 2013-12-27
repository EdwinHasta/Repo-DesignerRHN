/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarMotivoLocalizacionInterface;
import Entidades.MotivosLocalizaciones;
import InterfacePersistencia.PersistenciaMotivosLocalizacionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarMotivoLocalizacion implements AdministrarMotivoLocalizacionInterface {

    @EJB
    PersistenciaMotivosLocalizacionesInterface PersistenciaMotivosLocalizaciones;
    private MotivosLocalizaciones motivoLocalizacionSeleccionado;
    private MotivosLocalizaciones moticoLocalizacion;
    private List<MotivosLocalizaciones> listMotivosLocalizaciones;

    @Override
    public void modificarMotivosLocalizaciones(List<MotivosLocalizaciones> listMotivosCambiosCargosModificadas) {
        for (int i = 0; i < listMotivosCambiosCargosModificadas.size(); i++) {
            System.out.println("Administrar Modificando...");
            motivoLocalizacionSeleccionado = listMotivosCambiosCargosModificadas.get(i);
            PersistenciaMotivosLocalizaciones.editar(motivoLocalizacionSeleccionado);
        }
    }

    @Override
    public void borrarMotivosLocalizaciones(MotivosLocalizaciones motivosLocalizaciones) {
        PersistenciaMotivosLocalizaciones.borrar(motivosLocalizaciones);
    }

    @Override
    public void crearMotivosLocalizaciones(MotivosLocalizaciones motivosLocalizaciones) {
        PersistenciaMotivosLocalizaciones.crear(motivosLocalizaciones);
    }

    @Override
    public void buscarMotivosLocalizaciones(MotivosLocalizaciones motivosLocalizaciones) {
        PersistenciaMotivosLocalizaciones.crear(motivosLocalizaciones);
    }

    @Override
    public List<MotivosLocalizaciones> mostrarMotivosCambiosCargos() {
        listMotivosLocalizaciones = PersistenciaMotivosLocalizaciones.buscarMotivosLocalizaciones();
        return listMotivosLocalizaciones;
    }

    @Override
    public MotivosLocalizaciones mostrarMotivoCambioCargo(BigInteger secMotivosCambiosCargos) {
        moticoLocalizacion = PersistenciaMotivosLocalizaciones.buscarMotivoLocalizacionSecuencia(secMotivosCambiosCargos);
        return moticoLocalizacion;
    }
}
