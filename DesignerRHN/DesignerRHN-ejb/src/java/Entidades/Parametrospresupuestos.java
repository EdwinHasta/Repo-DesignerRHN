/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "PARAMETROSPRESUPUESTOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parametrospresupuestos.findAll", query = "SELECT p FROM Parametrospresupuestos p")})
public class Parametrospresupuestos implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SECUENCIA")
    private BigDecimal secuencia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHADESDE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechadesde;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAHASTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechahasta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHAGENERACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechageneracion;
    @Size(max = 15)
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "MOV_NUMERO")
    private BigInteger movNumero;
    @Size(max = 15)
    @Column(name = "ESTADOTESORERIA")
    private String estadotesoreria;
    @Size(max = 200)
    @Column(name = "COMENTARIO")
    private String comentario;
    @Size(max = 4000)
    @Column(name = "RESPUESTAXML")
    private String respuestaxml;
    @Column(name = "EMPRESA_CODIGO")
    private Short empresaCodigo;
    @OneToMany(mappedBy = "parametropresupuesto")
    private Collection<Solucionesnodos> solucionesnodosCollection;
    @JoinColumn(name = "TIPOTRABAJADOR", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private TiposTrabajadores tipotrabajador;
    @JoinColumn(name = "PROCESO", referencedColumnName = "SECUENCIA")
    @ManyToOne
    private Procesos proceso;

    public Parametrospresupuestos() {
    }

    public Parametrospresupuestos(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Parametrospresupuestos(BigDecimal secuencia, Date fechadesde, Date fechahasta, Date fechageneracion) {
        this.secuencia = secuencia;
        this.fechadesde = fechadesde;
        this.fechahasta = fechahasta;
        this.fechageneracion = fechageneracion;
    }

    public BigDecimal getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(BigDecimal secuencia) {
        this.secuencia = secuencia;
    }

    public Date getFechadesde() {
        return fechadesde;
    }

    public void setFechadesde(Date fechadesde) {
        this.fechadesde = fechadesde;
    }

    public Date getFechahasta() {
        return fechahasta;
    }

    public void setFechahasta(Date fechahasta) {
        this.fechahasta = fechahasta;
    }

    public Date getFechageneracion() {
        return fechageneracion;
    }

    public void setFechageneracion(Date fechageneracion) {
        this.fechageneracion = fechageneracion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigInteger getMovNumero() {
        return movNumero;
    }

    public void setMovNumero(BigInteger movNumero) {
        this.movNumero = movNumero;
    }

    public String getEstadotesoreria() {
        return estadotesoreria;
    }

    public void setEstadotesoreria(String estadotesoreria) {
        this.estadotesoreria = estadotesoreria;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getRespuestaxml() {
        return respuestaxml;
    }

    public void setRespuestaxml(String respuestaxml) {
        this.respuestaxml = respuestaxml;
    }

    public Short getEmpresaCodigo() {
        return empresaCodigo;
    }

    public void setEmpresaCodigo(Short empresaCodigo) {
        this.empresaCodigo = empresaCodigo;
    }

    @XmlTransient
    public Collection<Solucionesnodos> getSolucionesnodosCollection() {
        return solucionesnodosCollection;
    }

    public void setSolucionesnodosCollection(Collection<Solucionesnodos> solucionesnodosCollection) {
        this.solucionesnodosCollection = solucionesnodosCollection;
    }

    public TiposTrabajadores getTipotrabajador() {
        return tipotrabajador;
    }

    public void setTipotrabajador(TiposTrabajadores tipotrabajador) {
        this.tipotrabajador = tipotrabajador;
    }

    public Procesos getProceso() {
        return proceso;
    }

    public void setProceso(Procesos proceso) {
        this.proceso = proceso;
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
        if (!(object instanceof Parametrospresupuestos)) {
            return false;
        }
        Parametrospresupuestos other = (Parametrospresupuestos) object;
        if ((this.secuencia == null && other.secuencia != null) || (this.secuencia != null && !this.secuencia.equals(other.secuencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entidades.Parametrospresupuestos[ secuencia=" + secuencia + " ]";
    }
    
}
