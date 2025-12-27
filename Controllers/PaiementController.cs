using Microsoft.AspNetCore.Mvc;
using Brasil_BurgerC.Models;

namespace Brasil_BurgerC.Controllers
{
    public class PaiementController : Controller
    {
        public IActionResult Create()
        {
            return View();
        }

        [HttpPost]
        public IActionResult Create(Paiement paiement)
        {
            if (ModelState.IsValid)
            {
                // TODO: sauvegarder le paiement en base
                return RedirectToAction("Index", "Home");
            }
            return View(paiement);
        }
    }
}
