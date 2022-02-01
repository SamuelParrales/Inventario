function crearPaginacion(cant)
{
    
    const d = document;
    const $fragment = d.createDocumentFragment();
    const search = location.search;
    for(let i=0;i<cant;i++)
    {
        const $li = d.createElement('li');
        const $a = d.createElement('a');
        $a.textContent = (i+1);
        $li.appendChild($a);
        $li.className= `page-item pag-${cant}`;

        if((i+1)==pagActive)
            $li.classList.add("active");

        $a.className = 'page-link';
        
        if(!search)
            $a.href = `/tienda/page/${i+1}`;
        else
            $a.href = `/tienda/page/${i+1}`+search;

            
           

        $fragment.appendChild($li);
        
    }
    
    const $pageItem = d.querySelector('.page-item');
    $pageItem.after($fragment);
}

function insertProducto(data)
{

    const d = document;
    const $fragment = d.createDocumentFragment();

    const $template = d.getElementById('template-producto').content;
    // $template.set
    for (const producto of data) 
    {
        
        $template.querySelector('.name-product').textContent = producto.nombre;
        $template.querySelector('.descrip-product').textContent = producto.descripcion;
        $template.querySelector('.val-prest-product').textContent = producto.valorPrestacion;
        $template.querySelector('.cal-dis-product').textContent = producto.cantDisponible;
        $template.querySelector('img').setAttribute("src",`/pic/${producto.img}.jpg`)
        const $clone = document.importNode($template,true);
        //Se añaden los eventos para añadir al carrito y la cantidad a añadir

        //Cant 
        const $cant = $clone.querySelector('.cant-product');
        $cant.value = 1;
        $cant.max = producto.cantDisponible;
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
        });


        //AddToCart
        const $addToCart = $clone.querySelector('.add-cart');
        $addToCart.addEventListener('click',function(e)
        {

            Swal.fire({
                title: '¿Esta seguro de añadir este producto al carrito?',
                text: "Su acción podrá ser modificada más tarde",
                icon: 'question',
                iconHtml: '?',
                confirmButtonText: 'Si',
                cancelButtonText: 'No',
                showCancelButton: true,
                showCloseButton: true
              }).then(accept =>{
                  //Guarda el producto en la memoria local
                if(accept.isConfirmed)
                {
                    const produToCart=
                    {
                        id: producto.id,
                        nombre: producto.nombre,
                        descripcion: producto.descripcion,
                        valorPrestacion: +(producto.valorPrestacion.toFixed(2)),
                        cantDisponible: +(producto.cantDisponible),
                        cant: parseInt($cant.value)
                    };
                   
                    if(accept.isConfirmed)
                    {
                    let guardado = false;
                    const produToCart=
                    {
                        id: producto.id,
                        nombre: producto.nombre,
                        descripcion: producto.descripcion,
                        valorPrestacion: +(producto.valorPrestacion.toFixed(2)),
                        cantDisponible: +(producto.cantDisponible),
                        cant: parseInt($cant.value)
                    };
                    let dets = JSON.parse(localStorage.getItem('dets'));
                    
                    if((dets===null)||dets.length===0)
                    {
                        dets=[];
                        dets.push(produToCart);
                        localStorage.setItem('dets',JSON.stringify(dets));
                        guardado = true;
                    }
                    if(!guardado)
                    {
                        
                        let lon = dets.length;
                        for(let i=0;i<lon;i++)
                        {
                            console.log('aaaaaaa');
                            const p = dets[i];
                            if(p.id===produToCart.id)
                            {
                                dets[i]=produToCart;
                                guardado = true;
                                break;
                            }   
                        }
                        if(!guardado)
                            dets.push(produToCart);

                        localStorage.setItem('dets',JSON.stringify(dets));

                    }
                }
                    Swal.fire(
                        {
                            title: 'Su acción se realizó correctamente',
                            icon: 'success'
                        }
                      )
                      
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



        $fragment.appendChild($clone);
    }



    d.querySelector('.products').appendChild($fragment);
    
}   

if(textSearch!=='')
    document.getElementById('search').value = textSearch;
crearPaginacion(cantPagina);
insertProducto(productos);  //La varaible producto se recibe desde html con thymeleaf


