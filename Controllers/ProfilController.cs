using Microsoft.AspNetCore.Mvc;
using Brasil_BurgerC.Models;

namespace Brasil_BurgerC.Controllers
{
    public class ProfilController : Controller
    {
        public IActionResult Index()
        {
            var profil = new Profil
            {
                Nom = "Fily",
                Telephone = "+221-77-000-00-77",
                Localisation = "Sénégal, Dakar, Point E"
            };

            return View(profil);
        }

        [HttpPost]
        public IActionResult Index(Profil profil)
        {
            if (ModelState.IsValid)
            {
                // TODO : sauvegarder les modifications
                return RedirectToAction("Index");
            }
            return View(profil);
        }

        public IActionResult Logout()
        {
            // TODO : logique de déconnexion
            return RedirectToAction("Index", "Home");
        }
    }
}
