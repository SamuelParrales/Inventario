(function()
{
    const d = document;
    const recepPrest =  //Para enviaar en la petición pull
    {
        idEmpleado: 2,
        productos: 
        [
            // {
            //      id: 13,
            //      cant:1
            // }
        ],
        valorPagado: 0

    };
    //Para los estados
    const estados = prestacion.estados;
    let posLastEstado = prestacion.estados.length -1;
    let lastEstado = estados[posLastEstado].estado;
    //Obtencion de la fecha y hora
    const fechaCaducida = prestacion.fechaCaducida;
    let fecha = fechaCaducida.substring(0,10);
    let hora = fechaCaducida.substring(11,19);
    //Datos del cliente
    const cliente = prestacion.cliente; 
    let nombreCompleto = cliente.apellidos+" "+ cliente.nombres;


    d.getElementById('id').value = prestacion.id;
    d.getElementById('estado').value = translateEstado(lastEstado);
    d.getElementById('fecha-expiracion').value = fecha + '/'+ hora; 
    d.getElementById('nombre-completo').value = nombreCompleto;
    d.getElementById('ci').value = cliente.ci;
    d.getElementById('celular').value = cliente.celular ;
    d.getElementById('correo').value = cliente.correo; 
    d.getElementById('dir-entrega').value = prestacion.direccionEntrega;
    d.getElementById('garantia').value = prestacion.garantia;

    generateDetalles(prestacion.detalles)


    function translateEstado(est)
    {
        switch(est)
        {
            case 5:return 'Reservado';
            case 4: return 'Cancelado';
            case 3: return 'Prestado';
            case 2: return 'Expirado';
            case 1: return 'Compensando';
            case 0: return 'Recepcionado';
        }            
        
    }

    function generateDetalles(detalles =[])
    {
        
        const loops = detalles.length;
        const $fragment = d.createDocumentFragment();
        const $template = d.getElementById('template-fila').content;
        console.log($template); 
        let total=0;
        for(let i=0;i<loops;i++)
        {
            const producto = detalles[i].producto;
            let cantDevuelta = detalles[i].cantidadPrestada - detalles[i].cantidadPerdida;
            let totalPrestacion = detalles[i].totalPrestacion;
            let totalPerdida =detalles[i].totalPerdida;
            console.log(detalles[i].totalPerdida)
            let totalDet = totalPrestacion + totalPerdida;
            $template.querySelector('.i').textContent = (i+1);
            $template.querySelector('.nombre-det').textContent = producto.nombre;
            $template.querySelector('.descripcion-det').textContent = producto.descripcion;
            $template.querySelector('.cant-p-det').textContent = detalles[i].cantidadPrestada;
            $template.querySelector('.valor-p-det').textContent = producto.valorPrestacion;
            $template.querySelector('.total-pres-det').textContent = totalPrestacion;
            
            const $cantD = $template.querySelector('.cant-d-det');
           
            $cantD.value = cantDevuelta;
            $cantD.min = 0;
            $cantD.max = detalles[i].cantidadPrestada;


            $template.querySelector('.valor-u-det').textContent = producto.valorUnitario;
            $template.querySelector('.total-per-det').textContent = totalPerdida;
            $template.querySelector('.total-det').textContent = totalDet; 
            const $clone = document.importNode($template,true);
            const $fila = $clone.querySelector('.fila-det');

            //Añadiendo el evento para cambiar la cantidad devuelta
            $clone.querySelector('.cant-d-det').addEventListener('input',function(e)
            {
                
                let val = parseFloat(this.value);
                let mx = parseFloat(this.max);
                let min = parseFloat(this.min);
                
                if(isNaN(val))
                    this.value=0;
                if(val>mx)
                    this.value= this.max;

                if (val<min)
                {
                    this.value = min;
                }
                updateCantDevuelta(detalles[i],this,$fila);
            });

            $fragment.appendChild($clone)
            total+=totalDet;
        }
        let saldo =+( total -prestacion.valorPagado).toFixed(2);
        d.getElementById('total').textContent = total;
        d.getElementById('pago-pres').textContent = prestacion.valorPagado;
        d.getElementById('pago-per').value = 0;
        d.getElementById('saldo').textContent = saldo;
        d.querySelector('.table-detalles tbody').appendChild($fragment);
    };

    function updateCantDevuelta(det ={},$cantD,$detalle)
    {
        let valorUnitario = det.producto.valorUnitario;
        let cantD = parseInt($cantD.value);
        let cantP = parseInt(det.cantidadPrestada) - cantD;
    
        let totalDetPerdidaAn = det.totalPerdida;
        let totalDetPerdidaAct = +(cantP*valorUnitario).toFixed(2);

        let diferenciaPerdida = +(totalDetPerdidaAct - totalDetPerdidaAn).toFixed(2); 
        
        det.totalPerdida = totalDetPerdidaAct;
        prestacion.totalPerdida += diferenciaPerdida;

        console.log($detalle.querySelector('.total-per-det'));
        let total = +(prestacion.totalPerdida + prestacion.totalPrestacion).toFixed(2);
        let totalDetalle = +(det.totalPerdida+det.totalPrestacion).toFixed(2);

        $detalle.querySelector('.total-det').textContent = totalDetalle;
        $detalle.querySelector('.total-per-det').textContent = det.totalPerdida;
    
        //Información general de la recepcion;
        const $pagoPer = d.querySelector('#pago-per');
        let pagoPer = +$pagoPer.value;
        d.querySelector('#total').textContent = total;
        $pagoPer.max = +(total - prestacion.valorPagado).toFixed(2);
        console.log(pagoPer+">"+$pagoPer.max)
        if(pagoPer>$pagoPer.max)
        {
            $pagoPer.value = $pagoPer.max;
            pagoPer=+$pagoPer.max;
        }
        d.querySelector('#saldo').textContent = (total - (pagoPer + prestacion.valorPagado)).toFixed(2);    
        
        //Cant Perdida
        det.cantidadPerdida = parseInt(det.cantidadPrestada- +($cantD.value));
    
    };
    //Para generar la recepcion a enviar
    function generateRecepcion()
    {
        let detalles = prestacion.detalles;
        let loops = detalles.length;
        for(let i =0;i<loops;i++)
        {
            recepPrest.productos.push(
                {
                    id: detalles[i].producto.id,
                    cant: detalles[i].cantidadPerdida
                })
        }
        let valorPer = +d.getElementById('pago-per').value;
        recepPrest.valorPagado = prestacion.valorPagado +valorPer;
        return recepPrest;
    }
    async function putData(pathName,data)
    {
        const origin = location.origin;
        const url = origin+pathName;
        return await fetch(url,        
        {
            method: 'PUT',
            headers: 
            {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
      
    };
    //Eventos
    d.getElementById('pago-per').oninput = function(e)
    {
        let val = parseFloat(this.value);
        let mx = parseFloat(this.max);
        let min = parseFloat(this.min);

        if(isNaN(val))
            this.value=0;
        if(val>mx)
            this.value= this.max;

        if (val<min)
        {
            this.value = min;
        }

        let total = +(prestacion.totalPrestacion + prestacion.totalPerdida).toFixed(2);
        let pagoTotal = prestacion.valorPagado + (+this.value);
        console.log(total);
        d.getElementById('saldo').textContent = +(total - pagoTotal).toFixed(2);
    };

    d.getElementById('enviar').addEventListener("click",function(e)
    {
        Swal.fire({
            title: '¿Esta seguro de realizar esta acción?',
            icon: 'question',
            iconHtml: '?',
            confirmButtonText: 'Si',
            cancelButtonText: 'No',
            showCancelButton: true,
            showCloseButton: true
          }).then(accept =>{
              if(accept.isConfirmed)
              {
                  console.log(generateRecepcion())
                putData(`/api/v1/recepprest/prestacion/${prestacion.id}`,generateRecepcion())
                .then(response =>
                    {
                        if(response.status==400)
                        {
                            Swal.fire(
                                {
                                    title: 'Upss! Algo ha salido mal',
                                    icon: 'error'
                                }
                            )
                        }
                            
                    });
                    Swal.fire(
                    {
                        title: 'Su acción se realizó correctamente',
                        icon: 'success'
                    }
                  ).then(()=>location = location.origin);
                  
              }
              else
              {
                  Swal.fire(
                      {
                          title: 'Su acción ha sido cancelada',
                          icon: 'warning'
                      }
                  )
              }
          })
    });

    
})();