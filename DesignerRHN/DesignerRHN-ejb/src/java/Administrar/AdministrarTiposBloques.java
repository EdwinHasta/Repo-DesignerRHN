/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.TiposBloques;
import InterfaceAdministrar.AdministrarTiposBloquesInterface;
import InterfacePersistencia.PersistenciaFormulasInterface;
import InterfacePersistencia.PersistenciaTiposBloquesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposBloques implements AdministrarTiposBloquesInterface{
    
    @EJB
    PersistenciaTiposBloquesInterface persistenciaTiposBloques;
    @EJB
    PersistenciaFormulasInterface persistenciaFormulas;
    

    @Override
    public List<TiposBloques> buscarTiposBloques(BigInteger secuenciaOperando, String tipoOperando) {
        List<TiposBloques> listaTiposBloques;
        listaTiposBloques = persistenciaTiposBloques.tiposBloques(secuenciaOperando, tipoOperando);
        return listaTiposBloques;
    }

    @Override
    public void borrarTiposBloques(TiposBloques tiposConstantes) {
        persistenciaTiposBloques.borrar(tiposConstantes);
    }

    @Override
    public void crearTiposBloques(TiposBloques tiposConstantes) {
        persistenciaTiposBloques.crear(tiposConstantes);
    }

    @Override
    public void modificarTiposBloques(TiposBloques tiposConstantes) {
        persistenciaTiposBloques.editar(tiposConstantes);

    }
    
}