function blockOrNone(selector) 
{
    const e = document.querySelector(selector);
    const propiedades = window.getComputedStyle(e);
    if (propiedades.getPropertyValue('display') === "none") 
    {
        e.style.display = "flex";
    } 
    else 
    {
      e.style.display = "none";
    }
};

function toggle(selector1,selector2)
{
    blockOrNone(selector1);

    blockOrNone(selector2);
};



function toggleRecepPrest(e)
{
    e.preventDefault();
    toggle('#menu-principal','#submenu-recep-prest');
};

function toggleClientes(e)
{
    e.preventDefault();
    toggle('#menu-principal','#submenu-clientes');
}

function toggleProductos(e)
{
    e.preventDefault();
    toggle('#menu-principal','#submenu-productos');
}

function toggleProveedores(e)
{
    e.preventDefault();
    toggle('#menu-principal','#submenu-proveedores');
}



(function()
{
    const d = document;
    const $btnMenuRecepprest = d.getElementById('btn-menu-recepprest');
    const $backMenuRecepprest = d.querySelector('#submenu-recep-prest .back')
    $btnMenuRecepprest.addEventListener("click",toggleRecepPrest);
    $backMenuRecepprest.addEventListener('click',toggleRecepPrest);

    
    const $btnMenuClientes = d.getElementById('btn-menu-clientes');
    const $backMenuClientes = d.querySelector('#submenu-clientes .back');
    $btnMenuClientes.addEventListener('click',toggleClientes);
    $backMenuClientes.addEventListener('click',toggleClientes);

    const $btnMenuProductos = d.getElementById('btn-menu-productos');
    const $backMenuProductos = d.querySelector('#submenu-productos .back');
    $btnMenuProductos.addEventListener('click',toggleProductos);
    $backMenuProductos.addEventListener('click',toggleProductos);


    const $btnMenuProveedores = d.getElementById('btn-menu-proveedores');
    const $backMenuProveedores = d.querySelector('#submenu-proveedores .back');
    $btnMenuProveedores.addEventListener('click',toggleProveedores);
    $backMenuProveedores.addEventListener('click',toggleProveedores);

    // 
})();


