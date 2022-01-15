(function()
{
    const detalles =     //Objeto literal para los detalles
    {
        fila: [],
        total: 0
    };
    const recepPrest=
    {
        productos: 
        [
            // {
            //     id: 13,
            //     cant:1
            // }
        ],
        valorPagado:0,
        idEmpleado: 0,
        idCliente: 1,
        dirEntrega: "",
        garantia: "",
        fechaCaducida: 0,
        reservacion: true
    }
    

    //Para el cliente
    const $check = document.getElementById('check-consufinal');
    const $btnSearchClient = document.getElementById('btn-search-client');
    const $formCliente = document.getElementById('form-cliente'); 

    // Para la busqueda de los productos
    const $btnSearchProd = document.getElementById('btn-search-product');
    const $searchProd = document.querySelector('input[name="search-product"]');
    const $tBodyProd = document.querySelector('.table-product tbody');
    const $product = document.getElementById('product');
    
    //Para añadir detalles

    const $tBodydetalles = document.querySelector('.table-detalles tbody');
    const $total = document.getElementById('total');
    //Para realizar el pago
    const $pago = document.getElementById('pago');
    const $saldo = document.getElementById('saldo');

    //Para enviar y guardar 
    const $enviar = document.getElementById('enviar');
    const $checkReservacion =document.getElementById('check-reservacion');
    

    //**********************************************Funciones 

    function makeRequest(pathName)  //Para pedir datos al servidor
    {
        const origin = location.origin;

        const url = origin+pathName;
        return fetch(url)
        .then(function(response) 
        {

            if(response.status==200)
                return response.json();
            else
            {
                return null;
            }
        });

    };

    
    // async function postData(pathName,data)
    // {
    //     const origin = location.origin;
    //     const url = origin+pathName;
    //     return  await fetch(url,        
    //     {
    //         method: 'POST',
    //         headers: 
    //         {
    //             'Content-Type': 'application/json'
    //         },
    //         body: JSON.stringify(data)
    //     })
    //     .then(function(response) 
    //     {
    //         console.log(response)
    //         if(response.headers.append.length>=2)
    //             return response.json();
    //         else
    //         {
    //             return null;
    //         }
    //     });
    // };

    async function postData(pathName,data)
    {
        const origin = location.origin;
        const url = origin+pathName;
        return await fetch(url,        
        {
            method: 'POST',
            headers: 
            {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
      
    };

    const generarRecepPrest = function()
    {
        let fecha =document.getElementById('fecha').value;
        fecha =fecha.split('-');
        recepPrest.fechaCaducida = (new Date(fecha)).getTime();
        recepPrest.garantia = document.getElementById('garantia').value;
        recepPrest.dirEntrega = document.getElementById('direccion').value;
        recepPrest.reservacion = $checkReservacion.checked;
        recepPrest.valorPagado = parseFloat($pago.value);
        recepPrest.idEmpleado = 2;
     
        recepPrest.productos=[];
        for (let i=0;i<detalles.fila.length;i++) 
        {
            let fila = detalles.fila[i]; 
            recepPrest.productos.push(
                {
                    id: fila.id,
                    cant: fila.cant
                }
            )
        }
    
        return recepPrest;
    };

    $enviar.addEventListener("click",function(e)
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
                postData('/api/v1/recepprest',generarRecepPrest())
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
   
    //****Funciones para buscar cliente*/
    function searchCliente(cedula)
    {
        makeRequest('/api/v1/cliente/ci/'+cedula).then(
            function(data)
            {
         
                if(data==null)
                    return;
             
                
                $formCliente.querySelector("#nombres-cliente").value = data.nombres;
                $formCliente.querySelector("#apellidos-cliente").value = data.apellidos;
                $formCliente.querySelector("#celular-cliente").value = data.celular;
                $formCliente.querySelector("#correo-cliente").value = data.correo;

                recepPrest.idCliente = data.id; //Se almacena el id
            }
        );
    }
    
    //****Funciones para buscar productos y generar detallles*/
    const borrarDetalles = function(det)
    {
        detalles.fila.pop(det);
        updateDetalles();
    };
    
  
    const updateDetalles =function() //Actualiza el elemento html que contiene los detalles
    {
        $tBodydetalles.textContent =null;
        const d = document;
        const $fragment = d.createDocumentFragment();
        const dets = detalles.fila;
        let acc=0;
        for (let i=0;i<dets.length;i++) 
        {
            element = dets[i];
            const $tr = d.createElement('tr');
            const $th = d.createElement('th');
            $th.textContent = (i+1);
            $th.setAttribute('scope','row');

            const $tdNombre = d.createElement('td');
            const $divNombre = d.createElement('div');
            $divNombre.textContent =dets[i].nombre;
            $divNombre.className = 'nombre-over-flow';
            $tdNombre.appendChild($divNombre);

            const $tdCant = d.createElement('td');
            const $inputCant = d.createElement('input');
            $inputCant.value = dets[i].cant;
            $inputCant.min = 1;
            $inputCant.max = dets[i].cantDisponible;

            $inputCant.oninput = function(e)
            {
                let val = parseFloat(this.value);
                let mx = parseFloat(this.max);
                if(val>mx)
                    this.value= this.max;
                
                updateDetalle($tr,dets[i])
            };
            $inputCant.type = 'number';
            $tdCant.appendChild($inputCant);
            
            const $tdVal = d.createElement('td');
            $tdVal.textContent = dets[i].valor;
            $tdVal.className = 'text-center';

            const $tdTotal = d.createElement('td');
            $tdTotal.textContent = dets[i].total;
            $tdTotal.className = 'text-center total';

            const $tdBorrar = d.createElement('td');
            const $liBorrar = d.createElement('li');
            const $aBorrar = d.createElement('a');
            $liBorrar.className = 'fas fa-trash-alt';
            $aBorrar.href="";
            $aBorrar.appendChild($liBorrar);
            $tdBorrar.appendChild($aBorrar);
            $aBorrar.onclick = function(e)
            {
                e.preventDefault();
                borrarDetalles(dets[i]);
            };

            $tr.appendChild($th);
            $tr.appendChild($tdNombre);
            $tr.appendChild($tdCant);
            $tr.appendChild($tdVal);
            $tr.appendChild($tdTotal);
            $tr.appendChild($tdBorrar);
            $fragment.appendChild($tr);
            
            acc+= dets[i].total;
        }
        
        $tBodydetalles.appendChild($fragment);
        $total.textContent=acc;
        detalles.total = acc;
        //Auto calculo del pago minimo
        $pago.max = acc;
        $pago.min = (acc/2).toFixed(2);
        $pago.value=(acc/2).toFixed(2);
        $saldo.textContent = (acc - parseFloat($pago.value)).toFixed(2);
        

    };
    const updateDetalle = function($detalle, det)
    {
        let newCant = $detalle.querySelector('input').value;
        let newTotalDet = det.valor*newCant;
        let diferencia = newTotalDet-det.total;
        let total = parseFloat($total.textContent)+diferencia;
        $detalle.querySelector('.total').textContent = newTotalDet;
        $total.textContent = total;
       
        det.total = newTotalDet;
        detalles.total = parseFloat(total);
        
        $pago.value = (total/2).toFixed(2);
        $pago.min = (total/2).toFixed(2);
        $pago.max = total;

        $saldo.textContent = (total- $pago.value).toFixed(2);


    };

    const addToDetalles =function(producto)
    {
        const $inputNumber = $product.querySelector('input[type="number"]');
        dets = detalles.fila;
        const det = new Object();
        det.id = producto.id;
        det.cantDisponible = producto.cantDisponible;
        det.nombre = producto.nombre;
        det.cant = $inputNumber.value;
        det.valor = producto.valorPrestacion;
        det.total = det.cant*det.valor;    



        for (const element of dets) 
        {
            if(element.id===det.id)
            {
                element.cant = det.cant;
                element.total = det.total;
                updateDetalles();
                return;
            }
            
        }
        dets.push(det);
        updateDetalles();
    };

    const visualizarProducto = function(producto)   //Permite visualizar el producto seleccionado
    { 
        $product.querySelector('.nombre-product').textContent = producto.nombre;
        $product.querySelector('.descrip-product').textContent = producto.descripcion;
        $product.querySelector('.cal-dis-product').textContent = producto.cantDisponible;
        $product.querySelector('.val-prest-product ').textContent = producto.valorPrestacion;
        $product.querySelector('input[type="number"]').max = producto.cantDisponible
        $product.querySelector('input[type="number"]').value =1;
        $product.querySelector('input[type="number"]').onchange = 
        function()
        {
            let val = parseFloat(this.value);
            let mx = parseFloat(this.max);
            if(val>mx)
                this.value= this.max;
        };


        if(producto.img===null)
            $product.querySelector('img').setAttribute("src",'');
        else
            $product.querySelector('img').setAttribute("src",`/pic/${producto.img}.jpg`);
        
        $product.querySelector('a').onclick = function(e)
        {
            e.preventDefault();
            addToDetalles(producto);
        };
    };


    const searchProductos = function(e) //Realiza la busqueda de los productos
    {
               
        e.preventDefault();
        const d = document;
        $tBodyProd.textContent=null;    //Se borran los productos
        let search = $searchProd.value;
        const optionSearch = d.querySelector('#select-product-search').value;
    
        let path;
        if(optionSearch==1)
        {
            path = `/api/v1/producto/${search}`;
        }
        else
        {
            path = `/api/v1/producto/all?search=${search}`;
        }
        makeRequest(path) //Solicita los productos
        .then(
            function(data)
            {
                const $fragment = d.createDocumentFragment();
                console.log(data);
                if(optionSearch==1) //Si solo es un solo producto
                    data = [data];
                for (const producto of data)            
                {
                    const $tr = d.createElement('tr');   
                    const $tdNombre = d.createElement('td');
                    const $tdDescripcion = d.createElement('td');
                    
                    $tdNombre.textContent = producto.nombre;
                    $tdDescripcion.textContent = producto.descripcion;

                    $tr.appendChild($tdNombre);
                    $tr.appendChild($tdDescripcion);

                    $tr.addEventListener("click",   //Evento para que se pueda visualizar el producto
                    function(e)
                    {
                        visualizarProducto(producto);
                    });
                  
                    $fragment.appendChild($tr);
                }
                
                $tBodyProd.appendChild($fragment);
            });
    };

    //***********************************************************Eventos

    $check.addEventListener("change",function()
    {
        const $ci = document.querySelector('input[name="ci-cliente"]')
      
        if(this.checked==true)
        {
     
            $formCliente.querySelector("#nombres-cliente").value = "";
            $formCliente.querySelector("#apellidos-cliente").value = "";
            $formCliente.querySelector("#celular-cliente").value = "";
            $formCliente.querySelector("#correo-cliente").value = "";
            $ci.value ="";
            $ci.disabled = true;
            $btnSearchClient.disabled = true;
            recepPrest.idCliente = 1;

        }
        else
        {
            $ci.disabled = false;
            $btnSearchClient.disabled = false;
        }
    }); 
    
    //para buscar el producto
    $btnSearchClient.addEventListener('click',function(e)
    {
        e.preventDefault();
        const $ci  = $formCliente.querySelector('input[name="ci-cliente"]');
        searchCliente($ci.value);
    });

    $product.querySelector('a').onclick = function(e)
    {
        e.preventDefault();
    }
    $btnSearchProd.addEventListener("click",searchProductos);
    
    // Controlar el valor del pago
    
    
    $pago.onchange = function(e)
    {
        let val = parseFloat(this.value);
        let max = parseFloat(this.max);
        let min = parseFloat(this.min);

   
        if (val>max)
        {
            
            this.value = max;
            val = max;
        }
        console.log(val+"<"+min+": "+ (val<min))
        if (val<min)
        {
            this.value = min;
            val = min;
        }
            

        $saldo.textContent = (parseFloat($total.textContent)-val).toFixed(2);        
    };

    $pago.onkeyup = function(e)
    {
        const diner = /^[0-9]+\.{0,1}[0-9]{0,2}$/;
        const text =this.value;
        if(!diner.test(text))
        {   
            this.value=parseFloat(text).toFixed(2);
        }
    };

    
  
})();

