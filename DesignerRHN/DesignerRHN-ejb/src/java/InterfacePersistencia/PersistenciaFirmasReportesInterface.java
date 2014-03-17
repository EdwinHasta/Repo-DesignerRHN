/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.FirmasReportes;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaFirmasReportesInterface {

    public void crear(FirmasReportes tiposCursos);

    public void editar(FirmasReportes tiposCursos);

    public void borrar(FirmasReportes tiposCursos);

    public List<FirmasReportes> consultarFirmasReportes();

    public FirmasReportes consultarFirmaReporte(BigInteger secuencia);
}
