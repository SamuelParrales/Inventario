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
        console.log(producto);
        $template.querySelector('.descrip-product').textContent = producto.descripcion;
        $template.querySelector('.val-prest-product').textContent = producto.valorPrestacion;
        $template.querySelector('.cal-dis-product').textContent = producto.cantDisponible;
        $template.querySelector('img').setAttribute("src",`/pic/${producto.img}.jpg`)
        const $clone = document.importNode($template,true);
        $fragment.appendChild($clone);
    }
    d.querySelector('.products').appendChild($fragment);
    
}   

if(textSearch!=='')
    document.getElementById('search').value = textSearch;
crearPaginacion(cantPagina);
insertProducto(productos);


