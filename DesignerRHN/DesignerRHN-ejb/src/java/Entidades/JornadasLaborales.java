/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "JORNADASLABORALES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "JornadasLaborales.findAll", query = "SELECT j FROM JornadasLaborales j")})
public class JornadasLaborales implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Column(name = "CODIGO")
    private Short codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "HORASDIARIAS")
    private BigDecimal horasdiarias;
    @Column(name = "HORASMENSUALES")
    private Short horasmensuales;
    @Size(max = 1)
    @Column(name = "ROTATIVO")
    private String rotativo;
    @Size(max = 1)
    @Column(name = "TURNORELATIVO")
    private String turnorelativo;
    @Column(name = "CUADRILLAHORASDIARIAS")
    private BigInteger cuadrillahorasdiarias;
    @JoinColumn(name = "JORNADA", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Jornadas jornada;

    public JornadasLaborales() {
    }

    public JornadasLaborales(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public JornadasLaborales(BigDecimal secuencia, String descripcion) {
        this.secuencia = secuencia;
        this.descripcion = descripcion;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Short getCodigo() {
        return codigo;
    }

    public void setCodigo(Short codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        if (descripcion == null) {
            descripcion = " ";
        }
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getHorasdiarias() {
        return horasdiarias;
    }

    public void setHorasdiarias(BigDecimal horasdiarias) {
        this.horasdiarias = horasdiarias;
    }

    public Short getHorasmensuales() {
        return horasmensuales;
    }

    public void setHorasmensuales(Short horasmensuales) {
        this.horasmensuales = horasmensuales;
    }

    public String getRotativo() {
        return rotativo;
    }

    public void setRotativo(String rotativo) {
        this.rotativo = rotativo;
    }

    public String getTurnorelativo() {
        return turnorelativo;
    }

    public void setTurnorelativo(String turnorelativo) {
        this.turnorelativo = turnorelativo;
    }

    public BigInteger getCuadrillahorasdiarias() {
        return cuadrillahorasdiarias;
    }

    public void setCuadrillahorasdiarias(BigInteger cuadrillahorasdiarias) {
        this.cuadrillahorasdiarias = cuadrillahorasdiarias;
    }

    public Jornadas getJornada() {
        return jornada;
    }

    public void setJornada(Jornadas jornada) {
        this.jornada = jornada;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (secuencia != null ? secuencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JornadasLaborales)) {
            return false;
        }
        JornadasLaborales other = (JornadasLaborales) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Jornadaslaborales[ secuencia=" + secuencia + " ]";
    }

}
