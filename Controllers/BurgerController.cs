using Microsoft.AspNetCore.Mvc;
using Brasil_BurgerC.Data;
using Brasil_BurgerC.Models;

namespace Brasil_BurgerC.Controllers
{
    public class BurgerController : Controller
    {
        private readonly ApplicationDbContext _context;

        public BurgerController(ApplicationDbContext context)
        {
            _context = context;
        }

        public IActionResult Details(int id)
        {
            var burger = _context.Burgers.FirstOrDefault(b => b.Id == id);
            if (burger == null) return NotFound();
            return View(burger);
        }
    }
}
