/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarMotivosLocalizacionesInterface;
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
public class AdministrarMotivosLocalizaciones implements AdministrarMotivosLocalizacionesInterface {

    @EJB
    PersistenciaMotivosLocalizacionesInterface PersistenciaMotivosLocalizaciones;

    @Override
    public void modificarMotivosLocalizaciones(List<MotivosLocalizaciones> listaMotivosLocalizaciones) {
        for (int i = 0; i < listaMotivosLocalizaciones.size(); i++) {
            System.out.println("Administrar Modificando...");
            PersistenciaMotivosLocalizaciones.editar(listaMotivosLocalizaciones.get(i));
        }
    }

    @Override
    public void borrarMotivosLocalizaciones(List<MotivosLocalizaciones> listaMotivosLocalizaciones) {
        for (int i = 0; i < listaMotivosLocalizaciones.size(); i++) {
            System.out.println("Administrar Borrando...");
            PersistenciaMotivosLocalizaciones.borrar(listaMotivosLocalizaciones.get(i));
        }
    }

    @Override
    public void crearMotivosLocalizaciones(List<MotivosLocalizaciones> listaMotivosLocalizaciones) {
        for (int i = 0; i < listaMotivosLocalizaciones.size(); i++) {
            System.out.println("Administrar Creando...");
            PersistenciaMotivosLocalizaciones.crear(listaMotivosLocalizaciones.get(i));
        }
    }

    @Override
    public List<MotivosLocalizaciones> mostrarMotivosCambiosCargos() {
        List<MotivosLocalizaciones> listMotivosLocalizaciones;
        listMotivosLocalizaciones = PersistenciaMotivosLocalizaciones.buscarMotivosLocalizaciones();
        return listMotivosLocalizaciones;
    }

    @Override
    public MotivosLocalizaciones mostrarMotivoCambioCargo(BigInteger secMotivosCambiosCargos) {
        MotivosLocalizaciones moticoLocalizacion;
        moticoLocalizacion = PersistenciaMotivosLocalizaciones.buscarMotivoLocalizacionSecuencia(secMotivosCambiosCargos);
        return moticoLocalizacion;
    }
    
    public BigInteger contarVigenciasLocalizacionesMotivoLocalizacion (BigInteger secMotivoLocalizacion)
    { BigInteger contarVigencias;
    contarVigencias = PersistenciaMotivosLocalizaciones.contarVigenciasLocalizacionesMotivoLocalizacion(secMotivoLocalizacion);
    return contarVigencias;
    }
}
