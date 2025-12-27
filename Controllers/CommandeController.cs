using Microsoft.AspNetCore.Mvc;
using Brasil_BurgerC.Data;
using Brasil_BurgerC.Models;

namespace Brasil_BurgerC.Controllers
{
    public class CommandeController : Controller
    {
        private readonly ApplicationDbContext _context;

        public CommandeController(ApplicationDbContext context)
        {
            _context = context;
        }

        public IActionResult Create(int burgerId)
        {
            var burger = _context.Burgers.FirstOrDefault(b => b.Id == burgerId);
            if (burger == null) return NotFound();

            var commande = new Commande
            {
                DateCommande = DateTime.Now,
                Total = burger.Prix,
                Burger = burger
            };

            return View(commande);
        }

        [HttpPost]
        public IActionResult Create(Commande commande, string[] supplements, string modeConsommation, string paiement)
        {
            if (ModelState.IsValid)
            {
                // Exemple de calcul du total avec suppléments
                decimal total = commande.Total;
                if (supplements.Contains("Frites")) total += 1000;
                if (supplements.Contains("Coca")) total += 500;
                if (supplements.Contains("Sprite")) total += 500;

                commande.Total = total;
                commande.ModeConsommation = modeConsommation;
                commande.Paiement = paiement;

                _context.Commandes.Add(commande);
                _context.SaveChanges();

                return RedirectToAction("Suivi", new { id = commande.Id });
            }
            return View(commande);
        }

        public IActionResult Suivi(int id)
        {
            var commande = _context.Commandes.Find(id);
            if (commande == null) return NotFound();
            return View(commande);
        }
    }
}
