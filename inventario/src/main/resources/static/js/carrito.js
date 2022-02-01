const d = document;


function insertarDetalles()
{
    d.querySelector("table tbody").textContent="";
    // localStorage
    const $template = d.getElementById("plantilla").content;
    const $fragment = d.createDocumentFragment();
    const $totalCart = d.querySelector(".total-cart");
    const $pago = d.getElementById('pago');
    const $saldo = d.getElementById('saldo');
    let accu=0;
    
    const dets = JSON.parse(localStorage.getItem('dets'));
    if(dets===null)
        return;
    let loops = dets.length;
   
    for(let i=0;i<loops;i++)
    {
        const det = dets[i];
        $template.querySelector('.indice').textContent = i+1;
        $template.querySelector('.nombre').textContent = det.nombre;
        $template.querySelector('.descripcion').textContent = det.descripcion;
        $template.querySelector('.valor').textContent = det.valorPrestacion;
        $template.querySelector('.total').textContent = (det.cant*det.valorPrestacion).toFixed(2);

        const $clone = document.importNode($template,true);
        //Botones y input de los detalles
        const $cant = $clone.querySelector('.cant');
        $cant.value = det.cant;
        $cant.max = det.cantDisponible;
        const $total = $clone.querySelector('.total');
        $cant.addEventListener('input', function(e)
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
            //Calculo del nuevo total
            let detAnt = +(det.cant*det.valorPrestacion).toFixed(2);
            let detAct = +(val*det.valorPrestacion).toFixed(2);
            let dif = +(detAct - detAnt).toFixed(2);

            let totalAct = +$totalCart.value;
            totalAct = +totalAct.toFixed(2);

            totalAct=totalAct+dif;
            $totalCart.value = totalAct;
            //Calculo del pago
            $pago.value = (totalAct/2).toFixed(2);
            $pago.min = (totalAct/2).toFixed(2);
            $pago.max = totalAct;
           
            //Calculo del saldo
            $saldo.value = (totalAct- $pago.value).toFixed(2);

            det.cant = val;
            $total.textContent = (det.valorPrestacion*val).toFixed(2);
            
            localStorage.setItem('dets',JSON.stringify(dets));
        });

        //Botón eliminar
        const $delete = $clone.querySelector(".delete");
        
        $delete.addEventListener('click',function(e)
        {
            dets.pop(det);
         
            localStorage.setItem('dets',JSON.stringify(dets));
            insertarDetalles();
        });

        accu+= det.valorPrestacion*det.cant;
        $fragment.appendChild($clone);
    }
    d.querySelector("table tbody").appendChild($fragment);
    $totalCart.value = accu;
    //Calculo del pago
    $pago.value = (accu/2).toFixed(2);
    $pago.min = (accu/2).toFixed(2);
    $pago.max = accu;

    $saldo.value = accu/2;
}


d.getElementById('pago').addEventListener('change',function(e)
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

    const $totalCart = d.querySelector(".total-cart");
    const $saldo = d.getElementById('saldo');

    $saldo.value = (+$totalCart.value - val).toFixed(2);

});
//Funcion para enviar datos
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


d.getElementById('btn-comprar').addEventListener('click',function(e)
{
    const reservacion = 
    {
        productos: 
        [
            // {
            //     id: 1,
            //     cant:1
            // },
            // {
            //     id: 2,
            //     cant:1
            // }
        ],
        valorPagado:0.00,
        dirEntrega: null
    }
    const dets = JSON.parse(localStorage.getItem('dets'));
    
    for (const d of dets) 
    {
        let p = {id:d.id, cant:d.cant};
        reservacion.productos.push(p);    
    }
    reservacion.valorPagado =d.getElementById('pago').value;

    Swal.fire({
        title: '¿Esta seguro de realizar esta acción?',
        text: 'Tenga en cuenta que posee 3 días para acercarse a nuestro local para la recepción o cambiar la fecha de expiración',
        icon: 'question',
        iconHtml: '?',
        confirmButtonText: 'Si',
        cancelButtonText: 'No',
        showCancelButton: true,
        showCloseButton: true
      }).then(accept =>{
          if(accept.isConfirmed)
          {
            postData("/api/v1/cliente/reservacion",reservacion)
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
                localStorage.clear();
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

insertarDetalles();
