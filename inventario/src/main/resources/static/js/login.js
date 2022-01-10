function blockOrNone(selector) 
{
    const e = document.querySelector(selector);
    const propiedades = window.getComputedStyle(e);
    if (propiedades.getPropertyValue('display') === "none") 
    {
        e.style.display = "block";
    } 
    else 
    {
      e.style.display = "none";
    }
}

function toggleResetPswd(e)
{
    e.preventDefault();
    blockOrNone('#logreg-forms .form-signin');
    // $('#logreg-forms .form-signin').toggle() // display:block or none
    blockOrNone('#logreg-forms .form-reset');
    // $('#logreg-forms .form-reset').toggle() // display:block or none
}

function toggleSignUp(e){
    e.preventDefault();
    blockOrNone('#logreg-forms .form-signin');
    // $('#logreg-forms .form-signin').toggle(); // display:block or none
    blockOrNone('#logreg-forms .form-signup');
    // $('#logreg-forms .form-signup').toggle(); // display:block or none
}

(function()
{
    const d = document;
    d.querySelector('#logreg-forms #forgot_pswd').addEventListener("click",toggleResetPswd);
    d.querySelector('#logreg-forms #cancel_reset').addEventListener("click",toggleResetPswd);
    d.querySelector('#logreg-forms #btn-signup').addEventListener("click",toggleSignUp);
    d.querySelector('#logreg-forms #cancel_signup').addEventListener("click",toggleSignUp);
    // // Login Register Form
    // $('#logreg-forms #forgot_pswd').click(toggleResetPswd);
    // $('#logreg-forms #cancel_reset').click(toggleResetPswd);
    // $('#logreg-forms #btn-signup').click(toggleSignUp);
    // $('#logreg-forms #cancel_signup').click(toggleSignUp);

}
)();