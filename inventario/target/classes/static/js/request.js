function makeRequest(pathName)
{
    const http = new XMLHttpRequest();
    const url = 'http://localhost:8080/'+pathName;
    return fetch(url)
    .then(function(response) {
      return response.json();
    });
};


export async function cantPagProdu()
{
    let pathName ='producto/cantpagina';
    let result; 
    return (await makeRequest(pathName));
};

export async function getPagProdu(n)
{
  let pathName = 'producto/pag/'+n;
  return (await makeRequest(pathName));
}
