package devapp.inventario.services;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import devapp.inventario.dto.ProductoPresDto;
import devapp.inventario.dto.ReservacionClientDto;
import devapp.inventario.dto.PrestacionEmplDto;
import devapp.inventario.entities.Cliente;
import devapp.inventario.entities.DetRecepPrest;
import devapp.inventario.entities.Empleado;
import devapp.inventario.entities.EstRecepPrest;
import devapp.inventario.entities.Producto;
import devapp.inventario.entities.RecepPrest;
import devapp.inventario.repositories.ClienteRepository;
import devapp.inventario.repositories.EmpleadoRepository;
import devapp.inventario.repositories.ProductoRepository;
import devapp.inventario.repositories.RecepPrestRepository;

@Service
public class RecepPrestService {

    final String valVacio ="Sin especificar" ;
    @Autowired
    RecepPrestRepository recepPrestRepo;

    @Autowired
    ProductoRepository productoRepo;

    @Autowired
    EmpleadoRepository empleadoRepo;

    @Autowired
    ClienteRepository clienteRepo;

    public ArrayList<RecepPrest> obtenerRecepPrest(){
        return (ArrayList<RecepPrest>)recepPrestRepo.findAll();
    }

    public RecepPrest getById(long id){
        return recepPrestRepo.findById(id).get(); 
    }
                        //Para generar un reservacion por parte del cliente
    public RecepPrest saveReservacionCliente(ReservacionClientDto reservacionDto)
    {
        int est = 5; //5:Reservacion
        String direccionEntrega = reservacionDto.getDirEntrega();
        int idCliente = reservacionDto.getIdCliente();
        int idEmpleado = 1;//El empleado 1 es el sistema
        double valorPagado = reservacionDto.getValorPagado();
        double totalprestacion;
        RecepPrest reservacion = generarDetalles(reservacionDto.getProductos());
        Date fechaCaducida;
        if(reservacion==null)
            return null;

        totalprestacion = reservacion.getTotalPrestacion();
        if(valorPagado<totalprestacion/2||valorPagado>totalprestacion) //Debe pagar al menos el 50% y no debe ser mayor al total
            return null; 
        
        if(direccionEntrega==null)
        direccionEntrega = valVacio;

        fechaCaducida = expiresFrom(3); //La reservacion expira en 3 dias
        
        Empleado empleado = empleadoRepo.findById(idEmpleado).get();
        Cliente cliente = clienteRepo.findById(idCliente).get();
            
        EstRecepPrest estado = new EstRecepPrest(reservacion, est, empleado);
    
        reservacion.setCliente(cliente);
        reservacion.setValorPagado(valorPagado);
        reservacion.setFechaCaducida(fechaCaducida);
        reservacion.setDireccionEntrega(direccionEntrega);
        reservacion.setGarantia(valVacio);
        reservacion.prestacion(estado);

        return recepPrestRepo.save(reservacion);
    }

                        //Para generar una Prestacion y/o reservacion por parte de un empleado
    public RecepPrest savePrestacionEmpleado(PrestacionEmplDto prestacionDTO)
    {


        int est;
        double valorPagado = prestacionDTO.getValorPagado();
        int idEmpleado = prestacionDTO.getIdEmpleado();
        int idCliente = prestacionDTO.getIdCliente();
        String direccionEntrega = prestacionDTO.getDirEntrega();
        String garantia = prestacionDTO.getGarantia();
        Long fechaC = prestacionDTO.getFechaCaducida();
        Date fechaCaducida;

        if(idEmpleado==1)   //El sistema no puede realizar prestacion
        return null;

        RecepPrest prestacion = generarDetalles(prestacionDTO.getProductos()); //Genera la reservacion con los detalles dentro
        if(prestacion==null)
            return null;

        double totalprestacion = prestacion.getTotalPrestacion();

        if(valorPagado<totalprestacion/2||valorPagado>totalprestacion) //Debe pagar al menos el 50% y no debe ser mayor al total
            return null;

            
        if(prestacionDTO.isReservacion())
            est = 5;    //5: Reservacion
        else
        {
            if(idEmpleado==1)//idEmpleado=1; es el sistema
                est = 5;   //Reservacion 
            else
                est = 3;    //3: Despacho
        }
           
            //Calculo de la fecha en que caduca la prestacion
        if(fechaC==null||idEmpleado==1) //idEmpleado=1; es el sistema
            fechaCaducida = expiresFrom(3); //Caduca en 3 dias a partir del momento en que se crea
        else
            fechaCaducida = new Date(fechaC);  

        if(direccionEntrega==null)
            direccionEntrega = valVacio;
        
        if(garantia==null)
        {
            garantia =valVacio;
            est = 5; //5: Reservacion
        }

  
        //Obtencion del empleado y cliente
        Empleado empleado = empleadoRepo.findById(idEmpleado).get();
        Cliente cliente = clienteRepo.findById(idCliente).get();
        
        EstRecepPrest estado = new EstRecepPrest(prestacion, est, empleado);
        
		prestacion.setCliente(cliente);
		prestacion.setValorPagado(valorPagado);
        prestacion.setFechaCaducida(fechaCaducida);
		prestacion.setDireccionEntrega(direccionEntrega);
        prestacion.setGarantia(garantia);
        prestacion.prestacion(estado);
        
        return recepPrestRepo.save(prestacion);
    }


    //***********************************************Metodos para cambiar el estado
    //Metodo para cancelar la reservacion
    public RecepPrest cancelReservacion(long idRecepPrest, int idEmpleado)
    {
        
        int newEstado =4;//4: Cancelado
        RecepPrest reservacion= recepPrestRepo.findById(idRecepPrest).get();
        Empleado empleado = empleadoRepo.findById(idEmpleado).get();
        int lastEstado = reservacion.estadoActual();
        
        if(lastEstado==5)
        {
            EstRecepPrest estado = new EstRecepPrest(reservacion, newEstado, empleado);
            reservacion.cambiarEstado(estado);
            devolverProductos(reservacion);
        }
        else
            return null;


        return recepPrestRepo.save(reservacion);
        //Para estado
        // 5: Reservado (Debe cancelar el 50% como minimo)
        // 4: Cancelado
    }


    public RecepPrest despacharReservacion(long idRecepPrest, int idEmpleado,double valFaltante,String garantia)
    {
        if(idEmpleado==1) //El empleado 1 es el sistema y no puede realizar despachos
            return null;

        RecepPrest reservacion = recepPrestRepo.findById(idRecepPrest).get();
        Empleado empleado = empleadoRepo.findById(idEmpleado).get();
        int lastEstado = reservacion.estadoActual();
        int newEstado =3;//3: Despachado

        if(reservacion.getGarantia()==valVacio&&garantia==null)
        return null;

        if(lastEstado==5)
        {
            
            EstRecepPrest estado = new EstRecepPrest(reservacion, newEstado, empleado);
            reservacion.cambiarEstado(estado);
            reservacion.setGarantia(garantia);
        }
        else
            return null;



        return recepPrestRepo.save(reservacion);

                //Para estado
        // 5: Reservado
        // 4: Cancelado
        // 3: Despachado (Debe cancelar el 100%)
        // 2: Vencido (Por si se pasa de la fecha)
        // 1: Compensando (para compensar las perdidas)
        // 0: Recepcionado
    } 

    
    public RecepPrest recepcionar()
    {
        return null;
    }
    //***********************************************Metodos auxiliares
    private void devolverProductos(RecepPrest recepPrest)
    {
        List<DetRecepPrest> detalles = recepPrest.getDetalles();
        
        for(DetRecepPrest detalle: detalles)
        {
            int cantDevuelta = detalle.getCantidadPrestada() - detalle.getCantidadPerdida();
            detalle.getProducto().devolver(cantDevuelta);   
        }
    }

    private RecepPrest generarDetalles(List<ProductoPresDto> productosDto)
    {
        if(productosDto.size()<1)
            return null;

        double totalRecepPrest=0;
        List<DetRecepPrest> detalles = new ArrayList<DetRecepPrest>();
        RecepPrest recepPrest = new RecepPrest();

        for(ProductoPresDto produ: productosDto) 
        {
            int id = produ.getId();
            int cantPrestada= produ.getCantPrestada();
            double totalDetalle;
            Producto producto = productoRepo.findById(id).get();

            if(!producto.prestar(cantPrestada)) //Si no se puede prestar entonces retornar 0
                return null;

            totalDetalle= producto.getValorPrestacion()*cantPrestada;

            detalles.add(new DetRecepPrest(recepPrest, producto, cantPrestada, totalDetalle));
            totalRecepPrest+=totalDetalle;
        }

        recepPrest.setDetalles(detalles);
        recepPrest.setTotalPrestacion(totalRecepPrest);
        return recepPrest;
    }

    private Date expiresFrom(int dias)
    {
        Date fecha = new Date();
        if (dias==0) 
            return fecha;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha); 
        calendar.add(Calendar.DAY_OF_YEAR, dias);  
        
        return calendar.getTime(); 
    }


    
}
