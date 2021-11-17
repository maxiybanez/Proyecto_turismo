
package modelo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;


public class Paquete {
 
    //---ATRIBUTOS--------------------------------------------------------------
    private int idPaquete;
    private Transporte transporte;
    private ExtraAlojamiento extraAlojamiento;
    private Cliente cliente;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDate fechaEmision;
    private float costoTotal;
    private boolean activo;

    //---CONSTRUCTORES----------------------------------------------------------

    public Paquete() {

    }

    public Paquete(Transporte transporte, ExtraAlojamiento extraAlojamiento, Cliente cliente, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaEmision, float costoTotal, boolean activo) {
        this.transporte = transporte;
        this.extraAlojamiento = extraAlojamiento;
        this.cliente = cliente;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaEmision = fechaEmision;
        this.costoTotal = costoTotal;
        this.activo = activo;
    }

    public Paquete(int idPaquete, Transporte transporte, ExtraAlojamiento extraAlojamiento, Cliente cliente, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaEmision, float costoTotal, boolean activo) {
        this.idPaquete = idPaquete;
        this.transporte = transporte;
        this.extraAlojamiento = extraAlojamiento;
        this.cliente = cliente;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaEmision = fechaEmision;
        this.costoTotal = costoTotal;
        this.activo = activo;
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }

    public Transporte getTransporte() {
        return transporte;
    }

    public void setTransporte(Transporte transporte) {
        this.transporte = transporte;
    }

    public ExtraAlojamiento getExtraAlojamiento() {
        return extraAlojamiento;
    }

    public void setExtraAlojamiento(ExtraAlojamiento extraAlojamiento) {
        this.extraAlojamiento = extraAlojamiento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public float getCostoTotal() {
 
        return costoTotal;
 
    }
    

    public void setCostoTotal(float costoTotal) {
        this.costoTotal = costoTotal;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.idPaquete;
        hash = 71 * hash + Objects.hashCode(this.transporte);
        hash = 71 * hash + Objects.hashCode(this.extraAlojamiento);
        hash = 71 * hash + Objects.hashCode(this.cliente);
        hash = 71 * hash + Objects.hashCode(this.fechaInicio);
        hash = 71 * hash + Objects.hashCode(this.fechaFin);
        hash = 71 * hash + Objects.hashCode(this.fechaEmision);
        hash = 71 * hash + Float.floatToIntBits(this.costoTotal);
        hash = 71 * hash + (this.activo ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Paquete other = (Paquete) obj;
        if (this.idPaquete != other.idPaquete) {
            return false;
        }
        if (Float.floatToIntBits(this.costoTotal) != Float.floatToIntBits(other.costoTotal)) {
            return false;
        }
        if (this.activo != other.activo) {
            return false;
        }
        if (!Objects.equals(this.transporte, other.transporte)) {
            return false;
        }
        if (!Objects.equals(this.extraAlojamiento, other.extraAlojamiento)) {
            return false;
        }
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        if (!Objects.equals(this.fechaInicio, other.fechaInicio)) {
            return false;
        }
        if (!Objects.equals(this.fechaFin, other.fechaFin)) {
            return false;
        }
        if (!Objects.equals(this.fechaEmision, other.fechaEmision)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Paquete{" + "idPaquete=" + idPaquete + ", transporte=" + transporte + ", extraAlojamiento=" + extraAlojamiento + ", cliente=" + cliente + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", fechaEmision=" + fechaEmision + ", costoTotal=" + costoTotal + ", activo=" + activo + '}';
    }


    


}