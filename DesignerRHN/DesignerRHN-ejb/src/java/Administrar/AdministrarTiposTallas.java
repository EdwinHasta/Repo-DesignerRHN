/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import InterfaceAdministrar.AdministrarTiposTallasInterface;
import Entidades.TiposTallas;
import InterfacePersistencia.PersistenciaTiposTallasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTiposTallas implements AdministrarTiposTallasInterface {

    @EJB
    PersistenciaTiposTallasInterface persistenciaTiposTallas;

    @Override
    public void modificarTiposTallas(List<TiposTallas> listTiposTallas) {
        for (int i = 0; i < listTiposTallas.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaTiposTallas.editar(listTiposTallas.get(i));
        }
    }

    @Override
    public void borrarTiposTallas(List<TiposTallas> listTiposTallas) {
        for (int i = 0; i < listTiposTallas.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaTiposTallas.borrar(listTiposTallas.get(i));
        }
    }

    @Override
    public void crearTiposTallas(List<TiposTallas> listTiposTallas) {
        for (int i = 0; i < listTiposTallas.size(); i++) {
            System.out.println("Administrar Creando...");
            persistenciaTiposTallas.crear(listTiposTallas.get(i));
        }
    }

    @Override
    public List<TiposTallas> consultarTiposTallas() {
        List<TiposTallas> listTiposTallas;
        listTiposTallas = persistenciaTiposTallas.buscarTiposTallas();
        return listTiposTallas;
    }

    @Override
    public TiposTallas consultarTipoTalla(BigInteger secTipoEmpresa) {
        TiposTallas tiposTallas;
        tiposTallas = persistenciaTiposTallas.buscarTipoTalla(secTipoEmpresa);
        return tiposTallas;
    }

    @Override
    public BigInteger contarElementosTipoTalla(BigInteger secuenciaElementos) {
        try {
            BigInteger verificadorElementos;
            System.err.println("Secuencia Borrado Elementos" + secuenciaElementos);
            return verificadorElementos = persistenciaTiposTallas.contadorElementos(secuenciaElementos);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTiposTallas verificarBorradoElementos ERROR :" + e);
            return null;
        }
    }

    @Override
    public BigInteger contarVigenciasTallasTipoTalla(BigInteger secuenciaVigenciasTallas) {
        try {
            BigInteger verificadorVigenciasTallas;
            System.err.println("Secuencia Borrado Vigencias Tallas" + secuenciaVigenciasTallas);
            return verificadorVigenciasTallas = persistenciaTiposTallas.contadorVigenciasTallas(secuenciaVigenciasTallas);
        } catch (Exception e) {
            System.err.println("ERROR AdministrarTiposTallas verificarBorradoVigenciasTallas ERROR :" + e);
            return null;
        }
    }
}
